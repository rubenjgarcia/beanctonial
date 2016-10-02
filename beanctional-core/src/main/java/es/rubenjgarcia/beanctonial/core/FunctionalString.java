package es.rubenjgarcia.beanctonial.core;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

public class FunctionalString extends FunctionalClass<String> {

    public FunctionalString(String s) {
        super(s);
    }

    public static FunctionalString FString(String s) {
        return new FunctionalString(s);
    }

    private static Predicate<? super FunctionalString> length(int length, Condition condition) {
        switch (condition) {
            case EQUALS:
                return p -> Optional.ofNullable(p.value).map(v -> v.length() == length).orElse(false);
            case LESS:
                return p -> Optional.ofNullable(p.value).map(v -> v.length() < length).orElse(false);
            case LESS_EQUALS:
                return p -> Optional.ofNullable(p.value).map(v -> v.length() <= length).orElse(false);
            case GREATER:
                return p -> Optional.ofNullable(p.value).map(v -> v.length() > length).orElse(false);
            case GREATER_EQUALS:
                return p -> Optional.ofNullable(p.value).map(v -> v.length() >= length).orElse(false);
            default:
                throw new IllegalArgumentException("Condition " + condition + " is not supported");
        }
    }

    public static Predicate<? super FunctionalString> lengthEq(int length) {
        return length(length, Condition.EQUALS);
    }

    public static Predicate<? super FunctionalString> lengthGt(int length) {
        return length(length, Condition.GREATER);
    }

    public static Predicate<? super FunctionalString> lengthGte(int length) {
        return length(length, Condition.GREATER_EQUALS);
    }

    public static Predicate<? super FunctionalString> lengthLt(int length) {
        return length(length, Condition.LESS);
    }

    public static Predicate<? super FunctionalString> lengthLte(int length) {
        return length(length, Condition.LESS_EQUALS);
    }

    public static Predicate<? super FunctionalString> string(String s) {
        return p -> Optional.ofNullable(p.value).map(v -> v.equals(s)).orElse(s == null);
    }

    public static Predicate<? super FunctionalString> startsWith(String s) {
        return p -> Optional.ofNullable(p.value).map(v -> v.startsWith(s)).orElse(false);
    }

    public static Predicate<? super FunctionalString> endsWith(String s) {
        return p -> Optional.ofNullable(p.value).map(v -> v.endsWith(s)).orElse(false);
    }

    public static Comparator<? super FunctionalString> byLength() {
        return (o1, o2) -> {
            if (o1.value == null && o2.value == null) {
                return 0;
            } else if (o1.value == null) {
                return Integer.MIN_VALUE;
            } else if (o2.value == null) {
                return Integer.MAX_VALUE;
            } else {
                return o1.value.length() - o2.value.length();
            }
        };
    }
}
