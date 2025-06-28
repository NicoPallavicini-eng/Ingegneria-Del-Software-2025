package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.StardustCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.EventHandler;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//todo non vedo la carta dopo stardust, non viene stampata

public class StardustState extends TravellingState implements Serializable {
    private StardustCard currentCard;

    public StardustState(Game game, StardustCard card) {
        super(game, card);
        currentCard = card;
    }
    private List<Player> turns;

    public void init(){
        turns = new ArrayList<>(game.getListOfActivePlayers());
        Collections.reverse(turns);
        process();
        game.notifyObservers(game, "stardust");

    }

    private void process(){
        for (Player player : turns) {
            EventHandler.moveBackward(player.getShip(), player.getShip().getExposedConnectors(), game);
        }
        game.notifyObservers(game, "stardustEnd");
        game.notifyObservers(game, "newcard");
        next();
    }
}
