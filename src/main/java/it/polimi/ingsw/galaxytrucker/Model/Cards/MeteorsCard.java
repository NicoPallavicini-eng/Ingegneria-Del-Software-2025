package it.polimi.ingsw.galaxytrucker.Model.Cards;

import java.util.List;

public class MeteorsCard extends Card {
    private final List <Meteor> meteors;

    public MeteorsCard(boolean levelTwo, boolean used, List <Meteor> meteors) {
        super(levelTwo, used);
        this.category = CardCategory.METEORS;
        this.meteors = meteors;
    }

    public List <Meteor> getMeteorsList() {
        return meteors;
    }
}
