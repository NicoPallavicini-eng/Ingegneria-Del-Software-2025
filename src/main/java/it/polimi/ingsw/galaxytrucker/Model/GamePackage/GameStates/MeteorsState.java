package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.MeteorsCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;

public class MeteorsState extends TravellingState{

    public MeteorsState(Game game, MeteorsCard card) {
        super(game, card);
    }
}
