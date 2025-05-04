package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualCards;

import java.util.List;

public class VirtualPlanetsCard extends VirtualCard {
    private final List <VirtualPlanet> planets;
    private final int daysToLose;

    public VirtualPlanetsCard(boolean levelTwo, boolean used, List <VirtualPlanet> planets, int daysToLose) {
        super(levelTwo, used);
        this.planets = planets;
        this.daysToLose = daysToLose;
    }

    public List <VirtualPlanet> getPlanetsList() {
        return planets;
    }

    public int getDaysToLose() {
        return daysToLose;
    }
}
