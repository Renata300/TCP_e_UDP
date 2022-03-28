// Server-side

import java.net.*;
import java.io.*;

public class UdpServer {
    private static int port = 9876; // UDP port on which the server will be listening
    private static int timeout = 20000; // Timeout to close server socket in milliseconds
    private static int bufferSize = 1024; // Server's bytes buffer size
    private static String outputPath = "messageFromClient.txt"; // Path where file from client will be saved
    private byte[] buffer;

    public static void main(String args[]) {
        UdpServer server = new UdpServer();
        server.startFileReceiver();
		// server.startStringReceiver();
    }

    public UdpServer() {
        buffer = new byte[bufferSize];
    }

    public void startFileReceiver() {
        try {
            DatagramSocket serverSocket = new DatagramSocket(port);
            serverSocket.setSoTimeout(timeout);

            // Delete current file if it exists
            File outputFile = new File(outputPath);
            outputFile.delete();

            while (!serverSocket.isClosed()) {
                // Wait for client
                System.out.println("|=== Listening on port " + port + "... ===|");
                DatagramPacket receivedPacket = new DatagramPacket(buffer, bufferSize);
                
                // Client has sent file to server
                serverSocket.receive(receivedPacket);
                System.out.print("* Packet received from client on ");
                System.out.print(receivedPacket.getAddress().toString().substring(1));
                System.out.print(" * \n");

                // Count number of null values in buffer
                int zerosOffset = 0;
                int i = bufferSize - 1;
                while (buffer[i] == 0) {
                    zerosOffset++;
                    i--;
                }

                // Read packet from client and write it to a file
                FileOutputStream fileOutputStream = new FileOutputStream(outputFile, true);
                InputStream inputStream = new ByteArrayInputStream(receivedPacket.getData());
                int bytesToWrite = inputStream.read(buffer);

                while (bytesToWrite > 0) {
                    fileOutputStream.write(buffer, 0, bytesToWrite - zerosOffset);
                    bytesToWrite = inputStream.read(buffer);
                }

                System.out.println("* Packet received with client's file and saved as: " + outputPath + " *\n");

                // Close resources
                inputStream.close();
                fileOutputStream.close();
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
            DatagramSocket serverSocket = new DatagramSocket(port);
            serverSocket.setSoTimeout(timeout); // Timeout to close server socket

            while (!serverSocket.isClosed()) {
                // Wait for client
                System.out.println("|=== Listening on port " + port + "... ===|");
                DatagramPacket receivedPacket = new DatagramPacket(buffer, bufferSize);

                // Client has sent file to server
                serverSocket.receive(receivedPacket);
                System.out.print("* Packet received from client on ");
                System.out.print(receivedPacket.getAddress().toString().substring(1));
                System.out.print(" * \n");

                // Read string message from client
                BufferedReader fromClient = new BufferedReader(
                    new InputStreamReader(
                        new ByteArrayInputStream(receivedPacket.getData())
                    )
                );

                System.out.print("\nMessage from client:");
                System.out.println("\n------------------------------------------------------");
                System.out.println(fromClient.readLine()); 
                System.out.println("------------------------------------------------------\n");
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
