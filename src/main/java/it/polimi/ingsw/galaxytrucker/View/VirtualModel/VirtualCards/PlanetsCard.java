package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualCards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.PlanetsCardVisitor;

import java.util.List;

public class PlanetsCard extends Card {
    private final List <Planet> planets;
    private final int daysToLose;

    public PlanetsCard(boolean levelTwo, boolean used, List <Planet> planets, int daysToLose) {
        super(levelTwo, used);
        this.planets = planets;
        this.daysToLose = daysToLose;
    }

    public List <Planet> getPlanetsList() {
        return planets;
    }

    public int getDaysToLose() {
        return daysToLose;
    }

/*
    public void process(Player player) {
        if (player.getEngages()) {
            Ship ship = player.getShip();

            Planet chosenPlanet = planets.get(player.getInput());
            chosenPlanet.setShipLanded(ship);

            ship.addBlocks(chosenPlanet.getBlocks());
            ship.setTravelDays(ship.getTravelDays() - daysToLose);
        }
    }

 */
}
