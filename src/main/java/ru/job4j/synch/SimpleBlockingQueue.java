package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int limit;

    public SimpleBlockingQueue(int size) {
        this.limit = size;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() > limit - 1) {
            wait();
        }
        queue.offer(value);
        notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        T rsl = queue.poll();
        notifyAll();
        return rsl;
    }


    public static void main(String[] args) throws InterruptedException {
        List<Integer> container = new ArrayList<>();
        SimpleBlockingQueue<Integer> bQueue = new SimpleBlockingQueue<>(10);
        Thread provider = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    bQueue.offer(i);
                    System.out.println(i + " was put");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread consumer1 = new Thread(() -> {
            while (container.size() < 100) {
                Integer e = null;
                try {
                    e = bQueue.poll();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                System.out.println(e + " was got by " + Thread.currentThread().getName());
                container.add(e);
                System.out.println("elements collected in container: " + container.size());
            }
        });
        provider.start();
        consumer1.start();
        provider.join();
        System.out.println("Provider joined");
        System.out.println("consumer1 is " + consumer1.getState());
        consumer1.join();
        System.out.println("Consumer1 joined");
        System.out.println("bQueue is blocking queue: " + !container.contains(null));
        System.out.println(container);

    }
}
