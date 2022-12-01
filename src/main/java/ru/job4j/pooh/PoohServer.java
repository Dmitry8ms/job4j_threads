package ru.job4j.pooh;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class PoohServer {
    public static void main(String[] args) throws IOException {
        String ls = System.lineSeparator();
        String content = "GET /topic/weather HTTP/1.1" + ls
                + "Host: localhost:9000" + ls
                + "User-Agent: curl/7.72.0" + ls
                + "Accept: */*" + ls
                + "Content-Length: 14" + ls
                +  "Content-Type: application/x-www-form-urlencoded" + ls
                + "" + ls
                + "client407" + ls;
        String[] lines = content.split("\\R");
        String[] parses = lines[0].split(" ");
        String httpRequestType = parses[0];
        String[] words = parses[1].split("/");
        String poohMode = words[1];
        String sourceName = words[2];
        String param = lines[lines.length - 1].trim();
        if (("GET".equals(httpRequestType) && "queue".equals(poohMode))
                || words.length == 4) {
            param = "";
        }
        System.out.println(Arrays.toString(lines));
        System.out.println(Arrays.toString(parses));
        System.out.println(Arrays.toString(words));
        System.out.println("param: " + param);
        /*if (!param.contains("=")) {
            param = "";
        }*/
        System.out.println(httpRequestType + " " + poohMode + " " + sourceName + " "
                + param);
        try (ServerSocket server = new ServerSocket(9000)) {
            while (!server.isClosed()) {
                Socket socket = server.accept();
                try (OutputStream out = socket.getOutputStream();
                     InputStream input = socket.getInputStream()) {
                    byte[] buff = new byte[1_000_000];
                    var total = input.read(buff);
                    var text = new String(Arrays.copyOfRange(buff, 0, total),
                            StandardCharsets.UTF_8);
                    System.out.println("text: " + text + " end of text");
                    out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
                    out.write(text.getBytes());
                }
            }
        }
    }
}
