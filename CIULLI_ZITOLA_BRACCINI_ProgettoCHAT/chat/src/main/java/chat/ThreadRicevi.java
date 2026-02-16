package chat;


//import per leggere dati dal server
import java.io.BufferedReader;

//import per gestire errori I/O
import java.io.IOException;

//import per leggere input dal server
import java.io.InputStreamReader;

//import per la socket
import java.net.Socket;

//classe che gestisce la ricezione dei messaggi dal server
public class ThreadRicevi implements Runnable {

//socket del client
 private Socket socket;        
 
//per leggere i messaggi dal server
 private BufferedReader in;    

 public ThreadRicevi(Socket socket) throws IOException {
     this.socket = socket;
     
     //inizializzo il lettore per ricevere messaggi dal server
     in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
 }

 @Override
 public void run() {

     String messaggio;

     try {
         //leggo i messaggi finché il server è connesso
         while ((messaggio = in.readLine()) != null) {
        	 
        	//stampo il messaggio ricevuto
             System.out.println(messaggio); 
         }
         
      //chiudo la connessione se il server chiude
         socket.close(); 
     } 
     
     catch (IOException e) {
    	 
         // messaggio di errore se c'è problema di connessione
         System.out.println("errore di connessione");
     }
 }
}
