package ru.job4j.pool;

import ru.job4j.synch.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(10);
    public ThreadPool() {
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            threads.add(new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        String threadName = Thread.currentThread().getName();
                        Runnable task = tasks.poll();
                        System.out.println("Task started by: " + threadName);
                        task.run();
                        System.out.println(threadName + " finished task");
                    } catch (InterruptedException e) {
                        System.out.println("Consumer is interrupted");
                        Thread.currentThread().interrupt();
                    }
                }
            }));
        }
    }
    public void start() {
        for (Thread t : threads) {
            t.start();
        }
    }
    public void offerWork(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        for (Thread t : threads) {
            t.interrupt();
        }
    }

    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool();
        threadPool.start();
        for (int i = 1; i <= 5; i++) {
            try {
                threadPool.offerWork(new FakeJob(i));
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        threadPool.shutdown();
    }
}
