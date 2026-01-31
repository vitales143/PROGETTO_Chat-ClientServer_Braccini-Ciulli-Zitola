# **Chat multi-utente**

## **1\. Idea generale del progetto**

Il progetto consiste in una *chat TCP multi-utente in Java* con architettura *client-server*.

### **Server**

* Il server ascolta su una porta TCP (*5580*).  
* Accetta più client contemporaneamente.  
* Per ogni client crea un thread dedicato (ThreadConnessione).

### **Client**

* Il client si connette al server e avvia due thread:  
  * ThreadInvio: invio comandi e messaggi al server  
  * ThreadRicevi: ricezione e stampa dei messaggi provenienti dal server

### **Funzionalità**

Esistono *canali predefiniti* (es. generale, scuola, …).  
Un utente può:

* visualizzare la lista dei canali  
* entrare in un canale  
* inviare messaggi a un canale (solo se iscritto)  
* effettuare il logout La comunicazione avviene tramite *righe di testo*, una riga per comando, usando ; come separatore dei campi.

## **2\. Come eseguirlo**

### **Avvio del server**

All’avvio del server vengono stampati:

* server avviato  
* in attesa di connessioni...

### **Avvio del client**

Il client viene avviato in un *altro terminale*.  
È possibile avviare più client per simulare più utenti. All’avvio, il client:

* stampa i comandi disponibili  
* chiede username e password  
* invia automaticamente al server: LOGIN;;

## **3\. Protocollo: comandi e risposte**

La comunicazione è *testuale*, una riga per messaggio, con campi separati da ;.

### **3.1 Comandi inviati dal client al server**

#### **1\) LOGIN**

Client → Server : LOGIN;; Server → Client : OK;LOGIN   
Il server non verifica realmente le credenziali, ma le salva e risponde con OK.

#### **2\) LIST\_CHANNELS**

Client → Server : LIST\_CHANNELS  
Server → Client :CHANNEL\_LIST;1:generale,2:scuola,3:sport,4:tecnologia

#### **3\) JOIN\_CHANNEL**

Client → Server : JOIN\_CHANNEL;

* Operazione riuscita: OK;JOIN\_CHANNEL  
* Utente già iscritto: ERROR;JOIN\_CHANNEL

#### **4\) MSG\_CHANNEL**

Client → Server : MSG\_CHANNEL;;

* Se l’utente è iscritto al canale:  
  * conferma al mittente: OK;MSG\_CHANNEL  
  * messaggio inoltrato agli utenti del canale:  
     CANALE \<id\>  
    :\<mittente\>: \<testo\>  
* Se l’utente non è iscritto: ERROR;MSG\_CHANNEL

Il messaggio contiene un \\n e può essere stampato su due righe.

#### **5\) LOGOUT**

Client → Server : LOGOUT Server → Client : OK;LOGOUT

Dopo il logout il server:

* rimuove la socket dalla lista client  
* rimuove l’utente da tutti i canali  
* chiude la connessione

### **3.2 Errori generici**

Comando sconosciuto: ERROR;comando\_non\_valido

## **4\. Cosa fanno i vari comandi**

* *LOGIN;username;password*  
  Imposta username e password nel thread server e risponde OK.  
* *LIST\_CHANNELS*  
  Il server costruisce la lista dei canali tramite GestoreCanali.  
* *JOIN\_CHANNEL;id*  
  Aggiunge l’utente al canale se non già presente.  
* *MSG\_CHANNEL;id;testo*  
  Inoltra il messaggio a tutti gli utenti del canale e invia conferma al mittente.  
* *LOGOUT*  
  Rimuove l’utente dai canali, dalla lista client e chiude la socket.

## **5\. Esempio di utilizzo**

1. Avvio server (MainServer)  
2. Avvio client (ClientMain)  
3. Login: OK;LOGIN  
4. Lista canali: CHANNEL\_LIST;1:generale,2:scuola,3:sport,4:tecnologia  
5. Join canale: JOIN\_CHANNEL;1 OK;JOIN\_CHANNEL  
6. Invio messaggio: MSG\_CHANNEL;1;ciao a tutti Messaggio ricevuto: CANALE 1 :mario: ciao a tutti  
7. Logout: LOGOUT OK;LOGOUT

