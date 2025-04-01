package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.MeteorsCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Game.Game;
import it.polimi.ingsw.galaxytrucker.Model.Game.GameState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;

import java.util.List;

public class MeteorsCard extends Card {
    private final List <Meteor> meteors;

    public MeteorsCard(boolean levelTwo, boolean used, List <Meteor> meteors, MeteorsCardVisitor visitor) {
        super(levelTwo, used, visitor);
        this.meteors = meteors;
    }

    public List <Meteor> getMeteorsList() {
        return meteors;
    }

    public void acceptCardVisitorParallel(MeteorsCardVisitor visitor, List <Ship> ships) {
        for (Ship ship : ships) {
            visitor.handleMeteorsCard(this, ship);
        }
    }

    public void acceptNextVisitor(GameState state, MeteorsCardVisitor visitor, Game game) {
        visitor.setNextStateMeteorsCard(state, game, this);
    }

    public void process(Ship ship) {
        for (Meteor meteor : meteors) {
            meteor.getHit(ship);
        }
    }
}
