package chat;


//import per gestione socket
import java.io.IOException;

//import per socket client
import java.net.Socket;

//import per lista utenti
import java.util.ArrayList;

//classe che mantiene la lista dei client connessi
public class ListaClient {

 private ArrayList<Socket> listaSockets;

 public ListaClient() {
     //inizializzo lista
     listaSockets= new ArrayList<>();
 }

 public synchronized void addClient(Socket c) {
     //aggiungo client alla lista
     listaSockets.add(c);
 }

 public synchronized void removeClient(Socket c) throws IOException {
     
	 //rimuovo client dalla lista e chiudo socket
     listaSockets.remove(c);
     c.close();
 }
}

