package ru.job4j.io;

import java.io.*;

public final class SaveToFile {
    private final File file;

    public SaveToFile(File file) {
        this.file = file;
    }
    public synchronized void saveContent(String content) {
        try (OutputStream o = new BufferedOutputStream(new FileOutputStream(file))) {
            byte[] buffer = content.getBytes();
            o.write(buffer, 0, buffer.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
