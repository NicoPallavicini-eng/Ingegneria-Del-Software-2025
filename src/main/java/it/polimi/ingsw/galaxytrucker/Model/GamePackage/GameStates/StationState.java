package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.StationCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;

import java.io.Serializable;

public class StationState extends TravellingState implements Serializable {
    public StationState(Game game, StationCard card) {
        super(game, card);
    }
}
