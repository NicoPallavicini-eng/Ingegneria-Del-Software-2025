package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.StardustCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;

public class StardustState extends TravellingState{
    public StardustState(Game game, StardustCard card) {
        super(game, card);
    }
}
