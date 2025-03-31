package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.OpenSpaceCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Game.Game;
import it.polimi.ingsw.galaxytrucker.Model.Game.GameState;
import it.polimi.ingsw.galaxytrucker.Model.Game.SequentialTravellingState;
import it.polimi.ingsw.galaxytrucker.Model.Player;
import it.polimi.ingsw.galaxytrucker.Model.Ship;

import java.util.List;

public class OpenSpaceCard extends Card {
    private boolean goNext;

    public OpenSpaceCard(boolean levelTwo, boolean used, OpenSpaceCardVisitor visitor) {
        super(levelTwo, used, visitor);
    }

    public void acceptCardVisitorSequential(SequentialTravellingState state, OpenSpaceCardVisitor visitor, List <Player> players) {
        visitor.handleOpenSpaceCard(this, players);
    }

    public void acceptNextVisitor(GameState state, OpenSpaceCardVisitor visitor, Game game, Card card) {
        visitor.setNextStateOpenSpaceCard(state, game, this);
    }

    public void setGoNext(boolean goNext) {
        this.goNext = goNext;
    }

    public boolean getGoNext() {
        return goNext;
    }

    public void process1(Player player, List <Integer> engineChosenList) {
        Ship ship = player.getShip();
        int enginePower = player.getPlayerInput(); // TODO this

        if (enginePower == 0) {
            // If a player has zero engine power he is lost in space and out of further travelling
            ship.setTravelDays(null);
        }
    }

    public void process2(Player player, List <Integer> engineChosenList) {
        Ship ship = player.getShip();

        ship.setTravelDays(ship.getTravelDays() + ship.getEnginePower());
    }
}
