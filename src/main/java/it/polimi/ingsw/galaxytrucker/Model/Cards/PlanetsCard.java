package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Player;

import it.polimi.ingsw.galaxytrucker.Model.Ship;

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
        super.process();
        int planetNumber = 0;
        Planet planet = planets.get(planetNumber);

        List <Player> players = getListOfPlayers();

        for (Player player : players) {
            if (player.playerEngages) {
                Ship ship = player.getShip();
                ship.addBlocks(planet.getBlocksList());
                ship.setTravelDays(- daysToLose); // negative because deducting
                planetNumber++;
                if (planetNumber == planets.size()) {
                    break;
                }
            }
        }
    }
}
