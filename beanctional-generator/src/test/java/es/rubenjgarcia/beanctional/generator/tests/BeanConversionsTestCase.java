package es.rubenjgarcia.beanctional.generator.tests;

import es.rubenjgarcia.beanctonial.core.FunctionalClass;
import es.rubenjgarcia.beanctonial.core.FunctionalString;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class BeanConversionsTestCase {

    @Test
    public void canGenerateFunctionalBeanClass() {
        Bean beanA = new Bean();
        beanA.setMyString("A");

        List<FBean> fBeans = Arrays.asList(FBean.Bean(beanA), FBean.Bean());
        assertEquals("FunctionalClass list length", 2, fBeans.size());
    }

    @Test
    public void canGenerateFunctionalBeanClassWithNullBean() {
        FBean fBean = FBean.Bean(null);
        assertNotNull("FunctionalClass is not null", fBean);
    }

    @Test
    public void canGeneratePredicatesInFunctionalBeanClass() {
        Bean beanA = new Bean();
        beanA.setMyString("A");

        Bean beanB = new Bean();
        beanB.setMyString("B");

        List<Bean> beans = Arrays.asList(beanA, beanB);
        List<FBean> fBeans = FunctionalClass.toFunctionalList(beans, FBean.class);
        long count = fBeans.stream().filter(FBean.myString(FunctionalString.startsWith("A"))).count();
        assertEquals("Predicate length", 1, count);
    }
}
