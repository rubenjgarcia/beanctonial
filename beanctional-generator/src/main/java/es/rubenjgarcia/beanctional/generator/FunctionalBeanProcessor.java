package es.rubenjgarcia.beanctional.generator;

import com.squareup.javapoet.*;
import es.rubenjgarcia.beanctional.generator.annotation.FunctionalBean;
import es.rubenjgarcia.beanctonial.core.FunctionalClass;
import es.rubenjgarcia.beanctonial.core.FunctionalNumber;
import es.rubenjgarcia.beanctonial.core.FunctionalString;
import es.rubenjgarcia.commons.functional.FlowStream;
import es.rubenjgarcia.commons.functional.FunctionalExceptions.Consumer_WithExceptions;
import es.rubenjgarcia.commons.functional.FunctionalFilters;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static es.rubenjgarcia.commons.functional.FunctionalExceptions.rethrowConsumer;
import static es.rubenjgarcia.commons.functional.FunctionalFilters.findFirst;

public class FunctionalBeanProcessor extends AbstractProcessor {

    private Elements elementUtils;
    private Filer filer;

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(FunctionalBean.class.getCanonicalName());
        return annotations;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        roundEnv.getElementsAnnotatedWith(FunctionalBean.class)
                .stream()
                .filter(e -> e.getKind().isClass())
                .forEach(rethrowConsumer(generateFunctionalBean()));

        return true;
    }

    private Consumer_WithExceptions<Element> generateFunctionalBean() {
        return e -> {
            TypeElement typeElement = (TypeElement) e;
            Name qualifiedName = typeElement.getQualifiedName();
            PackageElement pkg = elementUtils.getPackageOf(e);
            Name name = elementUtils.getTypeElement(qualifiedName).getSimpleName();

            String uncapitalizeName = StringUtils.uncapitalize(name.toString());
            ClassName functionalClassName = ClassName.get(pkg.toString(), "F" + name.toString());
            ClassName declaringClass = ClassName.get(pkg.toString(), name.toString());


            Function<Class, Predicate<Element>> elementsPredicate = i -> el -> el.asType().toString().equals(i.getCanonicalName());
            List<Element> enclosedElements = (List<Element>) typeElement.getEnclosedElements();
            List<Element> fields = enclosedElements.stream()
                    .filter(el -> el.getKind().isField())
                    .filter(FunctionalFilters.anyOf(elementsPredicate.apply(String.class), elementsPredicate.apply(Byte.class)))
                    .collect(Collectors.toList());

            List<Element> getters = ElementFilter.methodsIn(elementUtils.getAllMembers(typeElement)).stream()
                    .filter(m -> ((ExecutableElement) m).getParameters().size() == 0 && m.getSimpleName().toString().startsWith("get"))
                    .collect(Collectors.toList());

            List<FieldSpec> fieldSpecs = FlowStream.mapIfAny(fields, elementPredicate(String.class), elementFieldSpec(FunctionalString.class))
                    .elseIfAnyMap(elementPredicate(Byte.class), elementFieldSpec(FunctionalNumber.class))
                    .collect(Collectors.toList());

            ParameterSpec beanParameter = ParameterSpec.builder(declaringClass, uncapitalizeName)
                    .addModifiers(Modifier.FINAL)
                    .build();

            MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(beanParameter)
                    .addStatement("super($N)", uncapitalizeName)
                    .beginControlFlow("if ($N != null)", uncapitalizeName);

            FlowStream.mapIfAny(fields, elementPredicate(String.class), elementMapper(getters, beanParameter, constructorBuilder, FunctionalString.class, "FString"))
                    .elseIfAnyMap(elementPredicate(Byte.class), elementMapper(getters, beanParameter, constructorBuilder, FunctionalNumber.class, "FNumber"))
                    .count(); // FIXME Foreach

            MethodSpec constructor = constructorBuilder
                    .endControlFlow()
                    .build();

            MethodSpec staticConstructor = MethodSpec.methodBuilder(name.toString())
                    .addModifiers(Modifier.STATIC, Modifier.PUBLIC)
                    .addParameter(beanParameter)
                    .returns(functionalClassName)
                    .addStatement("return new $T($N)", functionalClassName, uncapitalizeName)
                    .build();

            MethodSpec staticEmptyConstructor = MethodSpec.methodBuilder(name.toString())
                    .addModifiers(Modifier.STATIC, Modifier.PUBLIC)
                    .returns(functionalClassName)
                    .addStatement("return $N(new $T())", name.toString(), declaringClass)
                    .build();

            List<MethodSpec> predicateFields = FlowStream.mapIfAny(fields, elementPredicate(String.class), predicatesField(functionalClassName, FunctionalString.class))
                    .elseIfAnyMap(elementPredicate(Byte.class), predicatesField(functionalClassName, FunctionalNumber.class))
                    .collect(Collectors.toList());

            TypeSpec functionalClass = TypeSpec.classBuilder(functionalClassName)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .superclass(ParameterizedTypeName.get(ClassName.get(FunctionalClass.class), TypeName.get(e.asType())))
                    .addFields(fieldSpecs)
                    .addMethod(constructor)
                    .addMethod(staticConstructor)
                    .addMethod(staticEmptyConstructor)
                    .addMethods(predicateFields)
                    .build();

            JavaFile javaFile = JavaFile.builder(pkg.toString(), functionalClass).build();
            javaFile.writeTo(filer);
        };
    }

    private Function<Element, MethodSpec> predicatesField(ClassName fClass, Class<? extends FunctionalClass> superFClass) {
        return f -> {
            ParameterSpec predicate = ParameterSpec.builder(ParameterizedTypeName.get(ClassName.get(Predicate.class), WildcardTypeName.supertypeOf(superFClass)), "ps")
                    .addModifiers(Modifier.FINAL)
                    .build();

            return MethodSpec.methodBuilder(f.getSimpleName().toString())
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .addParameter(predicate)
                    .returns(ParameterizedTypeName.get(ClassName.get(Predicate.class), WildcardTypeName.supertypeOf(fClass)))
                    .addStatement("return pb -> ps.test(pb.$N)", f.getSimpleName().toString())
                    .build();
        };
    }

    private Function<Element, FieldSpec> elementFieldSpec(Class<? extends FunctionalClass> fClass) {
        return f -> FieldSpec.builder(fClass, f.getSimpleName().toString())
                .addModifiers(Modifier.PRIVATE)
                .build();
    }

    private Predicate<Element> elementPredicate(Class clazz) {
        return f -> f.asType().toString().equals(clazz.getCanonicalName());
    }

    private Function<Element, Optional<MethodSpec.Builder>> elementMapper(List<Element> getters, ParameterSpec beanParameter, MethodSpec.Builder constructorBuilder, Class<? extends FunctionalClass> fClass, String fName) {
        return f -> {
            String fieldName = f.getSimpleName().toString();
            return findFirst(getters.stream(), g -> g.getSimpleName().toString().equalsIgnoreCase("get" + fieldName))
                    .map(g -> constructorBuilder.addStatement("$N = $T.$N($N.$N())", fieldName, TypeName.get(fClass), fName, beanParameter, g.getSimpleName().toString()));
        };
    }
}
