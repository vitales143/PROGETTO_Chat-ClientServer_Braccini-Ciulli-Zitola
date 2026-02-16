package chat;

//import per usare liste dinamiche
import java.util.ArrayList; 

//classe che gestisce tutti i canali predefiniti della chat
public class GestoreCanali {

 //<> indica che la lista conterrà solo oggetti di tipo Canale
 private ArrayList<Canale> canali; //lista dei canali disponibili

 public GestoreCanali() {
     canali = new ArrayList<>(); //inizializzo lista canali
     //aggiungo canali predefiniti con id e nome
     canali.add(new Canale(1, "generale"));
     canali.add(new Canale(2, "scuola"));
     canali.add(new Canale(3, "sport"));
     canali.add(new Canale(4, "tecnologia"));
 }

 //ritorna la lista dei canali in formato stringa: id:nome,id:nome,...
 public String getListaCanali() {
     String lista = "";
     
   //ciclo su tutti i canali usando indici
     for (int i = 0; i < canali.size(); i++) { 
         Canale c = canali.get(i);
         lista += c.getId() + ":" + c.getNome() + ",";
     }
     
   //rimuovo ultima virgola altrimenti l'output verrebbe così "1:generale,2:scuola,3:sport,"
     if (lista.length() > 0) {
         lista = lista.substring(0, lista.length() - 1); 
     }
     return lista;
 }

 //aggiunge un utente a un canale specifico tramite idCanale
 public boolean aggiungiUtenteACanale(int idCanale, ThreadConnessione utente) {
     for (int i = 0; i < canali.size(); i++) {
         Canale c = canali.get(i);
         
         if (c.getId() == idCanale) {  //trovo canale giusto
             c.aggiungiUtente(utente); //aggiungo utente
             return true;              //esito positivo
         }
     }
     return false; //canale non trovato
 }

 //rimuove un utente da tutti i canali
 public void rimuoviUtenteDaCanali(ThreadConnessione utente) {
     for (int i = 0; i < canali.size(); i++) {
         Canale c = canali.get(i);
         c.rimuoviUtente(utente);
     }
 }

 //invia un messaggio a tutti gli utenti di un canale specifico
 public void inviaMessaggioCanale(int idCanale, String mittente, String messaggio) {
     for (int i = 0; i < canali.size(); i++) {
         Canale c = canali.get(i);
         
         if (c.getId() == idCanale) {
             c.inviaMessaggio(mittente, messaggio);
             break; //esco dal ciclo perché il canale è stato trovato
         }
     }
 }
}
