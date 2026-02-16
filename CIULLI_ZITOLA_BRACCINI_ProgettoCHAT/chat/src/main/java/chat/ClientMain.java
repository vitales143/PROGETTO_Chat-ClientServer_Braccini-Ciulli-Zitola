package chat;


//import per gestire possibili errori di connessione o I/O
import java.io.IOException;
//import per usare le socket (connessione client-server TCP)
import java.net.Socket;

//classe principale del client
public class ClientMain {

 public static void main(String[] args) {

     Socket clientSocket;

     try {
         //creo la connessione con il server sulla porta 5580
         clientSocket = new Socket("127.0.0.1", 5580);

         //creo thread che gestisce l'invio dei messaggi
         Thread invioThread = new Thread(new ThreadInvio(clientSocket));
         //creo thread che gestisce la ricezione dei messaggi
         Thread riceviThread = new Thread(new ThreadRicevi(clientSocket));

         //avvio i thread
         invioThread.start();
         riceviThread.start();
     } 
     
     catch (IOException e) {
         //messaggio di errore se non riesco a connettermi
         System.out.println("impossibile connettersi al server");
     }
 }
}
