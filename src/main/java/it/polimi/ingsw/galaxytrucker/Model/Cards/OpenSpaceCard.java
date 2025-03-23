package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.OpenSpaceCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Player;
import it.polimi.ingsw.galaxytrucker.Model.Ship;

import java.util.List;

public class OpenSpaceCard extends Card {
    public OpenSpaceCard(boolean levelTwo, boolean used) {
        super(levelTwo, used);
    }

    public void acceptCardVisitor(OpenSpaceCardVisitor visitor) {
        visitor.handleOpenSpaceCard(this);
    }

    @Override
    public void process() {
        List <Player> players = getListOfPlayers();

        for (Player player : players) {
            Ship ship = player.getShip();
            // TODO player input (sets number of engines on)
            if (ship.getEnginePower() == 0) {
                ship.setTravelDays(NULL);
            }
        }

        List <Player> reversed = players.reversed();

        for (Player player : reversed) {
            Ship ship = player.getShip();
            ship.setTravelDays(ship.getEnginePower());
        }
    }
}
