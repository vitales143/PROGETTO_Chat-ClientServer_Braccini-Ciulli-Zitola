package chat;


//import per server socket
import java.net.ServerSocket;

//import per socket client
import java.net.Socket;

//import per lista thread
import java.util.ArrayList;


//classe principale del server
public class MainServer {

 public static void main(String[] args) {

	//porta del server
     final int PORT = 5580; 

     try {
         //creo server socket
         ServerSocket serverSocket = new ServerSocket(PORT);

         //lista dei thread client
      //<Thread> indica che la lista conterrà solo oggetti di tipo Thread
      //le parentesi angolari servono a specificare il tipo di oggetti nella lista 
      //<> a destra è per non dover riscrivere il tipo due volte
         ArrayList<Thread> listaThread = new ArrayList<>();
         
         //lista dei client connessi
         ListaClient listaClient = new ListaClient();
         
         //gestore dei canali predefiniti
         GestoreCanali gestoreCanali = new GestoreCanali();

         System.out.println("server avviato");
         System.out.println("in attesa di connessioni...");

         // ciclo infinito per accettare nuovi client
         while (true) {
             Socket nuovoClient = serverSocket.accept();
             listaClient.addClient(nuovoClient);

             //creo un thread per gestire questo client
             Thread t = new Thread(new ThreadConnessione(nuovoClient, listaClient, gestoreCanali));
             listaThread.add(t);
             t.start();
         }

     } 
     
     catch (Exception e) {
         System.out.println("errore server");
     }
 }
}
