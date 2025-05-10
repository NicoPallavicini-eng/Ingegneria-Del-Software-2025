package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.StardustCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.EventHandler;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StardustState extends TravellingState implements Serializable {
    public StardustState(Game game, StardustCard card) {
        super(game, card);
    }
    private List<Player> turns;

    public void init(){
        turns = new ArrayList<>(game.getListOfPlayers());
        Collections.reverse(turns);
        process();
    }

    private void process(){
        for (Player player : turns) {
            EventHandler.moveBackward(player.getShip(), player.getShip().getExposedConnectors(), game);
        }
        next();
    }
}
