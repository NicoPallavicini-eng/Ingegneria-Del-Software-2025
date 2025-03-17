package it.polimi.ingsw.galaxytrucker.Model.Cards;
import it.polimi.ingsw.galaxytrucker.Model.Player;
import it.polimi.ingsw.galaxytrucker.Model.Ship;
import java.util.List;

public class OpenSpaceCard extends Card {
    public OpenSpaceCard(boolean levelTwo, boolean used) {
        super(levelTwo, used);
        this.category = CardCategory.OPEN_SPACE;
    }

    @Override
    public void process() {
        super.process();

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
