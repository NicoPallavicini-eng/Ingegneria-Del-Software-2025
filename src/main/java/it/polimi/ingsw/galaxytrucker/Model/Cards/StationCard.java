package it.polimi.ingsw.galaxytrucker.Model.Cards;
import it.polimi.ingsw.galaxytrucker.Model.Player;
import it.polimi.ingsw.galaxytrucker.Model.Ship;

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
        super.process();

        List <Player> players = getListOfPlayers();

        for (Player player : players) {
            if (player.playerEngages) {
                Ship ship = player.getShip();
                ship.addBlocks(blocks);
                ship.removeCrewMembers(crewNumberLost);
                ship.setTravelDays(- daysToLose); // negative because deducting
                break;
            }
        }
    }
}
