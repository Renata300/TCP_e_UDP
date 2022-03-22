// Recebe um pacote de algum cliente
// Separa o dado, o endere�o IP e a porta deste cliente
// Imprime o dado na tela

import java.io.*;
import java.net.*;

class Udp_Server {
   public static void main(String args[])  throws Exception {
      // cria socket do servidor com a porta 9876
      DatagramSocket serverSocket = new DatagramSocket(9876);

      //System.out.println("Antes");
      byte[] receiveData = new byte[1024];
      
      while(true) {
         //System.out.println("Iniciou");
         // declara o pacote a ser recebido
         DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

         // recebe o pacote do cliente
         serverSocket.receive(receivePacket);

         // pega os dados, o endere�o IP e a porta do cliente
         // para poder mandar a msg de volta
         String sentence = new String(receivePacket.getData());
         InetAddress IPAddress = receivePacket.getAddress();
         int port = receivePacket.getPort();

         System.out.println("porta: " + port);
         System.out.println("IP" + IPAddress);

         System.out.println("Mensagem recebida: " + sentence);
      }
   }
}

/**
    https://www.infoworld.com/article/2853780/socket-programming-for-scalable-systems.html
    https://www.devmedia.com.br/java-sockets-criando-comunicacoes-em-java/9465
    https://www.baeldung.com/a-guide-to-java-sockets
    https://docs.oracle.com/javase/7/docs/api/java/net/DatagramSocket.html
 */