package ru.job4j.pools;

import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

public class RolColSum {
    public static record Sums(int rowSum, int colSum) { }

    public static Sums[] sum(int[][] matrix) {
        Sums[] result = new Sums[matrix.length];
        int rowSum;
        for (int i = 0; i < matrix.length; i++) {
            rowSum = IntStream.of(matrix[i]).sum();
            int colSum = 0;
            for (int j = 0; j < matrix.length; j++) {
                colSum += matrix[j][i];
            }
            result[i] = new Sums(rowSum, colSum);
        }
        return result;
    }

    public static Sums[] asyncSum(int[][] matrix) {
        CompletableFuture<?>[] completableFuture =
                new CompletableFuture<?>[matrix.length];

        return null;
    }

    public static void main(String[] args) {
        int[][] matrix = {{1, 2}, {3, 4}};
        for (Sums element : sum(matrix)) {
            System.out.println(element);
        }
    }
}
