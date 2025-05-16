package it.polimi.ingsw.galaxytrucker.Network.Server;

import java.io.IOException;

public interface VirtualServerSocketInterface {
    void sendMessageToServer(String message,String nickname)throws IOException;
}
