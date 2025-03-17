package it.polimi.ingsw.galaxytrucker.Model.Cards;
import it.polimi.ingsw.galaxytrucker.Model.Ship;
import java.util.List;
import java.util.Optional;

public class EpidemicCard extends Card {
    public EpidemicCard(boolean levelTwo, boolean used) {
        super(levelTwo, used);
        this.category = CardCategory.EPIDEMIC;
    }

    @Override
    public void process() {
        super.process();

        // TODO link with proper tiles logic and Ship

        // foreach ship { // TODO use threads
            // foreach cabin check adjacent tiles {
                // if there is at least another cabin in those {
                    // remove one occupant from each of the connected cabins
                    // only remove one from each cabin
                // }
            // }
        // }
    }
}
