package it.polimi.ingsw.galaxytrucker.Network.Server;

import it.polimi.ingsw.galaxytrucker.Network.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class VirtualServerSocket implements VirtualServerSocketInterface {
    //final PrintWriter output;
    private final ObjectOutputStream objOut;

    public VirtualServerSocket(ObjectOutputStream output) throws IOException {
        this.objOut = output;
    }

    /*
    Metodi invocati lato client per
comunicare con il server.
Nell'implementazione, viene
scritto il messaggio che porta
all'invocazione lato server del
metodo corrispondente
     */
    //output aggiuge in coda i messaggi
    @Override
    public void sendMessageToServer(String message,String nickname) throws IOException{
        //output.println(message);
        //output.flush();
        //objOut.writeObject(message);
        //objOut.flush();
        Message obj = new Message("String",null,message);
        obj.setNickname(nickname);
        objOut.writeObject(obj);
        objOut.flush();
        //System.out.println("Message sent to server: " + message);
    }
}
