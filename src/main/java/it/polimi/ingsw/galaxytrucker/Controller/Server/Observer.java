package it.polimi.ingsw.galaxytrucker.Controller.Server;


import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;

public interface Observer {
    void update(Game game, String message);
}
