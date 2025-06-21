package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.OpenSpaceCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.ActivateEnginesEvent;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.EventHandler;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.NoChoiceEvent;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;

import java.io.Serializable;
import java.util.ArrayList;

/*Each player in order has to choose the engines to activate.
then the rockets are moved foward according to the engine power of their ship
 */

public class OpenSpaceState extends TravellingState implements Serializable {
    private OpenSpaceCard currentCard;
    private ArrayList<Player> handledPlayers;

    public OpenSpaceState(Game game, OpenSpaceCard card) {
        super(game, card);
        currentCard = card;
    }

    public void init(){
        super.init();
        handledPlayers = new ArrayList<>();
        game.notifyObservers(game, "openSpace");
    }

    public void handleEvent(ActivateEnginesEvent event)throws IllegalEventException {
        Ship ship = event.player().getShip();
        if (!event.player().equals(currentPlayer)) {
            throw new IllegalEventException("It is not your turn");
        } else {
            EventHandler.handleEvent(event);
            if(ship.getEnginePower() == 0){
                ship.setTravelDays(null);
            }
            else {
                EventHandler.moveForward(ship, ship.getEnginePower(), game);
                handledPlayers.add(event.player());
                game.notifyObservers(game, "openSpaceAction");
                nextPlayer();
                if (currentPlayer == null) {
                    next();
                }
            }
        }
    }

    public void handleEvent(NoChoiceEvent event)throws IllegalEventException {
        Ship ship = event.player().getShip();
        if (!event.player().equals(currentPlayer)) {
            throw new IllegalEventException("It is not your turn");
        }
        else if(ship.getEnginePower() == 0){
            ship.setTravelDays(null);
            handledPlayers.add(event.player());
            game.notifyObservers(game, "openSpaceAction");
            nextPlayer();
            if (currentPlayer == null) {
                next();
            }
        }
        else {
            EventHandler.moveForward(ship, ship.getEnginePower(), game);
            handledPlayers.add(event.player());
            game.notifyObservers(game, "openSpaceAction");
            nextPlayer();
            if (currentPlayer == null) {
                next();
            }
        }
    }

    @Override
    protected void disconnectionConsequences(Player p) {
        super.disconnectionConsequences(p);
        if (currentPlayer == null) {
            next();
        }
    }
}
