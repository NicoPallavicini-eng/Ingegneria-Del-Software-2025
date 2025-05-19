package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Direction;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.GameState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.TravellingStateFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CombatZoneCardNotL extends CombatZoneCard implements Serializable {
    public CombatZoneCardNotL(boolean levelTwo, boolean used) {
        super(levelTwo, used, 4, 0, 3, createCannonballList());
    }

    private static List<Cannonball> createCannonballList() {
        List <Cannonball> cannonballList = new ArrayList<>();
        cannonballList.add(new Cannonball(false, Direction.NORTH, RowOrColumn.COLUMN));
        cannonballList.add(new Cannonball(false, Direction.WEST, RowOrColumn.ROW));
        cannonballList.add(new Cannonball(false, Direction.EAST, RowOrColumn.ROW));
        cannonballList.add(new Cannonball(true, Direction.SOUTH, RowOrColumn.COLUMN));
        return cannonballList;
    }

    public GameState createGameState(Game game){
        return TravellingStateFactory.createGameState(game, this);
    }

}
