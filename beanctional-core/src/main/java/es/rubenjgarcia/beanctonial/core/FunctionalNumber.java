package es.rubenjgarcia.beanctonial.core;

import java.util.Optional;
import java.util.function.Predicate;

public class FunctionalNumber extends FunctionalClass<Number> {

    public FunctionalNumber(Number number) {
        super(number);
    }

    public static FunctionalNumber FNumber(Number n) {
        return new FunctionalNumber(n);
    }

    private static Predicate<? super FunctionalNumber> value(Number n, Condition condition) {
        if (n == null && condition.equals(Condition.EQUALS)) {
            return p -> p.value == null;
        } else if (n == null) {
            throw new IllegalArgumentException("Condition " + condition + " is not supported with null value");
        }

        switch (condition) {
            case EQUALS:
                return p -> Optional.ofNullable(p.value).map(v -> v.floatValue() == n.floatValue()).orElse(false);
            case LESS:
                return p -> Optional.ofNullable(p.value).map(v -> v.floatValue() < n.floatValue()).orElse(false);
            case LESS_EQUALS:
                return p -> Optional.ofNullable(p.value).map(v -> v.floatValue() <= n.floatValue()).orElse(false);
            case GREATER:
                return p -> Optional.ofNullable(p.value).map(v -> v.floatValue() > n.floatValue()).orElse(false);
            case GREATER_EQUALS:
                return p -> Optional.ofNullable(p.value).map(v -> v.floatValue() >= n.floatValue()).orElse(false);
            default:
                throw new IllegalArgumentException("Condition " + condition + " is not supported");
        }
    }

    public static Predicate<? super FunctionalNumber> eq(Number n) {
        return value(n, Condition.EQUALS);
    }

    public static Predicate<? super FunctionalNumber> gt(Number n) {
        return value(n, Condition.GREATER);
    }

    public static Predicate<? super FunctionalNumber> gte(Number n) {
        return value(n, Condition.GREATER_EQUALS);
    }

    public static Predicate<? super FunctionalNumber> lt(Number n) {
        return value(n, Condition.LESS);
    }

    public static Predicate<? super FunctionalNumber> lte(Number n) {
        return value(n, Condition.LESS_EQUALS);
    }

    @Override
    public int compareTo(FunctionalClass<Number> o) {
        if (value == null && o.value == null) {
            return 0;
        } else if (value == null) {
            return Integer.MAX_VALUE;
        } else if (o.value == null) {
            return Integer.MIN_VALUE;
        }

        return Float.compare(value.floatValue(), o.value.floatValue());
    }
}
