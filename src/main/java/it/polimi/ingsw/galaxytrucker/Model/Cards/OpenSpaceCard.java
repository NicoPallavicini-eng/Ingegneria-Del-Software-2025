package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Ship;

import java.util.List;

public class OpenSpaceCard extends Card {
    public OpenSpaceCard(boolean levelTwo, boolean used) {
        super(levelTwo, used);
        this.category = CardCategory.OPEN_SPACE;
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

        // TODO use threads
        // forall {
            // if no engine {
                // lostInSpace
            // } else {
                // setNumberOfEnginesOn(numberOfEnginesOn)
            // }
        // }
        // forall in reverse order {
            // advance(numberOfEnginesOn)
        // }
    }
}
