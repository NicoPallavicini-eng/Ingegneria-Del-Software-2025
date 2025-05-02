package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.MeteorsCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.GameState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;

import java.util.List;

public class MeteorsCard extends Card {
    private final List <Meteor> meteors;

    public MeteorsCard(boolean levelTwo, boolean used, List <Meteor> meteors) {
        super(levelTwo, used);
        this.meteors = meteors;
    }

    public List <Meteor> getMeteorsList() {
        return meteors;
    }

/*
    public void process(Ship ship) {
        for (Meteor meteor : meteors) {
            meteor.getHit(ship);
        }
    }

 */
}
