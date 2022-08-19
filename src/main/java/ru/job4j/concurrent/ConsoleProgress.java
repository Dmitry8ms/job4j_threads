package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    public String[] process = new String[] {"-", "\\", "|", "/"};
    @Override
    public void run() {
        int index = 0;
            while (!Thread.currentThread().isInterrupted()) {
                System.out.print("\rLoading : " + process[index++]);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                if (index == process.length) {
                    index = 0;
                }
            }

    }


}
