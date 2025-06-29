package it.polimi.ingsw.galaxytrucker.Model.Cards;

import java.io.Serializable;
import java.util.List;


/**
 * Abstraction of Combat Zone,because there is 2 types of Combat Zone Card
 */
public abstract class CombatZoneCard extends Card implements Serializable {
    private final int daysLost;
    private final int crewLost;
    private final int cargoLost;
    private final List <Cannonball> cannonballList;
    private String name;

    /**
     * Constructor of CombatZoneCard
     * @param levelTwo
     * @param used
     * @param daysLost
     * @param crewLost
     * @param cargoLost
     * @param cannonballList
     */
    public CombatZoneCard(boolean levelTwo, boolean used, int daysLost, int crewLost, int cargoLost, List <Cannonball> cannonballList) {
        super(levelTwo, used);
        this.daysLost = daysLost;
        this.crewLost = crewLost;
        this.cargoLost = cargoLost;
        this.cannonballList = cannonballList;
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    /**
     * This function returns the number of days that Player is need to loose
     * @return int
     */
    public int getDaysLost() {
        return daysLost;
    }

    /**
     * This function returns the number of crew that Player is need to loose
     * @return int
     */
    public int getCrewLost() {
        return crewLost;
    }
    /**
     * This function returns the number of cargo that Player is need to loose
     * @return int
     */
    public int getCargoLost() {
        return cargoLost;
    }

    /**
     * These function returns a List of Cannonballs of a card
     * @return  List <Cannonball>
     */
    public List <Cannonball> getCannonballList() {
        return cannonballList;
    }
}
