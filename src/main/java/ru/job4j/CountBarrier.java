package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class CountBarrier {

    private final int total;

    @GuardedBy("this")
    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public synchronized void count() {
        count = 0;
        for (int i = 0; i <= total; i++) {
            count++;
        }
        notifyAll();
    }

    public synchronized void await() {
        while (count < total) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    public static void main(String[] args) {
        CountBarrier timeBarrier = new CountBarrier(10);
        Thread master = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    timeBarrier.count();
                },
                "Master");
        Thread master2 = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    timeBarrier.count();
                },
                "Master 2");
        Thread slave = new Thread(
                () -> {
                    timeBarrier.await();
                    System.out.println(Thread.currentThread().getName() + " started");
                },
                "Slave");
        Thread slave2 = new Thread(
                () -> {
                    timeBarrier.await();
                    System.out.println(Thread.currentThread().getName() + " started");
                },
                "Slave 2");
        master.start();
        master2.start();
        slave.start();
        slave2.start();
    }
}
