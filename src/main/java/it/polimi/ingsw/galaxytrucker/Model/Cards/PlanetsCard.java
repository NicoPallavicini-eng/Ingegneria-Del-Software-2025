package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.PlanetsCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Player;

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

    public void acceptCardVisitor(PlanetsCardVisitor visitor) {
        visitor.handlePlanetsCard(this);
    }

    @Override
    public void process() {
        int planetsNumber = planets.size();
        boolean availablePlanets = true;

        List <Player> players = getListOfPlayers();

        for (Player player : players) {
            if (player.playerEngages) {
                // TODO implement player choosing planet
                Planet chosenPlanet = getChosenPlanet();
                chosenPlanet.setShipLanded(player.getShip());

                ship.addBlocks(chosenPlanet.getBlocksList());
                ship.setTravelDays(- daysToLose); // negative because deducting

                // Check for available planets
                availablePlanets = false;
                for (Planet planet : planets) {
                    if (planet.getShipLanded() == NULL) {
                        availablePlanets = true;
                    }
                }
            }

            // TODO should be in if?
            if (!availablePlanets) {
                break;
            }
        }
    }
}
