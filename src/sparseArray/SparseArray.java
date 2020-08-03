/*
 * SparseArray
 * 稀疏数组
 @author Sirius
 */
package sparseArray;


import java.io.*;

public class SparseArray {
    private int[][] array;
    private int[][] sparse;
    private int free;
    private int rowCount;
    private int columnCount;

    public SparseArray(int[][] input, Integer free) {
        if (input != null && free != null) {
            this.array = input;
            this.rowCount = array.length;
            this.columnCount = array[0].length;
            this.free = free;
            this.sparse();
        }
    }

    public SparseArray(int[][] input, Integer free, boolean reverse) {
        if (input != null && free != null) {
            this.free = free;
            if (reverse) {
                this.sparse = input;
                this.rowCount = input[0][0];
                this.columnCount = input[0][1];
                this.toArray();
            } else {
                this.array = input;
                this.rowCount = array.length;
                this.columnCount = array[0].length;
                this.sparse();
            }
        }

    }

    private void sparse() {
        int notFreeCount = 0;
        // 遍历原始数组，得到原始数组内有效值的个数
        for (int[] ints : array) {
            for (int j = 0; j < array[0].length; j++) {
                if (ints[j] != free) {
                    notFreeCount++;
                }
            }
        }
        // 初始化稀疏数组
        this.sparse = new int[notFreeCount + 1][3];
        this.sparse[0][0] = rowCount;
        this.sparse[0][1] = columnCount;
        this.sparse[0][2] = notFreeCount;
        int index = 1;
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                if (array[row][column] != free) {
                    this.sparse[index][0] = row;
                    this.sparse[index][1] = column;
                    this.sparse[index][2] = array[row][column];
                    index++;
                }
            }
        }
    }

    private void toArray() {
        this.array = new int[this.rowCount][this.columnCount];
        try {
            for (int i = 1; i < sparse.length; i++) {
                int a = sparse[i][0];
                int b = sparse[i][1];
                int c = sparse[i][2];
                this.array[a][b] = c;
            }
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[0].length; j++) {
                    if (array[i][j] == 0 && free != 0) {
                        array[i][j] = this.free;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("出现错误");
            System.out.println(e.getMessage());
        }
    }


    public int[][] getSparse() {
        return sparse;
    }

    public int[][] getArray() {
        return array;
    }

    public static void main(String[] args) throws IOException {
        int[][] input = {{10, 10, 3}, {1, 0, 2}, {2, 4, 10}, {8, 8, 6}};
        SparseArray sparseArray = new SparseArray(input, 1, true);
        int[][] out = sparseArray.getArray();
        for (int[] row : out) {
            for (int column : row) {
                System.out.print(column + "\t");
            }
            System.out.println();
        }
        FileOutputStream fileOutputStream = new FileOutputStream("out.data");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(out);
    }
}
