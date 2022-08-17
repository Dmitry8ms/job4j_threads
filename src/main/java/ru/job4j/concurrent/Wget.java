package ru.job4j.concurrent;

public class Wget {
    public static void main(String[] args) {
        Thread load = new Thread(() -> {
            for (int i = 0; i < 101; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print("\rLoading : " + i + "%");
            }
        });
        load.start();
    }
}
