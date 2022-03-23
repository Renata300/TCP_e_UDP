// Client-side
import java.net.*;
import java.io.*;

public class TcpClient {
    public static void main(String args[]) {
        TcpClient client = new TcpClient();
        int serverPort = 9876; // TCP port on which the server is listening
        client.sendFile(serverPort);
        // client.sendString("Hello, World!", serverPort);
    }
    
    public void sendFile(int serverPort) {
        try {
            // Connect to server
            String serverAddress = InetAddress.getByName("localhost").getHostAddress();
            System.out.println("Connecting to server on " + serverAddress + ":" + serverPort);
            Socket clientSocket = new Socket(serverAddress, serverPort); 

            // Send file to server
            File fileToServer = new File("messageToServer.txt");

            InputStream inputStream = new FileInputStream(fileToServer);
            OutputStream outputStream = clientSocket.getOutputStream();

            byte[] bytesBuffer = new byte[8196];
            
            int bytesToWrite = inputStream.read(bytesBuffer);
            while (bytesToWrite > 0) {
                outputStream.write(bytesBuffer, 0, bytesToWrite);
                bytesToWrite = inputStream.read(bytesBuffer);
            }

            System.out.println("* Message sent to server! * ");

            outputStream.close();
            inputStream.close();
            clientSocket.close();
        }
        catch(UnknownHostException e) {
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        } 
    }

    public void sendString(String stringMessage, int serverPort) {
        try {
            // Connect to server
            String serverAddress = InetAddress.getByName("localhost").getHostAddress();
            System.out.println("Connecting to server on " + serverAddress + ":" + serverPort);
            Socket clientSocket = new Socket(serverAddress, serverPort); 

            // Send string
            PrintWriter toServer = new PrintWriter(clientSocket.getOutputStream(), true);
            toServer.println(stringMessage); 
            
            // Server response
            BufferedReader fromServer = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream())
            );
            System.out.println(fromServer.readLine());

            // Close resources
            toServer.close();
            fromServer.close();
            clientSocket.close();
        }
        catch(UnknownHostException e) {
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        } 
    }
}
