package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);
    public void increment() {
        int newCount;
        int oldCount;
        do {
            oldCount = get();
            newCount = oldCount + 1;
        } while (!count.compareAndSet(oldCount, newCount));
    }

    public int get() {
        return count.get();
    }

    public static void main(String[] args) throws InterruptedException {
        CASCount cCount = new CASCount();
        Thread counter1 = new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                cCount.increment();
            }
        });
        Thread counter2 = new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                cCount.increment();
            }
        });
        counter1.start();
        counter2.start();
        counter1.join();
        counter2.join();
        System.out.println("count is good: " + (cCount.get() == 100));
    }
}