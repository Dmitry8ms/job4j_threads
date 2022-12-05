package ru.job4j.synch;

import java.util.concurrent.Phaser;

public class MyThread implements Runnable {
    Phaser phsr;
    String name;

    MyThread(Phaser p, String n) {
        phsr = p;
        name = n;
        new Thread(this).start();
    }

    public void run() {

        System.out.println("Поток " + name + " начинает фазу " + phsr.getPhase());
        phsr.arrive();

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        System.out.println("Поток " + name + " начинает фазу " + phsr.getPhase());
        phsr.arrive();

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        System.out.println("Поток " + name + " начинает фазу " + phsr.getPhase());

        System.out.println(phsr.arriveAndDeregister());
    }
}
