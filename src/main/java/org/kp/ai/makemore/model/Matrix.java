package org.kp.ai.makemore.model;

import java.lang.reflect.Array;
import java.util.function.Supplier;

public class Matrix<T> {

    private final T[] flatArray;
    private final int rowSize;
    private final int columnSize;
    private final int capacity;
    private final Class<T> clazz;
    private final T defaultValue;

    @SuppressWarnings("unchecked")
    public Matrix(Class<T> clazz, int rowSize, int columnSize, T defaultValue) {
        constructorValidations(clazz, rowSize, columnSize);
        this.clazz = clazz;
        this.rowSize = rowSize;
        this.columnSize = columnSize;
        this.capacity = rowSize * columnSize;
        this.flatArray = (T[]) Array.newInstance(clazz, capacity);
        this.defaultValue = defaultValue;
        if (defaultValue != null) {
            for (int i = 0; i < capacity; i++) {
                flatArray[i] = defaultValue;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public Matrix(Class<T> clazz, int rowSize, int columnSize, Supplier<? extends T> supplier, T defaultValue) {
        constructorValidations(clazz, rowSize, columnSize);
        this.clazz = clazz;
        this.rowSize = rowSize;
        this.columnSize = columnSize;
        this.defaultValue = defaultValue;
        this.capacity = rowSize * columnSize;
        this.flatArray = (T[]) Array.newInstance(clazz, capacity);
        if (supplier == null) {
            throw new IllegalArgumentException("Supplier cannot be null.");
        }
        for (int i = 0; i < flatArray.length; i++) {
            flatArray[i] = getDefaultValIfNull(supplier.get());
        }
    }

    public int getRowSize() {
        return rowSize;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void setValue(int row, int column, T value) {
        inputValueValidation(value);
        rowValidations(row, 1);
        columnValidations(column, 1);
        flatArray[getRowStartInFlatArr(row) + column] = getDefaultValIfNull(value);
    }

    public void setRow(int row, T[] values) {
        inputValueValidation(values);
        rowValidations(row, values.length);
        int rowStartInFlatArr = getRowStartInFlatArr(row);
        for (int i = rowStartInFlatArr; i < rowStartInFlatArr + values.length; i++) {
            flatArray[i] = getDefaultValIfNull(values[i - rowStartInFlatArr]);
        }
    }

    public void setColumn(int column, T[] values) {
        inputValueValidation(values);
        columnValidations(column, values.length);
        for (int i = 0; i < rowSize; i++) {
            flatArray[getRowStartInFlatArr(i) + column] = getDefaultValIfNull(values[i]);
        }
    }

    public T getValue(int row, int column) {
        rowValidations(row, 1);
        columnValidations(column, 1);
        return flatArray[getRowStartInFlatArr(row) + column];
    }

    @SuppressWarnings("unchecked")
    public T[] getRow(int row) {
        var result = (T[]) Array.newInstance(clazz, columnSize);
        rowValidations(row, result.length);
        int rowStartInFlatArr = getRowStartInFlatArr(row);
        for (int i = rowStartInFlatArr; i < rowStartInFlatArr + columnSize; i++) {
            result[i - rowStartInFlatArr] = flatArray[i];
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public T[] getColumn(int column) {
        var result = (T[]) Array.newInstance(clazz, rowSize);
        columnValidations(column, result.length);
        int j = 0;
        for (int i = column; i < flatArray.length; i += columnSize) {
            result[j++] = flatArray[i];
        }
        return result;
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb.append("Matrix { rows:").append(rowSize)
                .append(", columns:").append(columnSize)
                .append(", type: ").append(clazz.getTypeName())
                .append(", data: [\n");
        for (int r = 0; r < rowSize; r++) {
            sb.append("  [");
            for (int c = 0; c < columnSize; c++) {
                if (c > 0) {
                    sb.append(", ");
                }
                sb.append(flatArray[getRowStartInFlatArr(r) + c]);
            }
            sb.append("]");
            if (r < rowSize - 1) sb.append(",\n");
            else sb.append("\n");
        }
        sb.append("]}");
        return sb.toString();
    }

    private T getDefaultValIfNull(T value) {
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    private void constructorValidations(Class<?> clazz, int rowSize, int columnSize) {
        if (clazz == null) {
            throw new IllegalArgumentException("clazz must not be null");
        }
        if (rowSize <= 0 || columnSize <= 0) {
            throw new IllegalArgumentException("rowSize and columnSize must be > 0");
        }
    }

    private void rowValidations(int row, int valueSize) {
        if (row < 0 || row >= rowSize) {
            throw new IllegalArgumentException("Row index out of bounds: " + row);
        }
        if (valueSize > 1 && valueSize != columnSize) {
            throw new IllegalArgumentException("Number of values for a row must equal column size (" + columnSize + ")");
        }
    }

    private void columnValidations(int column, int valueSize) {
        if (column < 0 || column >= columnSize) {
            throw new IllegalArgumentException("Column index out of bounds: " + column);
        }
        if (valueSize > 1 && valueSize != rowSize) {
            throw new IllegalArgumentException("Number of values for a column must equal row size (" + rowSize + ")");
        }
    }

    private void inputValueValidation(Object input) {
        if(input ==null){
            throw new IllegalArgumentException("Input value cannot be null");
        }
    }

    private int getRowStartInFlatArr(int row) {
        return row * columnSize;
    }

}
