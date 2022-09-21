package ru.job4j.pools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

public class RolColSum {
    public static record Sums(int rowSum, int colSum) { }

    public static Sums[] sum(int[][] matrix) {
        Sums[] result = new Sums[matrix.length];
        int rowSum;
        for (int i = 0; i < matrix.length; i++) {
            rowSum = IntStream.of(matrix[i]).sum();
            int colSum = 0;
            for (int[] ints : matrix) {
                colSum += ints[i];
            }
            result[i] = new Sums(rowSum, colSum);
        }
        return result;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int size = matrix.length;
        Sums[] result = new Sums[size];
        List<CompletableFuture<Sums>> futureList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            futureList.add(getTask(matrix, i));
        }
        int index = 0;
        for (CompletableFuture<Sums> futureElement : futureList) {
            result[index++] = futureElement.get();
        }
        return result;
    }

    private static CompletableFuture<Sums> getTask(int[][] matrix, int i) {
        return CompletableFuture.supplyAsync(() -> {
            int size = matrix.length;
            int rowSum = 0;
            int colSum = 0;
            for (int j = 0; j < size; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
            }
            return new Sums(rowSum, colSum);
        });
    }

}
