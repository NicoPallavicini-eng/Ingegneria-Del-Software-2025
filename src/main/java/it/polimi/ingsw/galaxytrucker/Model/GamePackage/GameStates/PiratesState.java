package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.PiratesCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;

public class PiratesState extends TravellingState{
    public PiratesState(Game game, PiratesCard card) {
        super(game, card);
    }
}
