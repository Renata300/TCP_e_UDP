// Server-side

import java.net.*;
import java.io.*;

public class TcpServer {
    private static int port = 9876; // TCP port on which the server will be listening
    private static int timeout = 20000; // Timeout to close server socket in milliseconds
    private static int bufferSize = 1024; // Server's bytes buffer size
    private static String outputPath = "messageFromClient.txt"; // Path where file from client will be saved
    private byte[] buffer;

    public static void main(String args[]) {
        TcpServer server = new TcpServer();
        server.startFileReceiver();
		// server.startStringReceiver();
    }

    public TcpServer() {
        buffer = new byte[bufferSize];
    }

    public void startFileReceiver() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(timeout);

            while (!serverSocket.isClosed()) {
                // Wait for client
                System.out.println("|=== Listening on port " + port + "... ===|");
                Socket clientSocket = serverSocket.accept();

                // Client has connected to server
                System.out.print("* Connection received from client on ");
                System.out.print(clientSocket.getRemoteSocketAddress().toString().substring(1));
                System.out.print(" * \n");

                // Read packet from client and write it to a file
                InputStream inputStream = clientSocket.getInputStream(); // Get packet content sent by client
                OutputStream fileOutputStream = new FileOutputStream(outputPath);
                int bytesToWrite = inputStream.read(buffer);

                while (bytesToWrite > 0) {
                    fileOutputStream.write(buffer, 0, bytesToWrite);
                    bytesToWrite = inputStream.read(buffer);
                }

                System.out.println("* File received from client and saved as: " + outputPath + " *\n");

                // Close resources
                inputStream.close();
                fileOutputStream.close();
                clientSocket.close();
            }

            serverSocket.close(); // Make sure it's closed
        }
        catch (SocketTimeoutException e) {
            System.out.println("\n----------------------------");
            System.out.println("| Server socket timed out! |");
            System.out.println("----------------------------");
        }
        catch(UnknownHostException e) {
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void startStringReceiver() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(timeout);

            while (!serverSocket.isClosed()) {
                // Wait for client
                System.out.println("|=== Listening on port " + port + "... ===|");
                Socket clientSocket = serverSocket.accept();

                // Client has connected to server
                System.out.print("* Connection received from client on ");
                System.out.print(clientSocket.getRemoteSocketAddress().toString().substring(1));
                System.out.print(" * \n");

                // Read string message from client
                BufferedReader fromClient = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream())
                );
                System.out.print("\nMessage from client:");
                System.out.println("\n------------------------------------------------------");
                System.out.println(fromClient.readLine()); 
                System.out.println("------------------------------------------------------\n");

                // Reply to client
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);
                toClient.println(" * Client message string received by server! *"); 
            }

            serverSocket.close(); // Make sure it's closed
        }
        catch (SocketTimeoutException e) {
            System.out.println("\n----------------------------");
            System.out.println("| Server socket timed out! |");
            System.out.println("----------------------------");
        }
        catch(UnknownHostException e) {
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
