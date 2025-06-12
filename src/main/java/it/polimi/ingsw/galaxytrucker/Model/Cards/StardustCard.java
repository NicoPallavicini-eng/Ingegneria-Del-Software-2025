package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.GameState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.TravellingStateFactory;

import java.io.Serializable;

public class StardustCard extends Card implements Serializable {
    private String name;

    public StardustCard(boolean levelTwo, boolean used) {
        super(levelTwo, used);
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    public GameState createGameState(Game game){
        return TravellingStateFactory.createGameState(game, this);
    }
}
