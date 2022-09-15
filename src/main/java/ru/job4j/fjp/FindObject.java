package ru.job4j.fjp;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FindObject<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final T object;
    public FindObject(T[] array, T object) {
        this.array = array;
        this.object = object;
    }

    @Override
    protected Integer compute() {
        int result = 0;
        if (array.length <= 10) {
            for (int i = 0; i < array.length; i++) {
                if (array[i].equals(object)) {
                    result = i;
                }
            }
            return result;
        }

        FindObject<T> firstHalfFinder = new FindObject<>(Arrays.copyOfRange(array, 0,
                array.length / 2), object);
        FindObject<T> secondHalfFinder = new FindObject<>(Arrays.copyOfRange(array,
                array.length / 2, array.length), object);
        firstHalfFinder.fork();
        secondHalfFinder.fork();
        int r1 = firstHalfFinder.join();
        int r2 = secondHalfFinder.join();
        System.out.println(Math.max(r1, r2));
        return Math.max(r1, r2);
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Model[] array = InitArray.get(100);
        System.out.println(Arrays.toString(array));
        FindObject<Model> fo = new FindObject<>(array, new Model(99));

        System.out.println(forkJoinPool.invoke(fo));
        //System.out.println(fo.find());
    }
}
