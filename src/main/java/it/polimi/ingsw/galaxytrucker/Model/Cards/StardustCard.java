package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.StardustCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Game.Game;
import it.polimi.ingsw.galaxytrucker.Model.Game.GameState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;

import java.util.List;

public class StardustCard extends Card {
    public StardustCard(boolean levelTwo, boolean used, StardustCardVisitor visitor) {
        super(levelTwo, used, visitor);
    }

    public void acceptCardVisitorParallel(StardustCardVisitor visitor, Player player, List <Ship> ships) {
        for (Ship ship : ships) {
            visitor.handleStardustCard(this, ship);
        }
    }

    public void acceptNextVisitor(GameState state, StardustCardVisitor visitor, Game game, Card card) {
        visitor.setNextStateStardustCard(state, game, this);
    }

    public void process(Ship ship) {
        ship.setTravelDays(ship.getTravelDays() - ship.getExposedConnectors());
    }
}
