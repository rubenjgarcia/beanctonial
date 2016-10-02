package es.rubenjgarcia.beanctonial.core.tests;

import es.rubenjgarcia.beanctonial.core.FunctionalString;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static es.rubenjgarcia.beanctonial.core.FunctionalString.*;
import static org.junit.Assert.*;

public class FunctionalStringTestCase {

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
    public void canCloneFunctionalStrings() throws CloneNotSupportedException {
        FunctionalString fString = FString("FString");
        FunctionalString fString2 = (FunctionalString) fString.clone();
        assertTrue("Functional String clone", fString.equals(fString2));
    }

    @Test
    public void cantCreateFunctionalStringWithNullString() {
        FunctionalString fString = FString(null);
        assertNotNull("FunctionalString not null",fString);
    }

    @Test
    public void canCreateFunctionalStringListFromStringList() {
        List<String> strings = Arrays.asList("One", null);
        List<FunctionalString> fStrings = toFunctionalList(strings, FunctionalString.class);
        assertEquals("List size", 2, fStrings.size());
        Assert.assertEquals("List elements", FString("One"), fStrings.get(0));
    }

    @Test
    public void canFilterFunctionalStringsByLength() {
        List<FunctionalString> fStrings = Arrays.asList(FString("aa"), FString("bbb"), FString("cccc"), FString(null));
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

    @Test
    public void canFilterByString() {
        List<FunctionalString> fStrings = Arrays.asList(FString("aa"), FString("bbb"), FString("cccc"), FString(null));
        List<FunctionalString> fStringsFilteredByString = fStrings
                .stream()
                .filter(string("bbb"))
                .collect(Collectors.toList());
        assertEquals("Filtered by string length", 1, fStringsFilteredByString.size());
        Assert.assertEquals("Filtered by string", FString("bbb"), fStringsFilteredByString.get(0));

        List<FunctionalString> fStringsFilteredByString2 = fStrings
                .stream()
                .filter(string(null))
                .collect(Collectors.toList());
        assertEquals("Filtered by string length", 1, fStringsFilteredByString2.size());
        Assert.assertEquals("Filtered by string", FString(null), fStringsFilteredByString2.get(0));

        List<FunctionalString> fStringsFilteredByStart = fStrings
                .stream()
                .filter(startsWith("a"))
                .collect(Collectors.toList());
        assertEquals("Filtered by start length", 1, fStringsFilteredByStart.size());
        Assert.assertEquals("Filtered by start", FString("aa"), fStringsFilteredByStart.get(0));

        List<FunctionalString> fStringsFilteredByEnd = fStrings
                .stream()
                .filter(endsWith("cc"))
                .collect(Collectors.toList());
        assertEquals("Filtered by end length", 1, fStringsFilteredByEnd.size());
        Assert.assertEquals("Filtered by end", FString("cccc"), fStringsFilteredByEnd.get(0));
    }

    @Test
    public void canOrderFunctionalStrings() {
        List<FunctionalString> fStrings = Arrays.asList(FString("b"), FString("c"), FString("a"), FString(null));
        List<FunctionalString> fStringsSorted = fStrings
                .stream()
                .sorted()
                .collect(Collectors.toList());
        Assert.assertEquals("FunctionalString sorted", FString("a"), fStringsSorted.get(0));
        Assert.assertEquals("FunctionalString sorted", FString("b"), fStringsSorted.get(1));
        Assert.assertEquals("FunctionalString sorted", FString("c"), fStringsSorted.get(2));
        Assert.assertEquals("FunctionalString sorted", FString(null), fStringsSorted.get(3));
    }

    @Test
    public void canCompareByLength() {
        List<FunctionalString> fStrings = Arrays.asList(FString("aa"), FString("aaa"), FString("a"), FString(null));
        FunctionalString maxFString = fStrings
                .stream()
                .max(byLength())
                .get();
        Assert.assertEquals("FunctionalString compared by length", FString("aaa"), maxFString);

        FunctionalString minFString = fStrings
                .stream()
                .min(byLength())
                .get();
        Assert.assertEquals("FunctionalString compared by length", FString(null), minFString);
    }
}
