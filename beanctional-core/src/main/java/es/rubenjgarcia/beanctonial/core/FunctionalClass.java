package es.rubenjgarcia.beanctonial.core;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static es.rubenjgarcia.commons.functional.FunctionalExceptions.rethrowFunction;

public abstract class FunctionalClass<A> implements Serializable, Comparable<FunctionalClass<A>> {

    public FunctionalClass(A a) {
        this.value = a;
    }

    protected A value;

    public int compareTo(FunctionalClass<A> o) {
        if (value == null && o.value == null) {
            return 0;
        } else if (value == null) {
            return Integer.MAX_VALUE;
        } else if (o.value == null) {
            return Integer.MIN_VALUE;
        } else if (Comparable.class.isAssignableFrom(value.getClass())) {
            return ((Comparable) value).compareTo(o.value);
        }

        throw new IllegalArgumentException("Can't compare class");
    }

    public FunctionalClass<A> clone() throws CloneNotSupportedException {
        try {
            return newInstance((Class<FunctionalClass<A>>) this.getClass(), value);
        } catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new CloneNotSupportedException(e.getMessage());
        }
    }

    public static <A, B extends FunctionalClass<A>> List<B> toFunctionalList(List<A> aList, Class<B> clazz) {
        if (aList == null) {
            return null;
        } else if (aList.size() == 0) {
            return Collections.emptyList();
        }
        
        return (List<B>) aList.stream().map(rethrowFunction(a -> toFunctional(a, clazz))).collect(Collectors.toList());
    }

    private static <A, B extends FunctionalClass<A>> FunctionalClass<A> toFunctional(A a, Class<B> clazz) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return newInstance((Class<FunctionalClass<A>>) clazz, a);
    }

    private static <A> FunctionalClass<A> newInstance(Class<FunctionalClass<A>> clazz, A value) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<? extends FunctionalClass> constructor = (Constructor<? extends FunctionalClass>) clazz.getDeclaredConstructors()[0];
        return constructor.newInstance(value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof FunctionalClass)) return false;

        FunctionalClass otherMyClass = (FunctionalClass) other;
        if (otherMyClass.value == null && value == null){
            return true;
        } else if (otherMyClass.value == null || value == null) {
            return false;
        }

        if (!(otherMyClass.value.getClass().isAssignableFrom(value.getClass()))) return false;
        return otherMyClass.value.equals(value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value == null ? null : value.toString();
    }
}
