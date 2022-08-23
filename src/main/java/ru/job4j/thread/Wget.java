package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String downloadFile;
    public Wget(String url, int speed, String downloadFile) {
        this.url = url;
        this.speed = speed;
        this.downloadFile = downloadFile;
    }

    @Override
    public void run() {
        long zeroTime = System.nanoTime();
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(downloadFile)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int sumBytesRead = 0;
            int downloadData = 0;
            System.out.print("\rBytes read : " + sumBytesRead);
            long startTime = System.nanoTime();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                sumBytesRead += bytesRead;
                downloadData += bytesRead;
                if (downloadData >= 1048576) {
                    long elapsedTime = TimeUnit.MILLISECONDS.convert(System.nanoTime() - startTime,
                            TimeUnit.NANOSECONDS);
                    long deltaTime = (1 / speed) * 1000 - elapsedTime;
                    if (deltaTime > 0) {
                        Thread.sleep(deltaTime);
                    }
                    downloadData = 0;
                    startTime = System.nanoTime();
                }

                System.out.print("\rBytes read : " + sumBytesRead);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print(System.lineSeparator() + "File is downloaded for "
                + Math.round((System.nanoTime() - zeroTime) / 1000000000.0) + " seconds");
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 3) {
            throw new IllegalArgumentException("usage: wGet url speed(Mb/sec) downloadFile");
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String downloadFile = args[2];
        Thread wget = new Thread(new Wget(url, speed, downloadFile));
        wget.start();
        wget.join();
    }
}
