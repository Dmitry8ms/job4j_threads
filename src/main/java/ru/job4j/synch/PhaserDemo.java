package ru.job4j.synch;

import java.util.concurrent.Phaser;

public class PhaserDemo {

    public static void main(String[] args) {
        Phaser phsr = new Phaser(3);
        int curPhase;

        System.out.println("Запуск потоков");

        new MyThread(phsr, "A");
        new MyThread(phsr, "B");
        new MyThread(phsr, "C");


        phsr.awaitAdvance(0);
        curPhase = phsr.getPhase();
        System.out.println("Фаза " + curPhase + " завершена");

        curPhase = phsr.getPhase();
        System.out.println("Phase - " + curPhase);
        phsr.awaitAdvance(1);
        System.out.println("Фаза " + curPhase + " завершена");

        curPhase = phsr.getPhase();
        System.out.println("Phase - " + curPhase);
        phsr.awaitAdvance(2);
        System.out.println("Фаза " + curPhase + " завершена");

        if (phsr.isTerminated()) {
            System.out.println("Синхронизатор фаз завершен");
        }
    }
}
