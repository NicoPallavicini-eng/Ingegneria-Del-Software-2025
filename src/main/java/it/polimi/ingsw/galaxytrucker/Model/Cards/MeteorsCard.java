package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.GameState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.TravellingStateFactory;

import java.io.Serializable;
import java.util.List;

public class MeteorsCard extends Card implements Serializable {
    private final List <Meteor> meteors;

    public MeteorsCard(boolean levelTwo, boolean used, List <Meteor> meteors) {
        super(levelTwo, used);
        this.meteors = meteors;
    }

    public List <Meteor> getMeteorsList() {
        return meteors;
    }

    public GameState createGameState(Game game){
        return TravellingStateFactory.createGameState(game, this);
    }
}
