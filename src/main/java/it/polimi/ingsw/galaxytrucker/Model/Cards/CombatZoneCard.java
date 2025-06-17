package it.polimi.ingsw.galaxytrucker.Model.Cards;

import java.io.Serializable;
import java.util.List;


public abstract class CombatZoneCard extends Card implements Serializable {
    private final int daysLost;
    private final int crewLost;
    private final int cargoLost;
    private final List <Cannonball> cannonballList;
    private String name;

    public CombatZoneCard(boolean levelTwo, boolean used, int daysLost, int crewLost, int cargoLost, List <Cannonball> cannonballList) {
        super(levelTwo, used);
        this.daysLost = daysLost;
        this.crewLost = crewLost;
        this.cargoLost = cargoLost;
        this.cannonballList = cannonballList;
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    public int getDaysLost() {
        return daysLost;
    }

    public int getCrewLost() {
        return crewLost;
    }

    public int getCargoLost() {
        return cargoLost;
    }

    public List <Cannonball> getCannonballList() {
        return cannonballList;
    }
}
