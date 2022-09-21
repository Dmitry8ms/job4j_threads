package ru.job4j.pools;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class RolColSumTest {
    @Test
    public void testSync() {
        int[][] matrix = {{1, 2}, {3, 4}};
        assertArrayEquals(new RolColSum.Sums[] {new RolColSum.Sums(3, 4),
                    new RolColSum.Sums(7, 6)}, RolColSum.sum(matrix));
    }
    @Test
    public void testAsync() throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2}, {3, 4}};
        assertArrayEquals(new RolColSum.Sums[] {new RolColSum.Sums(3, 4),
                new RolColSum.Sums(7, 6)}, RolColSum.asyncSum(matrix));
    }
}