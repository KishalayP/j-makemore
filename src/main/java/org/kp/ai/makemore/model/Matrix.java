package org.kp.ai.makemore.model;

import java.lang.reflect.Array;
import java.util.function.Supplier;

public class Matrix<T> {

    private final T[] flatArray;
    private final int rowSize;
    private final int columnSize;
    private final int capacity;
    private final Class<T> clazz;

    public Matrix(Class<T> clazz, int rowSize, int columnSize) {
        this.clazz = clazz;
        this.rowSize = rowSize;
        this.columnSize = columnSize;
        this.capacity = rowSize * columnSize;
        this.flatArray = (T[]) Array.newInstance(clazz, columnSize * rowSize);
    }

    public Matrix(Class<T> clazz, int rowSize, int columnSize, Supplier<? extends T> supplier) {
        this.clazz = clazz;
        this.rowSize = rowSize;
        this.columnSize = columnSize;
        this.capacity = rowSize * columnSize;
        this.flatArray = (T[]) Array.newInstance(clazz, columnSize * rowSize);
        for (int i = 0; i < flatArray.length; i++) {
            flatArray[i] = supplier.get();
        }
    }

    public int getRowSize() {
        return rowSize;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void setValue(int row, int column, T value) {
        rowValidations(row, 1);
        columnValidations(column, 1);
        flatArray[getRowStartInFlatArr(row) + column] = value;
    }

    public void setRow(int row, T[] values) {
        rowValidations(row, values.length);
        int rowStartInFlatArr = getRowStartInFlatArr(row);
        for (int i = rowStartInFlatArr; i < rowStartInFlatArr + values.length; i++) {
            flatArray[i] = values[i - rowStartInFlatArr];
        }
    }

    public void setColumn(int column, T[] values) {
        columnValidations(column, values.length);
        int j = 0;
        for (int i = column; i < values.length; i += columnSize) {
            flatArray[i] = values[j++];
        }
    }

    private void rowValidations(int row, int valueSize) {
        if (row > rowSize && row <= 0) {
            throw new IllegalArgumentException("Row must be less than row size and greater than -1");
        }
        if (valueSize > 1 && valueSize != rowSize) {
            throw new IllegalArgumentException("No. of Values must be equal to row size");
        }
    }

    private void columnValidations(int column, int valueSize) {
        if (column > columnSize && column <= 0) {
            throw new IllegalArgumentException("Column must be less than column size and greater than -1");
        }
        if (valueSize > 1 && valueSize != rowSize) {
            throw new IllegalArgumentException("No. of Values must be equal to column size");
        }
    }

    public T getValue(int row, int column) {
        return flatArray[getRowStartInFlatArr(row) + column];
    }

    public T[] getRow(int row) {
        var result = (T[]) Array.newInstance(clazz, rowSize);
        rowValidations(row, result.length);
        int rowStartInFlatArr = getRowStartInFlatArr(row);
        for (int i = rowStartInFlatArr; i < rowStartInFlatArr + columnSize; i++) {
            result[i - rowStartInFlatArr] = flatArray[i];
        }
        return result;
    }

    public T[] getColumn(int column) {
        var result = (T[]) Array.newInstance(clazz, columnSize);
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
        sb.append("Matrix { rows:").append(rowSize).append(", columns:").append(columnSize).append(", type: ").append(clazz.getTypeName());
        sb.append(", data: [\n[");
        for (int i = 0; i < capacity; i++) {
            sb.append(flatArray[i]);
            if (i != 0 && i % columnSize == 0) {
                sb.append("],\n[");
            } else if (i == capacity - 1) {
                sb.append("]\n");
            } else {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private int getRowStartInFlatArr(int row) {
        return row * columnSize;
    }

}
