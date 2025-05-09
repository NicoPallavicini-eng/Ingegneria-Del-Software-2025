package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.ShipCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;

import java.io.Serializable;

public class ShipState extends TravellingState implements Serializable {
    public ShipState(Game game, ShipCard card) {
        super(game, card);
    }
}
