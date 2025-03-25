
//import java.util.Scanner;

public class MatrixOperations {
    public static int countArithmeticOperationsInClassicalMethod = 0;
    public static int countArithmeticOperationsInRecursionMethod = 0;
    public static int countArithmeticOperationsInOptimizedRecursionMethod = 0;
    
    public int[][] sumMatrix(int[][] firthMatrix, int[][] secondMatrix) {
        int[][] sumOfMatrix = new int[firthMatrix.length][getColumnsLength(secondMatrix)];
        if (areMatrixSameLength(firthMatrix, secondMatrix)
                && areMatrixColumnsSameLength(firthMatrix, secondMatrix)) {
            for (int row = 0; row < firthMatrix.length; row++) {
                for (int column = 0; column < firthMatrix[row].length; column++) {
                    sumOfMatrix[row][column] = firthMatrix[row][column] +
                            secondMatrix[row][column];
                }
            }
        }
        return sumOfMatrix;
    }

    // public int[][] getMatrixData(int size, int choice, String matrixName) {
    //     switch (choice) {
    //         case 1:
    //             return inputMatrixFromKeyboard(size, matrixName);
            
    //         case 2:
    //             return generateMatrix1(size);
    //         default:
    //             System.out.println("Неправильний вибір. Використовую генерацію випадкових чисел.");
    //             return generateMatrix2(size);
    //     }
    // }
/* 
    private int[][] inputMatrixFromKeyboard(int size, String matrixName) {
        int[][] matrix = new int[size][size];
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Введіть елементи матриці " + matrixName + ":");
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    System.out.print("Елемент [" + i + "][" + j + "]: ");
                    matrix[i][j] = scanner.nextInt();
                }
            }
        }
        return matrix;
    }
*/
    // Генерація Matrix 1 (центр з нулем)
    public int[][] generateMatrixA(int size) {  
        int [][] matrix = new int[size][size];      
      
        // Заповнюємо всі одиничками
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = 1;
            }
        }
        
        // Центр матриці заповнюємо нулем
        int centerX = size / 2;
        int centerY = size / 2;
        matrix[centerX][centerY] = 0;
        
        return matrix;
    }

    // Генерація Matrix 2 (стабільно заповнена одиничками згори вниз)
    public int[][] generateMatrixB(int size) {  
        int [][] matrix = new int[size][size];      
        // Заповнюємо матрицю за принципом: чим нижче, тим більше одиничок
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (j >= i) {
                    matrix[i][j] = 1;
                } else {
                    matrix[i][j] = 0;
                }
            }
        }

        return matrix;
    }

    public synchronized int[][] classicalMultiplyMatrix(int[][] firthMatrix,
            int[][] secondMatrix) {
        int numberOfRows = getRowsLength(firthMatrix);
        int numberOfColumns = getColumnsLength(secondMatrix);
        int[][] multipleMatrix = new int[numberOfRows][numberOfColumns];
        if (areMatrixValidForMultiple(firthMatrix, secondMatrix)) {
            for (int row = 0; row < numberOfRows; row++) {
                for (int column = 0; column < numberOfColumns; column++) {
                    multipleMatrix[row][column] = multipleRowsAndColumns(
                            firthMatrix[row],
                            generateColumnMatrix(secondMatrix, column));
                    countArithmeticOperationsInClassicalMethod++;
                }
            }
        }
        return multipleMatrix;
    }

    public synchronized int[][] recursionMultiplyMatrix(int[][] firthMatrix,
            int[][] secondMatrix) {
        return recursionMultiplyAlgorithm(firthMatrix, secondMatrix);
    }

    public synchronized int[][] recursionOptimizedMultiplyMatrix(int[][] firthMatrix, int[][] secondMatrix) {
        return recursionOptimizedMultiplyAlgorithm(firthMatrix, secondMatrix);
    }

    private int[][] recursionMultiplyAlgorithm(int[][] firthMatrix, int[][] secondMatrix) {
        int n = firthMatrix.length;
        int[][] C = new int[n][n];
        boolean isAdditionalColumn = false;
        if ((firthMatrix.length % 2 != 0 && firthMatrix.length != 1) ||
                (secondMatrix.length % 2 != 0 && secondMatrix.length != 1)) {
            firthMatrix = this.addAdditionColumnsAndRows(firthMatrix);
            secondMatrix = this.addAdditionColumnsAndRows(secondMatrix);
            isAdditionalColumn = true;
            n++;
        }
        if (n == 1) {
            C[0][0] = firthMatrix[0][0] * secondMatrix[0][0];
            countArithmeticOperationsInRecursionMethod++;
        } else {
            int[][] A11 = secondaryMatrix(firthMatrix, 0, 0, n / 2);
            int[][] A12 = secondaryMatrix(firthMatrix, 0, n / 2, n / 2);
            int[][] A21 = secondaryMatrix(firthMatrix, n / 2, 0, n / 2);
            int[][] A22 = secondaryMatrix(firthMatrix, n / 2, n / 2, n / 2);
            int[][] B11 = secondaryMatrix(secondMatrix, 0, 0, n / 2);
            int[][] B12 = secondaryMatrix(secondMatrix, 0, n / 2, n / 2);
            int[][] B21 = secondaryMatrix(secondMatrix, n / 2, 0, n / 2);
            int[][] B22 = secondaryMatrix(secondMatrix, n / 2, n / 2, n / 2);
            int[][] M1 = recursionMultiplyAlgorithm(A11, B11);
            int[][] M2 = recursionMultiplyAlgorithm(A12, B21);
            int[][] M3 = recursionMultiplyAlgorithm(A11, B12);
            int[][] M4 = recursionMultiplyAlgorithm(A12, B22);
            int[][] M5 = recursionMultiplyAlgorithm(A21, B11);
            int[][] M6 = recursionMultiplyAlgorithm(A22, B21);
            int[][] M7 = recursionMultiplyAlgorithm(A21, B12);
            int[][] M8 = recursionMultiplyAlgorithm(A22, B22);
            int[][] C11 = sumMatrix(M1, M2);
            int[][] C12 = sumMatrix(M3, M4);
            int[][] C21 = sumMatrix(M5, M6);
            int[][] C22 = sumMatrix(M7, M8);
            countArithmeticOperationsInClassicalMethod += 4;
            C = combineFourMatrix(C11, C12, C21, C22);
        }
        if (isAdditionalColumn) {
            C = deleteAdditionalColumnsAndRows(C);
        }
        return C;
    }

    private int[][] recursionOptimizedMultiplyAlgorithm(int[][] firthMatrix, int[][] secondMatrix) {
        int n = firthMatrix.length;
        int[][] C = new int[n][n];
        boolean isAdditionalColumn = false;
        if ((firthMatrix.length % 2 != 0 && firthMatrix.length != 1) ||
                (secondMatrix.length % 2 != 0 && secondMatrix.length != 1)) {
            firthMatrix = this.addAdditionColumnsAndRows(firthMatrix);
            secondMatrix = this.addAdditionColumnsAndRows(secondMatrix);
            isAdditionalColumn = true;
            n++;
        }
        if (n == 1) {
            C[0][0] = firthMatrix[0][0] * secondMatrix[0][0];
            countArithmeticOperationsInOptimizedRecursionMethod++;
        } else {
            int[][] A11 = secondaryMatrix(firthMatrix, 0, 0, n / 2);
            int[][] A12 = secondaryMatrix(firthMatrix, 0, n / 2, n / 2);
            int[][] A21 = secondaryMatrix(firthMatrix, n / 2, 0, n / 2);
            int[][] A22 = secondaryMatrix(firthMatrix, n / 2, n / 2, n / 2);
            int[][] B11 = secondaryMatrix(secondMatrix, 0, 0, n / 2);
            int[][] B12 = secondaryMatrix(secondMatrix, 0, n / 2, n / 2);
            int[][] B21 = secondaryMatrix(secondMatrix, n / 2, 0, n / 2);
            int[][] B22 = secondaryMatrix(secondMatrix, n / 2, n / 2, n / 2);
            int[][] M1;
            int[][] M2;
            int[][] M3;
            int[][] M4;
            int[][] M5;
            int[][] M6;
            int[][] M7;
            int[][] M8;
            if (isMatrixFilledByZeroValues(A11) ||
                    isMatrixFilledByZeroValues(B11)) {
                M1 = fillMatrixByZero(A11.length);
            } else {
                M1 = recursionOptimizedMultiplyAlgorithm(A11, B11);
            }
            if (isMatrixFilledByZeroValues(A12) ||
                    isMatrixFilledByZeroValues(B21)) {
                M2 = fillMatrixByZero(A12.length);
            } else {
                M2 = recursionOptimizedMultiplyAlgorithm(A12, B21);
            }
            if (isMatrixFilledByZeroValues(A11) ||
                    isMatrixFilledByZeroValues(B12)) {
                M3 = fillMatrixByZero(A11.length);
            } else {
                M3 = recursionOptimizedMultiplyAlgorithm(A11, B12);
            }
            if (isMatrixFilledByZeroValues(A12) ||
                    isMatrixFilledByZeroValues(B22)) {
                M4 = fillMatrixByZero(A12.length);
            } else {
                M4 = recursionOptimizedMultiplyAlgorithm(A12, B22);
            }
            if (isMatrixFilledByZeroValues(A21) ||
                    isMatrixFilledByZeroValues(B11)) {
                M5 = fillMatrixByZero(A21.length);
            } else {
                M5 = recursionOptimizedMultiplyAlgorithm(A21, B11);
            }
            if (isMatrixFilledByZeroValues(A22) ||
                    isMatrixFilledByZeroValues(B21)) {
                M6 = fillMatrixByZero(A22.length);
            } else {
                M6 = recursionOptimizedMultiplyAlgorithm(A22, B21);
            }
            if (isMatrixFilledByZeroValues(A21) ||
                    isMatrixFilledByZeroValues(B12)) {
                M7 = fillMatrixByZero(A21.length);
            } else {
                M7 = recursionOptimizedMultiplyAlgorithm(A21, B12);
            }
            if (isMatrixFilledByZeroValues(A22) ||
                    isMatrixFilledByZeroValues(B22)) {
                M8 = fillMatrixByZero(A22.length);
            } else {
                M8 = recursionOptimizedMultiplyAlgorithm(A22, B22);
            }
            int[][] C11;
            int[][] C12;
            int[][] C21;
            int[][] C22;
            if (isMatrixFilledByZeroValues(M1)) {
                C11 = M2;
            } else if (isMatrixFilledByZeroValues(M2)) {
                C11 = M1;
            } else {
                C11 = sumMatrix(M1, M2);
                countArithmeticOperationsInOptimizedRecursionMethod++;
            }
            if (isMatrixFilledByZeroValues(M3)) {
                C12 = M4;
            } else if (isMatrixFilledByZeroValues(M4)) {
                C12 = M3;
            } else {
                C12 = sumMatrix(M3, M4);
                countArithmeticOperationsInOptimizedRecursionMethod++;
            }
            if (isMatrixFilledByZeroValues(M5)) {
                C21 = M6;
            } else if (isMatrixFilledByZeroValues(M6)) {
                C21 = M5;
            } else {
                C21 = sumMatrix(M5, M6);
                countArithmeticOperationsInOptimizedRecursionMethod++;
            }
            if (isMatrixFilledByZeroValues(M7)) {
                C22 = M8;
            } else if (isMatrixFilledByZeroValues(M8)) {
                C22 = M7;
            } else {
                C22 = sumMatrix(M7, M8);
                countArithmeticOperationsInOptimizedRecursionMethod++;
            }
            C = combineFourMatrix(C11, C12, C21, C22);
        }
        if (isAdditionalColumn) {
            C = deleteAdditionalColumnsAndRows(C);
        }
        return C;
    }

    public int[][] addAdditionColumnsAndRows(int[][] matrix) {
        int[][] pairedMatrix = new int[matrix.length +
                1][getColumnsLength(matrix) + 1];
        for (int row = 0; row < matrix.length; row++) {
            if (getColumnsLength(matrix) >= 0)
                System.arraycopy(matrix[row], 0, pairedMatrix[row], 0,
                        getColumnsLength(matrix));
            pairedMatrix[row][getRowsLength(matrix)] = 0;
        }
        for (int k = 0; k < getColumnsLength(pairedMatrix); k++) {
            pairedMatrix[getRowsLength(pairedMatrix) - 1][k] = 0;
        }
        return pairedMatrix;
    }

    public int[][] deleteAdditionalColumnsAndRows(int[][] matrix) {
        int[][] pairedMatrix = new int[matrix.length -
                1][getColumnsLength(matrix) - 1];
        for (int row = 0; row < pairedMatrix.length; row++) {
            if (getColumnsLength(matrix) >= 0)
                System.arraycopy(matrix[row], 0, pairedMatrix[row], 0,
                        getColumnsLength(pairedMatrix));
        }
        return pairedMatrix;
    }

    private int[][] secondaryMatrix(int[][] A, int row, int column, int n) {
        int[][] secondaryMatrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(A[i + row], column, secondaryMatrix[i], 0, n);
        }
        return secondaryMatrix;
    }

    public int[][] combineFourMatrix(int[][] C11, int[][] C12, int[][] C21,
            int[][] C22) {
        int n = C11.length * 2;
        int[][] combinedMatrix = new int[n][n];
        copy((C11), combinedMatrix, 0, 0);
        copy((C12), combinedMatrix, 0, n / 2);
        copy((C21), combinedMatrix, n / 2, 0);
        copy((C22), combinedMatrix, n / 2, n / 2);
        return combinedMatrix;
    }

    public void copy(int[][] copied, int[][] currentMatrix, int rowOffset, int colOffset) {
        int size = copied.length;
        for (int i = 0; i < size; i++) {
            System.arraycopy(copied[i], 0, currentMatrix[rowOffset + i],
                    colOffset, size);
        }
    }

    public boolean isMatrixFilledByZeroValues(int[][] matrix) {
        for (int[] rows : matrix) {
            for (int column = 0; column < rows.length; column++) {
                if (rows[column] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private int[][] fillMatrixByZero(int dimension) {
        return new int[dimension][dimension];
    }

    public boolean areMatrixEquals(int[][] firthMatrix, int[][] secondMatrix) {
        return areMatrixSameLength(firthMatrix, secondMatrix)
                && areAllValuesOfMatrixEquals(firthMatrix, secondMatrix);
    }

    private int multipleRowsAndColumns(int[] rows, int[] columns) {
        int multiplyResult = 0;
        for (int index = 0; index < columns.length; index++) {
            multiplyResult += rows[index] * columns[index];
            countArithmeticOperationsInClassicalMethod += 2;
        }
        return multiplyResult;
    }

    private int[] generateColumnMatrix(int[][] matrix, int columnNumber) {
        int[] column = new int[getRowsLength(matrix)];
        if (areAllColumnsOfMatrixFixedLength(matrix)) {
            for (int index = 0; index < getRowsLength(matrix); index++) {
                column[index] = matrix[index][columnNumber];
            }
        }
        return column;
    }

    private synchronized boolean areMatrixValidForMultiple(int[][] firthMatrix,
            int[][] secondMatrix) {
        return getRowsLength(firthMatrix) == getColumnsLength(secondMatrix);
    }

    private int getColumnsLength(int[][] matrix) {
        if (areAllColumnsOfMatrixFixedLength(matrix)) {
            return matrix[0].length;
        }
        return 0;
    }

    private int getRowsLength(int[][] matrix) {
        return matrix.length;
    }

    private boolean areAllColumnsOfMatrixFixedLength(int[][] matrix) {
        int rowLength = matrix[0].length;
        for (int[] rows : matrix) {
            for (int column = 0; column < rows.length; column++) {
                if (rows.length != rowLength) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean areMatrixSameLength(int[][] firthMatrix, int[][] secondMatrix) {
        return firthMatrix.length == secondMatrix.length;
    }

    private boolean areAllValuesOfMatrixEquals(int[][] firthMatrix, int[][] secondMatrix) {
        if (!areMatrixColumnsSameLength(firthMatrix, secondMatrix)) {
            return false;
        }
        for (int row = 0; row < firthMatrix.length; row++) {
            for (int column = 0; column < firthMatrix[row].length; column++) {
                if (firthMatrix[row][column] != secondMatrix[row][column]) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean areColumnsSameLength(int[] firthColumn, int[] secondColumn) {
        return firthColumn.length == secondColumn.length;
    }

    private boolean areMatrixColumnsSameLength(int[][] firthMatrix, int[][] secondMatrix) {
        if (!areMatrixSameLength(firthMatrix, secondMatrix)) {
            return false;
        }
        for (int row = 0; row < firthMatrix.length; row++) {
            for (int column = 0; column < firthMatrix[row].length; column++) {
                if (!areColumnsSameLength(firthMatrix[row], secondMatrix[row])) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int elem : row) {
                System.out.print(elem + " ");
            }
            System.out.println();
        }
    }
    
}

