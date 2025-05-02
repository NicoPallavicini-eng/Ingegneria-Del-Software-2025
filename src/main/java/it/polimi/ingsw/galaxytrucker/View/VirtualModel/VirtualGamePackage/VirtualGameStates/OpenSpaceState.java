package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGameStates;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualCards.OpenSpaceCard;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.Game;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGameEvents.ActivateEnginesEvent;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGameEvents.EventHandler;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGameEvents.IllegalEventException;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualPlayerShip.Ship;

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
