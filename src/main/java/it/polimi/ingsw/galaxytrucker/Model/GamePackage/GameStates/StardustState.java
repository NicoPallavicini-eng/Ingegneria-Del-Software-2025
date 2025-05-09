package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.StardustCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;

import java.io.Serializable;

public class StardustState extends TravellingState implements Serializable {
    public StardustState(Game game, StardustCard card) {
        super(game, card);
    }
}
