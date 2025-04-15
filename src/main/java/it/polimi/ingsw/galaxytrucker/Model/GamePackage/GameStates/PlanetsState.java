package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.PlanetsCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;

public class PlanetsState extends TravellingState{
    public PlanetsState(Game game, PlanetsCard card) {
        super(game, card);
    }
}
