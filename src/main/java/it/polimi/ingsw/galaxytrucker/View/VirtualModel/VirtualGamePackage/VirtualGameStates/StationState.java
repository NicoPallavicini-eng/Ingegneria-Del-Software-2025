package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGameStates;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualCards.StationCard;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.Game;

public class StationState extends TravellingState {
    public StationState(Game game, StationCard card) {
        super(game, card);
    }
}
