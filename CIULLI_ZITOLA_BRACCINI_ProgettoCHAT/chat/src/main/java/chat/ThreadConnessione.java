package chat;


//import per leggere dati dal client
import java.io.BufferedReader;

//import per gestire possibili errori di input/output
import java.io.IOException;

//import per leggere dati dal socket
import java.io.InputStreamReader;

//import per inviare dati al client
import java.io.PrintWriter;

//import per gestire la connessione tramite socket
import java.net.Socket;

//import per liste dinamiche
import java.util.ArrayList; 

//classe che gestisce la connessione di un singolo client
public class ThreadConnessione implements Runnable {

private Socket client;             //socket del client connesso
private BufferedReader in;         //per leggere i messaggi inviati dal client
private PrintWriter out;           //per inviare messaggi al client
private ListaClient listaClient;   //riferimento alla lista di tutti i client connessi
private GestoreCanali gestoreCanali; //riferimento per gestire i canali disponibili

private String username;           //username scelto dal client
private String password;           //password del client
private ArrayList canaliJoinati = new ArrayList (); //lista dei canali in cui il client è joinato

public ThreadConnessione(Socket client, ListaClient listaClient, GestoreCanali gestoreCanali) throws IOException {
   this.client = client;
   this.listaClient = listaClient;
   this.gestoreCanali = gestoreCanali;

   //leggere dati dal client
   in = new BufferedReader(new InputStreamReader(client.getInputStream())); 
   
   //inviare dati al client
   out = new PrintWriter(client.getOutputStream(), true); 
}

@Override
public void run() {
   String messaggio;
   try {
       //ciclo principale per leggere i messaggi finché il client è connesso
       while ((messaggio = in.readLine()) != null) {
           gestisciMessaggio(messaggio); //interpreta e gestisce il comando ricevuto
       }
   } 
   
   catch (IOException e) {
       System.out.println("connessione persa con " + username);
   }
}

private void gestisciMessaggio(String messaggio) throws IOException {
   String[] parti = messaggio.split(";"); //separo comando e parametri
   String comando = parti[0];             //prendo il comando

   switch (comando) {
       case "LOGIN":
           gestisciLogin(parti);
           break;
       case "LIST_CHANNELS":
           gestisciListaCanali();
           break;
       case "JOIN_CHANNEL":
           gestisciEntraCanale(parti);
           break;
       case "MSG_CHANNEL":
           gestisciMessaggioCanale(parti);
           break;
       case "LOGOUT":
           gestisciLogout();
           break;
       default:
           out.println("ERROR;comando_non_valido");
   }
}

private void gestisciLogin(String[] parti) {
    username = parti[1];             //prendo lo username dal comando LOGIN inviato dal client
    password = parti[2];             //prendo la password dal comando LOGIN
    out.println("OK;LOGIN");         //confermo al client che il login è avvenuto correttamente
}

private void gestisciListaCanali() {
   out.println("CHANNEL_LIST;" + gestoreCanali.getListaCanali());
}

private void gestisciEntraCanale(String[] parti) {
   int idCanale = Integer.parseInt(parti[1]);
   
   if (!canaliJoinati.contains(idCanale)) {                 //aggiungo solo se non è già joinato
       canaliJoinati.add(idCanale);                         //aggiunge alla lista dei canali joinati
       gestoreCanali.aggiungiUtenteACanale(idCanale, this); //aggiunge utente al canale
       out.println("OK;JOIN_CHANNEL");                      //conferma join al client
   } 
   
   else {
       out.println("ERROR;JOIN_CHANNEL");   //errore se era già joinato
   }
}

private void gestisciMessaggioCanale(String[] parti) {
   int idCanale = Integer.parseInt(parti[1]); //prendo l'id del canale dal comando
   String testo = parti[2];                   //prendo il testo del messaggio

   if (canaliJoinati.contains(idCanale)) {                            //controllo se il client è joinato in quel canale
       gestoreCanali.inviaMessaggioCanale(idCanale, username, testo); //inoltra messaggio a tutti
       out.println("OK;MSG_CHANNEL");                                 //conferma invio al mittente
   } 
   
   else {
       out.println("ERROR;MSG_CHANNEL");                               //errore se non joinato
   }
}

private void gestisciLogout() throws IOException {
   out.println("OK;LOGOUT");                   //conferma logout
   listaClient.removeClient(client);           //rimuove il client dalla lista
   gestoreCanali.rimuoviUtenteDaCanali(this);  //rimuove il client da tutti i canali
   client.close();                              //chiude la connessione
}

public PrintWriter getOut() {
    return out;                      //restituisce l’oggetto per inviare messaggi al client
}

public String getUsername() {
    return username;                 //restituisce lo username del client
}
}

