package it.polimi.ingsw.galaxytrucker.View.VirtualModel.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.Cards.OpenSpaceCard;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.GamePackage.GameEvents.ActivateEnginesEvent;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.GamePackage.GameEvents.EventHandler;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.GamePackage.GameEvents.IllegalEventException;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.PlayerShip.Ship;

public class OpenSpaceState extends TravellingState {

    public OpenSpaceState(Game game, OpenSpaceCard card) {
        super(game, card);
    }

    public void handleInput(ActivateEnginesEvent event)throws IllegalEventException {
        Ship ship = event.player().getShip();
        if (event.player().equals(currentPlayer)) {
            throw new IllegalEventException("It is not your turn");
        } else {
            EventHandler.handleEvent(event);
            EventHandler.moveFoward(ship, ship.getEnginePower(), game);
            nextPlayer();
            if (currentPlayer == null) {
                next();
            }
        }
    }
}
