package ru.job4j.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());
    public  void emailTo(User user) {
        pool.submit(() -> {
            String subject =  "Notification " + user.username() + " to email " + user.email()
                    + ".";
            String body = "Add a n2. ExecutorService рассылка почты. [#63097]ew event to " + user.username();
            send(subject, body, user.email());
        });
    }
    private void send(String subject, String body, String email) {
        System.out.println("Message " + subject + " " + body + " " + email + " was sent by "
                + Thread.currentThread().getName());
    }
    public void close() {
        pool.shutdown();
    }

    public static void main(String[] args) {
        EmailNotification en = new EmailNotification();
        List<User> userList = new ArrayList<>();
        userList.add(new User("Petr", "petr@job4j.ru"));
        userList.add(new User("Stas", "stas@job4j.ru"));
        userList.add(new User("Dmitry", "dmitry@job4j.ru"));
        for (User u : userList) {
            en.emailTo(u);
        }
        en.close();

    }
}
