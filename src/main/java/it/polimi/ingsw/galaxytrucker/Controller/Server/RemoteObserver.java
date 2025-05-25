package it.polimi.ingsw.galaxytrucker.Controller.Server;


import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;

import java.io.Serializable;

public class RemoteObserver implements Observer, Serializable {
    private final transient ServerController serverController;

    public RemoteObserver(ServerController serverController, Game game){
        this.serverController = serverController;
        game.addObserver(this);
    }

    @Override
    public void update(Game game, String message) {
        serverController.updateView(game, message);
    }

}
