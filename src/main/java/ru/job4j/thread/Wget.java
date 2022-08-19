package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int sumBytesRead = 0;
            System.out.print("\rBytes read : " + sumBytesRead);
            long startTime = System.nanoTime();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                sumBytesRead += bytesRead;
                long elapsedTime = TimeUnit.MILLISECONDS.convert(System.nanoTime() - startTime,
                        TimeUnit.NANOSECONDS);
                long shouldBeTime = (bytesRead / speed) * 1000;
                Thread.sleep(shouldBeTime - elapsedTime);
                startTime = System.nanoTime();
                System.out.print("\rBytes read : " + sumBytesRead);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("\nFile is downloaded");
    }

    public static void main(String[] args) throws InterruptedException {
        String url = "";
        if (ValidateURL.isURL(args[0])) {
            url = args[0];
        } else {
            throw new IllegalArgumentException("Wrong URL format");
        }
        int speed = 0;
        if (ValidateInt.isInt(args[1])) {
            speed = Integer.parseInt(args[1]);
        } else {
            throw new IllegalArgumentException("Wrong integer format");
        }
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
