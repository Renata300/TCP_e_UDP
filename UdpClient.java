// Client-side

import java.io.*;
import java.net.*;

public class UdpClient {
    private static String serverIp = "127.0.0.1";
    private static int serverPort = 9876; // UDP port on which the server will be listening
    private static String fileToBeSent = "messageToServer.txt";
    private static String stringToBeSent = "Hello, World!";
    private static int bufferSize = 1024; // Client's bytes buffer size
    private byte[] buffer;

    public static void main(String args[]) {
        UdpClient client = new UdpClient();
        client.sendFile();
        // client.sendString();
    }
    
    public UdpClient() {
        buffer = new byte[bufferSize];
    }

    public void sendFile() {
        try {
            DatagramSocket clientSocket = new DatagramSocket();
            // Make sure packets can only be sent to this address.
            // Not really a way to establish a "connection" the same way TCP does, 
            // but a way to prevent sending or receiving packets to/from other addresses.
            // UDP is connectionless
            clientSocket.connect(InetAddress.getByName(serverIp), serverPort); // Can throw PortUnreachableException exception

            // Read file to be sent to server
            InputStream fileInputStream = new FileInputStream(fileToBeSent);

            // Send file to server
            while (fileInputStream.read(buffer) > 0) { // Bytes to send greater than 0
                DatagramPacket packet = new DatagramPacket(buffer, bufferSize);
                clientSocket.send(packet);
                buffer = new byte[bufferSize]; // Clear the buffer
            }

            System.out.println("* File sent to server on " + serverIp + ":" + serverPort + " *");

            // Close resources
            fileInputStream.close();
            clientSocket.close();
        }
        catch(PortUnreachableException e) {
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
            DatagramSocket clientSocket = new DatagramSocket();
            // Make sure packets can only be sent to this address.
            // Not really a way to establish a "connection" the same way TCP does, 
            // but a way to prevent sending or receiving packets to/from other addresses.
            // UDP is connectionless
            clientSocket.connect(InetAddress.getByName(serverIp), serverPort); // Can throw PortUnreachableException exception

            // Send string
            byte[] stringToBeSentInBytes = stringToBeSent.getBytes();
            InputStream bytesInputStream = new ByteArrayInputStream(stringToBeSentInBytes);
            
            while (bytesInputStream.read(buffer) > 0) { // Bytes to send greater than 0
                DatagramPacket packet = new DatagramPacket(buffer, bufferSize);
                clientSocket.send(packet);
                buffer = new byte[bufferSize]; // Clear the buffer
            }

            System.out.println("* Message string sent to server on " + serverIp + ":" + serverPort + " *");

            // Close resources
            bytesInputStream.close();
            clientSocket.close();
        }
        catch(PortUnreachableException e) {
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
