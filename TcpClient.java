// Client-side

import java.net.*;
import java.io.*;

public class TcpClient {
    private static String serverIp = "127.0.0.1";
    private static int serverPort = 9876; // TCP port on which the server will be listening
    private static String fileToBeSent = "messageToServer.txt";
    private static String stringToBeSent = "Hello, World!";
    private static int bufferSize = 1024; // Client's bytes buffer size
    private byte[] buffer;

    public static void main(String args[]) {
        TcpClient client = new TcpClient();
        // client.sendFile();
        client.sendString();
    }

    public TcpClient() {
        buffer = new byte[bufferSize];
    }

    public void sendFile() {
        try {
            // Connect to server
            System.out.println("* Connecting to server on " + serverIp + ":" + serverPort + " *");
            Socket clientSocket = new Socket(serverIp, serverPort); 

            // Send file to server
            InputStream fileInputStream = new FileInputStream(fileToBeSent);
            OutputStream fileOutputStream = clientSocket.getOutputStream();
            int bytesToSend = fileInputStream.read(buffer);

            while (bytesToSend > 0) {
                fileOutputStream.write(buffer, 0, bytesToSend);
                bytesToSend = fileInputStream.read(buffer);
            }

            System.out.println("* File sent to server! * ");

            // Close resources
            fileOutputStream.close();
            fileInputStream.close();
            clientSocket.close();
        }
        catch(ConnectException e) {
            System.err.println("\n--------------------------");
            System.err.println("| Server not available! |");
            System.err.println("--------------------------");
        }
        catch(UnknownHostException e) {
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        } 
    }

    public void sendString() {
        try {
            // Connect to server
            String serverAddress = InetAddress.getByName("localhost").getHostAddress();
            System.out.println(" * Connecting to server on " + serverAddress + ":" + serverPort + " *");
            Socket clientSocket = new Socket(serverAddress, serverPort); 

            // Send string
            PrintWriter toServer = new PrintWriter(clientSocket.getOutputStream(), true);
            toServer.println(stringToBeSent);
            
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
        catch(ConnectException e) {
            System.err.println("\n--------------------------");
            System.err.println("| Server not available! |");
            System.err.println("--------------------------");
        }
        catch(UnknownHostException e) {
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        } 
    }
}
