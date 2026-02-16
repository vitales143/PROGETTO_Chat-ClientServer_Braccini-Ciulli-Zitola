package chat;


//import per inviare messaggi ai client
import java.io.PrintWriter;

//import per usare liste dinamiche di utenti
import java.util.ArrayList;

//classe che rappresenta un canale della chat
public class Canale {

 private int id;                              //id univoco del canale
 private String nome;                         //nome del canale (es. "generale")
 private ArrayList<ThreadConnessione> utenti; //lista degli utenti collegati al canale

 //costruttore del canale
 public Canale(int id, String nome) {
     this.id =id;               //assegna id del canale
     this.nome= nome;           //assegna nome del canale
     utenti=new ArrayList<>();  //inizializza lista utenti
 }

 //getter per ottenere l'id del canale
 public int getId() {
     return id;
 }

 //getter per ottenere il nome del canale
 public String getNome() {
     return nome;
 }

 //aggiunge un utente al canale
 public void aggiungiUtente(ThreadConnessione utente) {
     utenti.add(utente);
 }

 //rimuove un utente dal canale
 public void rimuoviUtente(ThreadConnessione utente) {
     utenti.remove(utente);
 }

 //invia un messaggio a tutti gli utenti presenti nel canale
 public void inviaMessaggio(String mittente, String messaggio) {
	 
	 for (int i = 0; i < utenti.size(); i++) {
		    ThreadConnessione u = utenti.get(i); //prendo l'utente alla posizione i
		    
		    u.getOut().println("CANALE " + id + "\n:" + mittente + ": " + messaggio);
		}
 }
}

