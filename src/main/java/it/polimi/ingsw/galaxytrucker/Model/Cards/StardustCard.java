package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.GameState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.TravellingStateFactory;

import java.io.Serializable;

public class StardustCard extends Card implements Serializable {
    public StardustCard(boolean levelTwo, boolean used) {
        super(levelTwo, used);
    }

    public GameState createGameState(Game game){
        return TravellingStateFactory.createGameState(game, this);
    }
}
