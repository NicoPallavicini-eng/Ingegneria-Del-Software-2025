package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;
import it.polimi.ingsw.galaxytrucker.Network.Client.SocketClient;
import it.polimi.ingsw.galaxytrucker.Network.Client.VirtualClient;
import it.polimi.ingsw.galaxytrucker.SceneManager;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Background;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.rmi.RemoteException;

public class MyScene {
    protected Scene scene;
    protected Game game;
    protected String nickname;
    protected StackPane root;
    protected Background background;
    protected final int SCENE_WIDTH = 1024;
    protected final int SCENE_HEIGHT = 760;
    protected SceneManager sceneManager;
    protected final VirtualClient rmiClient;
    protected final SocketClient socketClient;


    public MyScene(Game game, SceneManager sceneManager) {
        this.game = game;
        this.background = new Background();
        this.root = new StackPane();
        this.sceneManager = sceneManager;
        root.getChildren().add(background);
        this.rmiClient = sceneManager.getRmiClient();
        this.socketClient = sceneManager.getSocketClient();
    }

    public  void sendMessageToServer(String message, String nickname) {
        if (rmiClient != null) {
            try {
                sceneManager.getServer().handleUserInput(sceneManager.getRmiClient(), message);
                sceneManager.getServer().showMessage(sceneManager.getRmiClient() + message);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        else if (socketClient != null) {
            try {
                socketClient.getServerSocket().sendMessageToServer(message, nickname);
            } catch (IOException e){
                throw new RuntimeException(e);
            }
        }
        else {
            throw new IllegalEventException("No client connection available.");
        }
    }


}
