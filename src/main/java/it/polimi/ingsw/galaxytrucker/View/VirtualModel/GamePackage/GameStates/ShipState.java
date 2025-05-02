package it.polimi.ingsw.galaxytrucker.View.VirtualModel.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.Cards.ShipCard;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.GamePackage.Game;

public class ShipState extends TravellingState {
    public ShipState(Game game, ShipCard card) {
        super(game, card);
    }
}
