package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.PlanetsCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.ChoosePlanetEvent;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

public class PlanetsState extends TravellingState{
    private PlanetsCard Currentcard;
    private Player currentPlayer;

    public PlanetsState(Game game, PlanetsCard card) {
        super(game, card);
    }

    public void handleInput(ChoosePlanetEvent event){
        if(!event.player().equals(currentPlayer) ){
            throw new IllegalEventException("It is not your turn to land");
        }

    }


}
