package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.PlanetsCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Game.Game;
import it.polimi.ingsw.galaxytrucker.Model.Game.GameState;
import it.polimi.ingsw.galaxytrucker.Model.Game.SequentialTravellingState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;

import java.util.List;

public class PlanetsCard extends Card {
    private final List <Planet> planets;
    private final int daysToLose;

    public PlanetsCard(boolean levelTwo, boolean used, PlanetsCardVisitor visitor, List <Planet> planets, int daysToLose) {
        super(levelTwo, used, visitor);
        this.planets = planets;
        this.daysToLose = daysToLose;
    }

    public List <Planet> getPlanetsList() {
        return planets;
    }

    public int getDaysToLose() {
        return daysToLose;
    }

    public void acceptCardVisitorSequential(SequentialTravellingState state, PlanetsCardVisitor visitor, List <Player> players) {
        visitor.handlePlanetsCard(this, players);
    }

    public void acceptNextVisitor(GameState state, PlanetsCardVisitor visitor, Game game) {
        visitor.setNextStatePlanetsCard(state, game, this);
    }

    public void process(Player player) {
        if (player.getEngages()) {
            Ship ship = player.getShip();

            Planet chosenPlanet = planets.get(player.getInput());
            chosenPlanet.setShipLanded(ship);

            ship.addBlocks(chosenPlanet.getBlocks());
            ship.setTravelDays(ship.getTravelDays() - daysToLose);
        }
    }
}
