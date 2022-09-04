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

    public synchronized void offer(T value) {
        queue.offer(value);
        notifyAll();
    }

    public synchronized T poll() {
        while (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("poll() wait in "
                        + Thread.currentThread().getName() + " was " + "interrupted");
                Thread.currentThread().interrupt();
            }
        }
        return queue.poll();
    }


    public static void main(String[] args) throws InterruptedException {
        List<Integer> container = new ArrayList<>();
        SimpleBlockingQueue<Integer> bQueue = new SimpleBlockingQueue<>();
        Thread provider = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                bQueue.offer(i);
                System.out.println(i + " was put");
            }
        });
        Thread consumer1 = new Thread(() -> {
            while (container.size() < 99) {
                Integer e = bQueue.poll();
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
