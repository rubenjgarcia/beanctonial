package es.rubenjgarcia.beanctonial.core.functional;

import java.util.function.Predicate;

public class FunctionalFilters {

    public static <T> Predicate<T> oneOf(Predicate<T> p1, Predicate<T> p2) {
        return p -> p1.test(p) || p2.test(p);
    }

    public static <T> Predicate<T> bothOf(Predicate<T> p1, Predicate<T> p2) {
        return p -> p1.test(p) && p2.test(p);
    }

    public static <T> Predicate<T> noneOf(Predicate<T> p1, Predicate<T> p2) {
        return p -> !(p1.test(p)) && !(p2.test(p));
    }
}
