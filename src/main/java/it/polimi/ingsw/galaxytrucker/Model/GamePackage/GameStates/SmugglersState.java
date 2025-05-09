package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.SmugglersCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;

import java.io.Serializable;

public class SmugglersState extends TravellingState implements Serializable {
    public SmugglersState(Game game, SmugglersCard card) {
        super(game, card);
    }
}
