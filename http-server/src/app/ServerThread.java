package app;
import java.lang.Thread;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.io.BufferedReader;


public class ServerThread extends Thread{
     private String  newMessage;
     private Socket client;
     private ServerSocket serverSocket;
     private List<String> htmlFile; 
     private List<String> jsonFile; 

     public ServerThread(ServerSocket serverSocket, List<String> htmlFile, List<String> jsonFile ){
          this.serverSocket = serverSocket;
          this.htmlFile = htmlFile;
          this.jsonFile=jsonFile;
     }


     public static StringBuilder BuildString(List<String> file) {
          StringBuilder finalResult = new StringBuilder();
          for (String line :file) {
            finalResult.append(line);
        }
        return finalResult;
        }
    
     @Override
     public void run(){
          try {
               // while (true) {
                    client = serverSocket.accept();
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
                    //   }
          } catch (Exception e) {
               System.out.printf(e.getMessage()); 
          }
     }

}