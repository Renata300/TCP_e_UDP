import java.io.*;
import java.net.*;
import java.util.*;

public class Tcp_Cliente {
    public static void main(String[] args) {
        try {
            Socket cliente = new Socket("paulo",12345);
            ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
            Date data_atual = (Date)entrada.readObject();
            //JOptionPane.showMessageDialog(null,"Data recebida do servidor:" + data_atual.toString());
            entrada.close();
            System.out.println("Conexão encerrada");
        } catch(Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}