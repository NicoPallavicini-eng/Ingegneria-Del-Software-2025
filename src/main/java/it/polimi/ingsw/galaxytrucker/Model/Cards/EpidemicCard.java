package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.GameState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.TravellingStateFactory;

import java.io.Serializable;

public class EpidemicCard extends Card implements Serializable {
    public EpidemicCard(boolean levelTwo, boolean used) {
        super(levelTwo, used);
    }


    public GameState createGameState(Game game){
        return TravellingStateFactory.createGameState(game, this);
    }
}
