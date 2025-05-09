package it.polimi.ingsw.galaxytrucker.Model.Cards;

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

/*
    public void process(Ship ship) {
        for (Meteor meteor : meteors) {
            meteor.getHit(ship);
        }
    }

 */
}
