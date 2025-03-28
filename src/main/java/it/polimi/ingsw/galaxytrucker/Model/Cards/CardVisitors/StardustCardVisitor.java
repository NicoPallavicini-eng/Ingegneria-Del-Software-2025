package it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors;
import it.polimi.ingsw.galaxytrucker.Model.Cards.*;
import it.polimi.ingsw.galaxytrucker.Model.Game.*;
import it.polimi.ingsw.galaxytrucker.Model.*;

public class StardustCardVisitor extends CardVisitor {

    public void visitStardustCard() {}

    public void handleStardustCard(StardustCard stardustCard, Player player) {
        Ship ship = player.getShip();
        ship.setTravelDays(ship.getTravelDays() - ship.getExposedConnectors());
    }
}
