package it.polimi.ingsw.galaxytrucker.Model.Cards;

public class EpidemicCard extends Card {
    public EpidemicCard(boolean levelTwo, boolean used) {
        super(levelTwo, used);
        this.category = CardCategory.EPIDEMIC;
    }
}
