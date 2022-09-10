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

}