package es.rubenjgarcia.beanctonial;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FunctionalString implements Serializable {

    private final String s;

    private FunctionalString(String s) {
        this.s = s;
    }

    public static FunctionalString FString(String s) {
        if (s == null) {
            throw new IllegalArgumentException("String can't be null");
        }

        return new FunctionalString(s);
    }

    public static List<FunctionalString> FString(List<String> strings) {
        if (strings == null) {
            return null;
        } else if (strings.size() == 0) {
            return Collections.emptyList();
        }

        return strings.stream().map(FunctionalString::FString).collect(Collectors.toList());
    }

    private static Predicate<? super FunctionalString> length(int length, Condition condition) {
        switch (condition) {
            case EQUALS:
                return p -> p.s.length() == length;
            case LESS:
                return p -> p.s.length() < length;
            case LESS_EQUALS:
                return p -> p.s.length() <= length;
            case GREATER:
                return p -> p.s.length() > length;
            case GREATER_EQUALS:
                return p -> p.s.length() >= length;
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

    @Override
    public String toString() {
        return s;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof FunctionalString)) return false;
        FunctionalString otherMyClass = (FunctionalString) other;
        return otherMyClass.s.equals(s);
    }

    @Override
    public int hashCode() {
        return s.hashCode();
    }

    public FunctionalString clone() {
        return FString(s);
    }
}
