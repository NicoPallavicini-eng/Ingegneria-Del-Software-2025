package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Ship;

import java.util.List;

public class MeteorsCard extends Card {
    private final List <Meteor> meteors;

    public MeteorsCard(boolean levelTwo, boolean used, List <Meteor> meteors) {
        super(levelTwo, used);
        this.category = CardCategory.METEORS;

        meteors =

        this.meteors = meteors;
    }

    public List <Meteor> getMeteorsList() {
        return meteors;
    }

    @Override
    public void process() {
        super.process();

        // TODO link with proper tiles and Ship

        // temp creating a list of ships with different crew sizes
        List <Ship> ships = List.of(
                new Ship(),
                new Ship(),
                new Ship(),
                new Ship()
        );

        // roll dice
        // foreach ship {
            // if column/row is part of ship {
                // if meteor.isBig() && there is a tile {
                    // getHit()
                // } else if !meteor.isBig() && there ia a tile {
                    // if (hits column && !cannon in line && !shield) |
                    // | (hits row && !cannon in row or adjacent rows && !shield) {
                        // getHit()
                    // }
                // }
            // }
        // }
    }
}
