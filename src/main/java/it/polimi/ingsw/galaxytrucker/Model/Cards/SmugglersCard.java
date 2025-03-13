package it.polimi.ingsw.galaxytrucker.Model.Cards;
import java.util.List;

public class SmugglersCard extends Card {
    private final int firepower;
    private final List <Integer> blocks;
    private final int lostBlocksNumber;
    private final int daysToLose;

    public SmugglersCard(boolean levelTwo, boolean used, int firepower, List <Integer> blocks, int lostBlocksNumber, int daysToLose) {
        super(levelTwo, used);
        this.category = CardCategory.SMUGGLERS;
        this.firepower = firepower;
        this.blocks = blocks;
        this.lostBlocksNumber = lostBlocksNumber;
        this.daysToLose = daysToLose;
    }

    public int getFirepower() {
        return firepower;
    }

    public List <Integer> getBlocksList() {
        return blocks;
    }

    public int getDaysToLose() {
        return daysToLose;
    }

    public int getLostBlocksNumber() {
        return lostBlocksNumber;
    }

    @Override
    public void process() {
        //
    }
}
