package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.OpenSpaceCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.SequentialTravellingState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;

import java.util.List;

public class OpenSpaceCard extends Card {
    public OpenSpaceCard(boolean levelTwo, boolean used, OpenSpaceCardVisitor visitor) {
        super(levelTwo, used, visitor);
    }

    public void acceptCardVisitorSequential(SequentialTravellingState state, OpenSpaceCardVisitor visitor, List <Player> players) {
        visitor.handleOpenSpaceCard(this, players);
    }

    public void acceptNextVisitor(GameState state, OpenSpaceCardVisitor visitor, Game game) {
        visitor.setNextStateOpenSpaceCard(state, game, this);
    }

    public void process1(Player player) {
        Ship ship = player.getShip();
        int enginePower = ship.getEnginePower() + player.getInput();

        if (enginePower == 0) {
            // If a player has zero engine power he is lost in space and out of further travelling
            ship.setTravelDays(null);
        } else {
            // ship.setEnginePower(enginePower); TODO when engine setter works
        }
    }

    public void process2(Player player) {
        Ship ship = player.getShip();

        ship.setTravelDays(ship.getTravelDays() + ship.getEnginePower());
    }
}
