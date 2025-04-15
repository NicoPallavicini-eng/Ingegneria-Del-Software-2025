package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.StationCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;

public class StationState extends TravellingState{
    public StationState(Game game, StationCard card) {
        super(game, card);
    }
}
