package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.EpidemicCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;

public class EpidemicState extends TravellingState{
    public EpidemicState(Game game, EpidemicCard card) {
        super(game, card);
    }
}
