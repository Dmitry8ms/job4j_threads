package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) throws InterruptedException {
        Thread first = new Thread(
                () -> System.out.println("first thread - " + Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println("Second thread - " + Thread.currentThread().getName())
        );
        System.out.println("first thread - " + first.getState());
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED) {
            System.out.println("first thread - " + first.getState());
        }
        System.out.println("first thread - " + first.getState());
        first.join();
        second.join();
        System.out.println("Main thread - " + Thread.currentThread().getName());
        System.out.println("Job is done");
    }
}
