package it.polimi.ingsw.galaxytrucker.Network.Server;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Network.Client.VirtualClient;
import it.polimi.ingsw.galaxytrucker.Network.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.util.List;

public class VirtualServerSocket implements VirtualServer {
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

    public void connect(VirtualClient virtualClient) {}
    public void showMessage(String input) {}
    public void handleUserInput(VirtualClient virtualClient, String input){}
    public List<VirtualClient> getClients() {
        return null;
    }
    public void ping() {}
    public void addNickname(String nickname) {}
    public void mapNicknameClient(VirtualClient virtualClient, String nickname){}
    public Game getGame() {
        return null;
    }
}
