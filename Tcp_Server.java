import java.io.*;
import java.net.*;
import java.util.*;

public class Tcp_Server {
    public static void main(String[] args) {
        try {
            // Instancia o ServerSocket ouvindo a porta 12345
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Servidor ouvindo a porta 12345");
      
            while(true) {
                // o método accept() bloqueia a execução até que
                // o servidor receba um pedido de conexão
                Socket cliente = serverSocket.accept();
                System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());

                ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());

                saida.flush();
                saida.writeObject(new Date());
                saida.close();
                cliente.close();
            }
        } catch(Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}