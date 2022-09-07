package ru.job4j.synch;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        List<Integer> container = new ArrayList<>();
        SimpleBlockingQueue<Integer> bQueue = new SimpleBlockingQueue<>(10);
        Thread provider = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    bQueue.offer(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread consumer1 = new Thread(() -> {
            while (container.size() < 5) {
                Integer e = null;
                try {
                    e = bQueue.poll();
                } catch (InterruptedException ex) {
                    System.out.println("Consumer is interrupted");
                    Thread.currentThread().interrupt();
                }
                container.add(e);
            }
        });
        provider.start();
        consumer1.start();
        provider.join();
        consumer1.join();
        assertFalse(container.contains(null));
        assertThat(container, is(Arrays.asList(0, 1, 2, 3, 4)));
    }
}