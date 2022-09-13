package ru.job4j.pool;

import ru.job4j.synch.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks;
    public ThreadPool(int queueSize) {
        tasks = new SimpleBlockingQueue<>(queueSize);
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            threads.add(new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    String threadName = Thread.currentThread().getName();
                    try {
                        Runnable task = tasks.poll();
                        System.out.println("Task started by: " + threadName);
                        task.run();
                        System.out.println(threadName + " finished task");
                    } catch (InterruptedException e) {
                        System.out.println("Thread " + threadName + " is interrupted");
                        Thread.currentThread().interrupt();
                    }
                }
            }));
        }
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
        ThreadPool threadPool = new ThreadPool(10);
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
