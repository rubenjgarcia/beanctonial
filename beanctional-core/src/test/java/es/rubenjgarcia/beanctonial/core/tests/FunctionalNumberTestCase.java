package es.rubenjgarcia.beanctonial.core.tests;

import es.rubenjgarcia.beanctonial.core.FunctionalNumber;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static es.rubenjgarcia.beanctonial.core.FunctionalNumber.*;
import static es.rubenjgarcia.beanctonial.core.tests.TestHelper.*;
import static org.junit.Assert.*;

public class FunctionalNumberTestCase {

    @Test
    public void canCreateFunctionalNumberFromTypes() {
        FunctionalNumber fNumberByte = FNumber((byte) 1);
        assertEquals("FunctionalNumber byte created", "1", fNumberByte.toString());

        FunctionalNumber fNumberShort = FNumber((short) 1);
        assertEquals("FunctionalNumber short created", "1", fNumberShort.toString());

        FunctionalNumber fNumberInt = FNumber(1);
        assertEquals("FunctionalNumber int created", "1", fNumberInt.toString());

        FunctionalNumber fNumberLong = FNumber((long) 1);
        assertEquals("FunctionalNumber long created", "1", fNumberLong.toString());

        FunctionalNumber fNumberFloat = FNumber((float) 1);
        assertEquals("FunctionalNumber float created", "1.0", fNumberFloat.toString());

        FunctionalNumber fNumberDouble = FNumber((double) 1);
        assertEquals("FunctionalNumber double created", "1.0", fNumberDouble.toString());
    }

    @Test
    public void canCompareFunctionalNumbers() {
        FunctionalNumber fNumberByte = FNumber((byte) 1);
        FunctionalNumber fNumberByte2 = FNumber((byte) 1);
        assertTrue("FunctionalNumber byte equals", fNumberByte.equals(fNumberByte2));

        FunctionalNumber fNumberShort = FNumber((short) 1);
        FunctionalNumber fNumberShort2 = FNumber((short) 1);
        assertTrue("FunctionalNumber short equals", fNumberShort.equals(fNumberShort2));

        FunctionalNumber fNumberInt = FNumber(1);
        FunctionalNumber fNumberInt2 = FNumber(1);
        assertTrue("FunctionalNumber int equals", fNumberInt.equals(fNumberInt2));

        FunctionalNumber fNumberLong = FNumber((long) 1);
        FunctionalNumber fNumberLong2 = FNumber((long) 1);
        assertTrue("FunctionalNumber long equals", fNumberLong.equals(fNumberLong2));

        FunctionalNumber fNumberFloat = FNumber((float) 1);
        FunctionalNumber fNumberFloat2 = FNumber((float) 1);
        assertTrue("FunctionalNumber float equals", fNumberFloat.equals(fNumberFloat2));

        FunctionalNumber fNumberDouble = FNumber((double) 1);
        FunctionalNumber fNumberDouble2 = FNumber((double) 1);
        assertTrue("FunctionalNumber double equals", fNumberDouble.equals(fNumberDouble2));

        assertFalse("FunctionalNumber not equals", fNumberByte.equals(fNumberShort));
        assertFalse("FunctionalNumber not equals", fNumberInt.equals(fNumberLong));
        assertFalse("FunctionalNumber not equals", fNumberFloat.equals(fNumberDouble));
    }

    @Test
    public void canCloneFunctionalNumbers() throws CloneNotSupportedException {
        FunctionalNumber fNumberByte = FNumber((byte) 1);
        FunctionalNumber fNumberByte2 = (FunctionalNumber) fNumberByte.clone();
        assertTrue("FunctionalNumber byte clone", fNumberByte.equals(fNumberByte2));

        FunctionalNumber fNumberShort = FNumber((short) 1);
        FunctionalNumber fNumberShort2 = (FunctionalNumber) fNumberShort.clone();
        assertTrue("FunctionalNumber short clone", fNumberShort.equals(fNumberShort2));

        FunctionalNumber fNumberInt = FNumber(1);
        FunctionalNumber fNumberInt2 = (FunctionalNumber) fNumberInt.clone();
        assertTrue("FunctionalNumber int clone", fNumberInt.equals(fNumberInt2));

        FunctionalNumber fNumberLong = FNumber((long) 1);
        FunctionalNumber fNumberLong2 = (FunctionalNumber) fNumberLong.clone();
        assertTrue("FunctionalNumber long clone", fNumberLong.equals(fNumberLong2));

        FunctionalNumber fNumberFloat = FNumber((float) 1);
        FunctionalNumber fNumberFloat2 = (FunctionalNumber) fNumberFloat.clone();
        assertTrue("FunctionalNumber float clone", fNumberFloat.equals(fNumberFloat2));

        FunctionalNumber fNumberDouble = FNumber((double) 1);
        FunctionalNumber fNumberDouble2 = (FunctionalNumber) fNumberDouble.clone();
        assertTrue("FunctionalNumber double clone", fNumberDouble.equals(fNumberDouble2));
    }

    @Test
    public void cantCreateFunctionalNumberWithNullValue() {
        FunctionalNumber fNumber = FNumber(null);
        assertNotNull("FunctionalNumber not null", fNumber);
    }

    @Test
    public void canCreateFunctionalNumberListFromNumberList() {
        List<Number> numbers = Arrays.asList((byte) 1, (short) 1, 1, (long) 1, (double) 1);
        List<FunctionalNumber> fNumbers = toFunctionalList(numbers, FunctionalNumber.class);
        assertEquals("List size", 5, fNumbers.size());
        assertEquals("List elements", FNumber((byte) 1), fNumbers.get(0));
        assertEquals("List elements", FNumber((short) 1), fNumbers.get(1));
        assertEquals("List elements", FNumber(1), fNumbers.get(2));
        assertEquals("List elements", FNumber((long) 1), fNumbers.get(3));
        assertEquals("List elements", FNumber((double) 1), fNumbers.get(4));
    }

    @Test
    public void canFilterFunctionalNumbersByValue() {
        List<FunctionalNumber> fNumbers = Arrays.asList(FNumber((byte) 1), FNumber((short) 2), FNumber(3), FNumber((long) 4), FNumber((double) 5), FNumber(null));
        long valueEq3 = fNumbers
                .stream()
                .filter(eq(3))
                .count();
        assertEquals("Value equals", 1, valueEq3);

        long valueGt3 = fNumbers
                .stream()
                .filter(gt(3))
                .count();
        assertEquals("Value greater than", 2, valueGt3);

        long valueGte3 = fNumbers
                .stream()
                .filter(gte(3))
                .count();
        assertEquals("Value greater than or equals", 3, valueGte3);

        long valueLt3 = fNumbers
                .stream()
                .filter(lt(3))
                .count();
        assertEquals("Value less than", 2, valueLt3);

        long valueLte3 = fNumbers
                .stream()
                .filter(lte(3))
                .count();
        assertEquals("Value less than or equals", 3, valueLte3);

        long valueNull = fNumbers
                .stream()
                .filter(eq(null))
                .count();
        assertEquals("Value equals null", 1, valueNull);

        assertThrows(IllegalArgumentException.class, () -> fNumbers.stream().filter(gt(null)).count());
        assertThrows(IllegalArgumentException.class, () -> fNumbers.stream().filter(gte(null)).count());
        assertThrows(IllegalArgumentException.class, () -> fNumbers.stream().filter(lt(null)).count());
        assertThrows(IllegalArgumentException.class, () -> fNumbers.stream().filter(gte(null)).count());
    }

    @Test
    public void canOrderFunctionalNumbers() {
        List<FunctionalNumber> fNumbers = Arrays.asList(FNumber((byte) 3), FNumber((short) 2), FNumber(5), FNumber((long) 1), FNumber((double) 4), FNumber(null));
        List<FunctionalNumber> fNumbersSorted = fNumbers
                .stream()
                .sorted()
                .collect(Collectors.toList());
        assertEquals("FunctionalNumber sorted", FNumber((long) 1), fNumbersSorted.get(0));
        assertEquals("FunctionalNumber sorted", FNumber((short) 2), fNumbersSorted.get(1));
        assertEquals("FunctionalNumber sorted", FNumber((byte) 3), fNumbersSorted.get(2));
        assertEquals("FunctionalNumber sorted", FNumber((double) 4), fNumbersSorted.get(3));
        assertEquals("FunctionalNumber sorted", FNumber(5), fNumbersSorted.get(4));
        assertEquals("FunctionalNumber sorted", FNumber(null), fNumbersSorted.get(5));
    }
}
