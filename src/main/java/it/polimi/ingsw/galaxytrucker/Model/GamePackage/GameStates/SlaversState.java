package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.SlaversCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;

public class SlaversState extends TravellingState{
    public SlaversState(Game game, SlaversCard card) {
        super(game, card);
    }
}
