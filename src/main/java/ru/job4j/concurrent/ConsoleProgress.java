package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    public String[] process = new String[] {"-", "\\", "|", "/"};
    @Override
    public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                for (int i = 0; i < 4; i++) {
                    System.out.print("\rLoading : " + process[i]);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

            }

    }
}
