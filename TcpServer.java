// Server-side
import java.net.*;
import java.io.*;

public class TcpServer {
    public static void main(String args[]) {
        TcpServer server = new TcpServer();
        int serverPort = 9876; // TCP port on which the server will be listening
        server.startFileReceiver(serverPort);
		// server.startStringReceiver(serverPort);
    }

    public void startFileReceiver(int serverPort) {
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            serverSocket.setSoTimeout(20000); // Timeout to close server socket

            while (true) {
                // Wait for client
                System.out.println("Listening on port " + serverSocket.getLocalPort() + "..."); 
                Socket clientSocket = serverSocket.accept();

                // Client has connected to server
                System.out.print("* Connection received from client on ");
                System.out.print(clientSocket.getRemoteSocketAddress().toString().substring(1));
                System.out.print(" * \n\n");

                // Read message from client and write it to a file
                InputStream inputStream = clientSocket.getInputStream();
                File messageFromClient = new File("messageFromClient.txt");
                messageFromClient.createNewFile(); // Make sure file exists
                OutputStream outputStream = new FileOutputStream(messageFromClient);

                byte[] bytesBuffer = new byte[8196];

                int bytesToWrite = inputStream.read(bytesBuffer);
                while (bytesToWrite > 0) {
                    outputStream.write(bytesBuffer, 0, bytesToWrite);
                    bytesToWrite = inputStream.read(bytesBuffer);
                }

                // Close resources
                inputStream.close();
                outputStream.close();
                clientSocket.close();
            }
        }
        catch(UnknownHostException e) {
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        } 
    }
    
    public void startStringReceiver(int serverPort) {
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            serverSocket.setSoTimeout(32000); // Timeout to close server socket
    
            while (true) {
                // Wait for client
                System.out.println("Listening on port " + serverSocket.getLocalPort() + "..."); 
                Socket clientSocket = serverSocket.accept();

                // Client has connected to server
                System.out.print("* Connection received from client on ");
                System.out.print(clientSocket.getRemoteSocketAddress().toString().substring(1));
                System.out.print(" * \n");


                // Receive string from client
                BufferedReader fromClient = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream())
                );
                System.out.println("\n------------------------------------------------------");
                System.out.println(fromClient.readLine()); 
                System.out.println("------------------------------------------------------\n");
                
                // Reply to client
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);
                toClient.println("Client message string received!"); 
            }
        }
        catch(UnknownHostException e) {
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        } 
    }
}
