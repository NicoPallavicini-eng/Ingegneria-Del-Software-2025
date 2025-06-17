package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Direction;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.GameState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.TravellingStateFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CombatZoneCardL extends CombatZoneCard implements Serializable {
    private String name;

    public CombatZoneCardL(boolean levelTwo, boolean used, List<Cannonball> cannonballList) {
        super(levelTwo, used, 3, 2, 0, cannonballList);
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    public GameState createGameState(Game game){
        return TravellingStateFactory.createGameState(game, this);
    }
}
