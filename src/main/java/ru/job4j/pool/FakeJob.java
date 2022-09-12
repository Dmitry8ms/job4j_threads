package ru.job4j.pool;

public class FakeJob implements  Runnable {
    private final int id;
    public FakeJob(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Task " + id + " is doing really important job here");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Task " + id + " is done here");
    }
}
