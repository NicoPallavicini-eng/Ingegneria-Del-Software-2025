package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.SmugglersCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;

public class SmugglersState extends TravellingState{
    public SmugglersState(Game game, SmugglersCard card) {
        super(game, card);
    }
}
