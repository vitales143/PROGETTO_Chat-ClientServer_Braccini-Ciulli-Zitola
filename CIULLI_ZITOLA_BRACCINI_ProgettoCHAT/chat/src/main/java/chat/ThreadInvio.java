package chat;


//import per gestire errori I/O
import java.io.IOException;

//import per inviare dati al server
import java.io.PrintWriter;

//import per la connessione tramite socket
import java.net.Socket;

//import per leggere input da tastiera
import java.util.Scanner;

//classe che gestisce l'invio dei messaggi dal client
public class ThreadInvio implements Runnable {
	
//per leggere input da tastiera
 private Scanner sc;       

//per inviare dati al server
 private PrintWriter out;  

 public ThreadInvio(Socket socket) throws IOException {
     //inizializzo scanner per leggere messaggi
     sc = new Scanner(System.in);
     
     //inizializzo PrintWriter per inviare messaggi al server
     out = new PrintWriter(socket.getOutputStream(), true);
 }

 @Override
 public void run() {
     String input;
     
     // STAMPO LA LISTA DEI COMANDI DISPONIBILI
     System.out.println("comandi disponibili:");
     System.out.println("LIST_CHANNELS -> mostra i canali disponibili");
     System.out.println("JOIN_CHANNEL;<id> -> entra in un canale");
     System.out.println("MSG_CHANNEL;<id>;<testo> -> invia messaggio a un canale");
     System.out.println("LOGOUT -> disconnessione dalla chat");

     //login dell'utente
     System.out.println("\ninserisci username:");
     String username = sc.nextLine();
     
     System.out.println("inserisci password:");
     String password = sc.nextLine();

     //invio dati al server
     out.println("LOGIN;" + username + ";" + password);
     
    

     //ciclo principale per inviare messaggi o comandi
     while (true) {
         input = sc.nextLine();   //leggo input
         out.println(input);      //invio al server
     }
 }
}
