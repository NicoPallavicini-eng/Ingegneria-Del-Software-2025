package it.polimi.ingsw.galaxytrucker.Controller.Server;


import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;

/**
 * Observer interface for the Galaxy Trucker game.
 * It defines a method to update the observer with the current game state and a message.
 */
public interface Observer {
    void update(Game game, String message);
}
