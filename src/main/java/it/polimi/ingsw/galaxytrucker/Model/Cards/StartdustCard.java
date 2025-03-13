package it.polimi.ingsw.galaxytrucker.Model.Cards;

public class StartdustCard extends Card {
    public StartdustCard(boolean levelTwo, boolean used) {
        super(levelTwo, used);
        this.category = CardCategory.STARDUST;
    }

    @Override
    public void process() {
        super.process();

        // specific process
    }
}
