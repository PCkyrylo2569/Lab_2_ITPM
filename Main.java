import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(System.in)){
        MatrixOperations matrixOperations = new MatrixOperations();

        System.out.print("Введіть розмірність матриці (n x n): ");
        int n = scanner.nextInt();

        System.out.println("Оберіть спосіб введення матриць:");
        System.out.println("1. Введення з клавіатури");
        System.out.println("2. Генерація випадкових чисел");
        //int choice = scanner.nextInt();

        int[][] A = matrixOperations.generateMatrixA(n);
        int[][] B = matrixOperations.generateMatrixB(n);

        System.out.println("Matrix 1:");
        MatrixOperations.printMatrix(A);
        System.out.println("Matrix 2:");
        MatrixOperations.printMatrix(B);

        System.out.println("=======================================");
        System.out.println("The result of classical multiplication A x B:");
        int[][] classicalMultiplyResult = matrixOperations.classicalMultiplyMatrix(A, B);
        MatrixOperations.printMatrix(classicalMultiplyResult);

        System.out.println("Result by recursive multiplication A x B:");
        int[][] recursionMultiplyResult = matrixOperations.recursionMultiplyMatrix(A, B);
        MatrixOperations.printMatrix(recursionMultiplyResult);

        matrixOperations.recursionOptimizedMultiplyMatrix(A, B);
        System.out.println("=======================================");
        System.out.print("Number of arithmetic operations in the classical method (+ - x /): ");
        System.out.println(MatrixOperations.countArithmeticOperationsInClassicalMethod);
        System.out.print("Number of arithmetic operations in a recursive method (+ - x /): ");
        System.out.println(MatrixOperations.countArithmeticOperationsInRecursionMethod);
        System.out.print("Number of arithmetic operations in the optimised recursive method (+ - x /): ");
        System.out.println(MatrixOperations.countArithmeticOperationsInOptimizedRecursionMethod);
        }
    }
}
