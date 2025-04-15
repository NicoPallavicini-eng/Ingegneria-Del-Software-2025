package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.ShipCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;

public class ShipState extends TravellingState{
    public ShipState(Game game, ShipCard card) {
        super(game, card);
    }
}
