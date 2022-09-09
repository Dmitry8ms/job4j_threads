package ru.job4j;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CASCountTest {

    @Test
    public void whenCount50and50result100() throws InterruptedException {
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
        assertEquals(100, cCount.get());
    }

}