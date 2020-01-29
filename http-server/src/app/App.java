package app;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.io.IOException;

/**
 * Server
 */
public class App {
  static boolean quit = false;
  static final int port = 8081;
  static Path path = Paths.get("http-server\\src\\app\\sample.html");
  static Path path2 = Paths.get("http-server\\src\\app\\myjson.json");

  public static void main(String[] args) {
    System.out.printf("Server is waiting for connection on port %d\n", port);
    try (ServerSocket serverSocket = new ServerSocket(port)) {
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        List<String> lines2 = Files.readAllLines(path2, StandardCharsets.UTF_8);
      Socket client = serverSocket.accept();
      System.out.println("New user connected");
      InputStream is = client.getInputStream();
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      OutputStream ous = client.getOutputStream();
      String message = "";
      int headers = 0;
      while (!quit) {
        // when connecting with a browser, read all first before returning the response
        String newMessage = null;
        String method = null;
        if(headers == 0){
             newMessage = br.readLine();
        message += newMessage;
        String[] arrayOfMethods = message.split(" ");
        method = arrayOfMethods[1];
        // System.out.println(Arrays.toString(arrayOfMethods));
        }
        headers++;
        System.out.printf("Client: %s\n", message);
        if (message.equals("quit")) {
          message = "Thanks for chatting with me...";
          quit = true;
        } else {
            String httpResponse = null;
          if (!client.isClosed()) {
            StringBuilder finalResult = new StringBuilder();
            
            if(method.equals("/")){
                for (String line : lines) {
                    finalResult.append(line);
                }
                httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + finalResult; 
            }
            if(method.equalsIgnoreCase("/json")){
                for (String line : lines2) {
                    finalResult.append(line);
                }
                httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + finalResult; 
            }
            
              ous.write((httpResponse).getBytes());
              ous.flush();
              ous.close();
              quit = true;
          } else {
            System.err.println("Connection to client is closed!");
            quit = true;
          }
      }
    }
}
     catch (IOException e) {

    }
}
}

