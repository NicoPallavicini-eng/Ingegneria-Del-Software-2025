package it.polimi.ingsw.galaxytrucker.Controller.Server;


import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;

import java.io.Serializable;

/**
 * RemoteObserver class implements the Observer interface and is used to observe changes in the Game state.
 * It updates the ServerController with the current game state and a message whenever the game is updated.
 */
public class RemoteObserver implements Observer, Serializable {
    private final transient ServerController serverController;

    /**
     * Constructor for RemoteObserver.
     * It registers the observer to the game and initializes the server controller.
     *
     * @param serverController the server controller to update the view
     * @param game             the game to observe
     */
    public RemoteObserver(ServerController serverController, Game game){
        this.serverController = serverController;
        game.addObserver(this);
    }

    /**
     * Updates the observer with the current game state and a message.
     * This method is called whenever there is a change in the game state.
     *
     * @param game    the current game state
     * @param message a message describing the update
     */
    @Override
    public void update(Game game, String message) {
        serverController.updateView(game, message);
    }

}
