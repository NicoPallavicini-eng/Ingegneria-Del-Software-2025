package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGameStates;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualCards.ShipCard;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.Game;

public class ShipState extends TravellingState {
    public ShipState(Game game, ShipCard card) {
        super(game, card);
    }
}
