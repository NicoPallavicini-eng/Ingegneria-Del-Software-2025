package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.OpenSpaceCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;

public class OpenSpaceState extends TravellingState{
    public OpenSpaceState(Game game, OpenSpaceCard card) {
        super(game, card);
    }
}
