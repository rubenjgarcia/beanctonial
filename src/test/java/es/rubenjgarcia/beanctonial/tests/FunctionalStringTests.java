package es.rubenjgarcia.beanctonial.tests;

import es.rubenjgarcia.beanctonial.FunctionalString;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static es.rubenjgarcia.beanctonial.FunctionalString.*;
import static es.rubenjgarcia.beanctonial.test.TestHelper.*;
import static org.junit.Assert.*;

public class FunctionalStringTests {

    @Test
    public void canCreateFunctionalStringFromString() {
        FunctionalString fString = FString("FString");
        assertEquals("FunctionalString created", "FString", fString.toString());
    }

    @Test
    public void canCompareFunctionalStrings() {
        FunctionalString fString = FString("FString");
        FunctionalString fString2 = FString("FString");
        assertTrue("Functional String equals", fString.equals(fString2));
    }

    @Test
    public void canCloneFunctionalStrings() {
        FunctionalString fString = FString("FString");
        FunctionalString fString2 = fString.clone();
        assertTrue("Functional String equals", fString.equals(fString2));
    }

    @Test
    public void cantCreateFunctionalStringWithNullString() {
        assertThrows(IllegalArgumentException.class, () -> FString((String) null));
    }

    @Test
    public void canCreateFunctionaStringListFromStringList() {
        List<String> strings = Arrays.asList("One");
        List<FunctionalString> fStrings = FString(strings);
        assertEquals("List size", 1, fStrings.size());
        assertEquals("List elements", FString("One"), fStrings.get(0));
    }

    @Test
    public void canFilterStringsByLength() {
        List<FunctionalString> fStrings = Arrays.asList(FString("aa"), FString("bbb"), FString("cccc"));
        long lengthEq3 = fStrings
                .stream()
                .filter(lengthEq(3))
                .count();
        assertEquals("Length equals", 1, lengthEq3);

        long lengthGt3 = fStrings
                .stream()
                .filter(lengthGt(3))
                .count();
        assertEquals("Length greater than", 1, lengthGt3);

        long lengthGte3 = fStrings
                .stream()
                .filter(lengthGte(3))
                .count();
        assertEquals("Length greater than or equals", 2, lengthGte3);

        long lengthLt3 = fStrings
                .stream()
                .filter(lengthLt(3))
                .count();
        assertEquals("Length less than", 1, lengthLt3);

        long lengthLte3 = fStrings
                .stream()
                .filter(lengthLte(3))
                .count();
        assertEquals("Length less than or equals", 2, lengthLte3);
    }
}
