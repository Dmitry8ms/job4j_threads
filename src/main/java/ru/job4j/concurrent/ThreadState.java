package ru.job4j.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ThreadState {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ThreadLocal<String> tl = new ThreadLocal<>();
        Thread first = new Thread(
                () -> {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(tl.get());
                    tl.set("ThreadLocal_1");
                    System.out.println(tl.get());
                    for (int i = 0; i < 5; i++) {
                        System.out.println("First thread - " + Thread.currentThread().getName());
                    }

                }
        );
        Thread second = new Thread(
                () -> {
                    tl.set("ThreadLocal_2");
                    System.out.println(tl.get());
                    System.out.println("Second thread - " + Thread.currentThread().getName());
                }
        );
        FutureTask<Integer> ft = new FutureTask<>(() -> 3);
        Thread third = new Thread(ft);
        System.out.println("first thread - " + first.getState());
        System.out.println("second thread - " + second.getState());
        first.start();
        second.start();
        third.start();
        while (first.getState() != Thread.State.TERMINATED
                || second.getState() != Thread.State.TERMINATED) {
            System.out.println("first thread - " + first.getState());
            System.out.println("second thread - " + second.getState());
        }
        System.out.println("first thread - " + first.getState());
        System.out.println("second thread - " + second.getState());
        System.out.println("Main thread - " + Thread.currentThread().getName());
        System.out.println(ft.get());
        System.out.println("Job is done");
    }
}
