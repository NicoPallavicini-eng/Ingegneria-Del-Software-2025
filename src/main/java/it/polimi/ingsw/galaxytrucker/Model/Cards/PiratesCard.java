package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.PiratesCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Game.Game;
import it.polimi.ingsw.galaxytrucker.Model.Game.GameState;
import it.polimi.ingsw.galaxytrucker.Model.Game.SequentialTravellingState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;

import java.util.List;

public class PiratesCard extends Card {
    private final int firepower;
    private final int credits;
    private final int daysToLose;
    private final List <Cannonball> cannonballList;
    private boolean defeated = false;
    private boolean goNext;

    public PiratesCard(boolean levelTwo, boolean used, PiratesCardVisitor visitor, int firepower, int credits, int daysToLose, List <Cannonball> cannonballList) {
        super(levelTwo, used, visitor);
        this.firepower = firepower;
        this.credits = credits;
        this.daysToLose = daysToLose;
        this.cannonballList = cannonballList;
    }

    public int getFirepower() {
        return firepower;
    }

    public int getCredits() {
        return credits;
    }

    public int getDaysToLose() {
        return daysToLose;
    }

    public List <Cannonball> getCannonballList() {
        return cannonballList;
    }

    public void acceptCardVisitorSequential(SequentialTravellingState state, PiratesCardVisitor visitor, List <Player> players) {
        for (Player player : players) {
            visitor.handlePiratesCard(state,this, player);
            if (state.getAccomplished()) {
                break;
            }
        }
    }

    public void acceptNextVisitor(GameState state, PiratesCardVisitor visitor, Game game) {
        visitor.setNextStatePiratesCard(state, game, this);
    }

    public void setDefeated(boolean defeated) {
        this.defeated = defeated;
    }

    public boolean getDefeated() {
        return defeated;
    }

    public void setGoNext(boolean goNext) {
        this.goNext = goNext;
    }

    public boolean getGoNext() {
        return goNext;
    }

    public void process(Player player, SequentialTravellingState state) {
        Ship ship = player.getShip();

        if (ship.getFirepower() < firepower) {
            for (Cannonball cannonball : cannonballList) {
                cannonball.getHit(ship);
            }
        } else if (ship.getFirepower() > firepower) {
            state.setAccomplished(true);

            if (player.getEngages()) {
                ship.setCredits(ship.getCredits() + credits);
                ship.setTravelDays(ship.getTravelDays() - daysToLose);
            }
        }
    }
}
