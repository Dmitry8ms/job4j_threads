package ru.job4j.concurrent;

public class ConcurrentOutput {
    public static void main(String[] args) throws InterruptedException {
        Thread another = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        System.out.println(Thread.currentThread().getName());
                    }

                }
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        another.start();
        Thread.sleep(1);
        second.start();
        Thread.sleep(1);
        another.interrupt();
        System.out.println(Thread.currentThread().getName());
    }
}
