package it.polimi.ingsw.galaxytrucker.Model.Cards;

import java.util.List;

public class StationCard extends Card {
    private final int crewNumberNeeded;
    private final List <Integer> blocks; // Integer
    private final int daysToLose;

    public StationCard(boolean levelTwo, boolean used, int crewNumberNeeded, List <Integer> blocks, int daysToLose) {
        super(levelTwo, used);
        this.category = CardCategory.STATION;
        this.crewNumberNeeded = crewNumberNeeded;
        this.blocks = blocks;
        this.daysToLose = daysToLose;
    }

    public List<Integer> getBlockList() {
        return blocks;
    }

    public int getDaysToLose() {
        return daysToLose;
    }

    public int getCrewNumberNeeded() {
        return crewNumberNeeded;
    }

    @Override
    public void process() {
        //
    }
}
