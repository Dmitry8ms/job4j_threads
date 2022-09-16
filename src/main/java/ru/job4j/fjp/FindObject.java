package ru.job4j.fjp;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FindObject<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final T object;
    private final int from;
    private final int to;

    public FindObject(T[] array, T object, int from, int to) {
        this.array = array;
        this.object = object;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        int result = -1;
        if ((to - from) <= 10) {
            for (int i = from; i <= to; i++) {
                if (array[i].equals(object)) {
                    result = i;
                    break;
                }
            }
            return result;
        }
        int mid = (from + to) / 2;
        FindObject<T> firstHalfFinder = new FindObject<>(array, object, from, mid);
        FindObject<T> secondHalfFinder = new FindObject<>(array, object, mid + 1, to);
        firstHalfFinder.fork();
        secondHalfFinder.fork();
        int r1 = firstHalfFinder.join();
        int r2 = secondHalfFinder.join();
        return Math.max(r1, r2);
    }

    public static <T> int find(T[] array, T object) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        FindObject<T> fo = new FindObject<>(array, object, 0, array.length - 1);
        return forkJoinPool.invoke(fo);
    }

    public static void main(String[] args) {
        Model[] array = InitArray.get(100);
        System.out.println("INDEX IS: " + FindObject.find(array, new Model(110)));
    }
}
