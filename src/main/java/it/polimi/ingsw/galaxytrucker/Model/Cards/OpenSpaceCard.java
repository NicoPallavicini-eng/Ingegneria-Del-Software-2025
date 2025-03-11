package it.polimi.ingsw.galaxytrucker.Model.Cards;

public class OpenSpaceCard extends Card {
    public OpenSpaceCard(boolean levelTwo, boolean used) {
        super(levelTwo, used);
        this.category = CardCategory.OPEN_SPACE;
    }
}
