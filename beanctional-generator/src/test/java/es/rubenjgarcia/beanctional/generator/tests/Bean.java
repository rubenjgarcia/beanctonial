package es.rubenjgarcia.beanctional.generator.tests;

import es.rubenjgarcia.beanctonial.core.annotation.FunctionalBean;

@FunctionalBean
public class Bean implements Comparable<Bean> {

    private String myString;

    public String getMyString() {
        return myString;
    }

    public void setMyString(String myString) {
        this.myString = myString;
    }

    @Override
    public int compareTo(Bean o) {
        return 0;
    }
}