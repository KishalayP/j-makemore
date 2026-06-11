package org.kp.ai.makemore.model;

import junit.framework.TestCase;

import static org.junit.Assert.assertArrayEquals;

public class MatrixTest extends TestCase {

    public void testConstructorRejectsNonPositiveSizes() {
        try {
            new Matrix<>(Integer.class, 0, 3, 0);
            fail("Expected IllegalArgumentException for non-positive row size");
        } catch (IllegalArgumentException expected) {
            // ok
        }
    }

    public void testSupplierConstructorRejectsNullSupplier() {
        try {
            new Matrix<>(Integer.class, 2, 2, null, 0);
            fail("Expected IllegalArgumentException for null supplier");
        } catch (IllegalArgumentException expected) {
            // ok
        }
    }

    public void testGetValueRejectsNegativeRow() {
        Matrix<Integer> m = new Matrix<>(Integer.class, 2, 3, 0);
        try {
            m.getValue(-1, 0);
            fail("Expected IllegalArgumentException for negative row");
        } catch (IllegalArgumentException expected) {
            // ok
        }
    }

    public void testGetValueRejectsRowTooLarge() {
        Matrix<Integer> m = new Matrix<>(Integer.class, 2, 3, 0);
        try {
            m.getValue(2, 0);
            fail("Expected IllegalArgumentException for row too large");
        } catch (IllegalArgumentException expected) {
            // ok
        }
    }

    public void testGetValueRejectsNegativeColumn() {
        Matrix<Integer> m = new Matrix<>(Integer.class, 2, 3, 0);
        try {
            m.getValue(0, -1);
            fail("Expected IllegalArgumentException for negative column");
        } catch (IllegalArgumentException expected) {
            // ok
        }
    }

    public void testGetValueRejectsColumnTooLarge() {
        Matrix<Integer> m = new Matrix<>(Integer.class, 2, 3, 0);
        try {
            m.getValue(0, 3);
            fail("Expected IllegalArgumentException for column too large");
        } catch (IllegalArgumentException expected) {
            // ok
        }
    }

    public void testSetRowRejectsNullArray() {
        Matrix<Integer> m = new Matrix<>(Integer.class, 2, 2, 0);
        try {
            m.setRow(0, null);
            fail("Expected IllegalArgumentException for null values array");
        } catch (IllegalArgumentException expected) {
            // ok
        }
    }

    public void testSetColumnRejectsNullArray() {
        Matrix<Integer> m = new Matrix<>(Integer.class, 2, 2, 0);
        try {
            m.setColumn(0, null);
            fail("Expected IllegalArgumentException for null values array");
        } catch (IllegalArgumentException expected) {
            // ok
        }
    }

    public void testSetColumnWritesCorrectIndicesAndGettersReturnExpectedValues() {
        Matrix<Integer> m = new Matrix<>(Integer.class, 3, 4, 0);
        Integer[] values = new Integer[]{10, 20, 30};
        m.setColumn(2, values);

        assertEquals(Integer.valueOf(10), m.getValue(0, 2));
        assertEquals(Integer.valueOf(20), m.getValue(1, 2));
        assertEquals(Integer.valueOf(30), m.getValue(2, 2));

        // other cells unchanged (default 0)
        assertEquals(Integer.valueOf(0), m.getValue(0, 0));
    }

    public void testGetRowAndGetColumnReturnExpectedArrays() {
        Matrix<Integer> m = new Matrix<>(Integer.class, 3, 3, 0);
        Integer[] row1 = new Integer[]{1, 2, 3};
        m.setRow(1, row1);

        Integer[] readRow = m.getRow(1);
        assertArrayEquals("Rows should be equal", row1, readRow);

        Integer[] expectedCol0 = new Integer[]{0, 1, 0};
        Integer[] readCol = m.getColumn(0);
        assertArrayEquals("Columns should be equal", expectedCol0, readCol);
    }

    public void testDefaultValueInitializationWorks() {
        Matrix<String> m = new Matrix<>(String.class, 2, 2, "X");
        assertEquals("X", m.getValue(0, 0));
        assertEquals("X", m.getValue(1, 1));
    }
}
