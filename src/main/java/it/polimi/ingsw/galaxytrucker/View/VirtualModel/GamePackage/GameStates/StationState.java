package it.polimi.ingsw.galaxytrucker.View.VirtualModel.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.Cards.StationCard;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.GamePackage.Game;

public class StationState extends TravellingState {
    public StationState(Game game, StationCard card) {
        super(game, card);
    }
}
