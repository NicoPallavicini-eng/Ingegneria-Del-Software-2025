package it.polimi.ingsw.galaxytrucker.Model.Cards;

import java.util.List;

public class PlanetsCard extends Card {
    private final List <Planet> planets;
    private final int daysToLose;

    public PlanetsCard(boolean levelTwo, boolean used, List <Planet> planets, int daysToLose) {
        super(levelTwo, used);
        this.category = CardCategory.PLANETS;
        this.planets = planets;
        this.daysToLose = daysToLose;
    }

    public List <Planet> getPlanetsList() {
        return planets;
    }

    public int getDaysToLose() {
        return daysToLose;
    }

    @Override
    public void process() {
        //
    }
}
