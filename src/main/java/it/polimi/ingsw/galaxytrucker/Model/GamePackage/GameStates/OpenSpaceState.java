package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.OpenSpaceCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.ActivateEnginesEvent;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.EventHandler;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.EngineTile;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;

public class OpenSpaceState extends TravellingState{

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
