package app;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.net.Socket;

/**
 * Server
 * 
 *authored by @ unclejide1
 */

public class App {
  static final int port = 9000;
  static Path htmlPath = Paths.get("http-server\\src\\app\\sample.html");
  static Path jsonPath = Paths.get("http-server\\src\\app\\myjson.json");

  public static void main(String[] args) {
    System.out.printf("Server is waiting for connection on port %d\n", port);
    try (ServerSocket serverSocket = new ServerSocket(port)) {
        List<String>htmlFile = Files.readAllLines(htmlPath, StandardCharsets.UTF_8);
        List<String>jsonFile = Files.readAllLines(jsonPath, StandardCharsets.UTF_8);
      while (true) {
        Socket clientSocket = serverSocket.accept();
       ClientThread client = new ClientThread(clientSocket, htmlFile, jsonFile);
       client.start();
          }
      }
     catch (IOException e) {
 System.out.printf(e.getMessage()); 
    }
}
}

