package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

/**
 * Server
 * 
 *authored by @ unclejide1
 */

public class App {
  static final int port = 9000;
  static Path htmlPath = Paths.get("http-server\\src\\app\\sample.html");
  static Path jsonPath = Paths.get("http-server\\src\\app\\myjson.json");

  public static StringBuilder BuildString(List<String> file) {
    StringBuilder finalResult = new StringBuilder();
    for (String line :file) {
      finalResult.append(line);
  }
  return finalResult;
  }
  

  public static void main(String[] args) {
    System.out.printf("Server is waiting for connection on port %d\n", port);
    try (ServerSocket serverSocket = new ServerSocket(port)) {
        List<String>htmlFile = Files.readAllLines(htmlPath, StandardCharsets.UTF_8);
        List<String>jsonFile = Files.readAllLines(jsonPath, StandardCharsets.UTF_8);
      String newMessage = "";

      while (true) {
        Socket client = serverSocket.accept();
        System.out.println("New user connected");
        InputStream is = client.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        OutputStream ous = client.getOutputStream();
        String  route = null;
        newMessage = br.readLine();
                if (newMessage.contains("GET ")){
                String[] arrayOfMethods = newMessage.split(" ");
                route = arrayOfMethods[1];
                }
       
        StringBuilder routeResponse = new StringBuilder();
        String contentType = null;

            
                if(route.equals("/")){
                  routeResponse = BuildString(htmlFile);
                  contentType = "Content-Type: text/html\r\n" ;
                }

                if(route.equalsIgnoreCase("/json")){
                  routeResponse = BuildString(jsonFile);
                  contentType = "Content-Type: application/json\r\n" ;
                }

          String httpResponse = "HTTP/1.1 200 OK\r\n" + contentType  + "\r\n" +routeResponse;
              ous.write((httpResponse).getBytes());
              ous.flush();
              ous.close();
          }
      }
     catch (IOException e) {

    }
}
}

