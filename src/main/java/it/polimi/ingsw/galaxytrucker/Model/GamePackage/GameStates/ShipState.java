package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;


import it.polimi.ingsw.galaxytrucker.Model.Cards.ShipCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.EjectPeopleEvent;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.EventHandler;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.NoChoiceEvent;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;

import java.io.Serializable;
import java.util.List;

/**
 * each player in order has to choose whether to eject people if they have enough or noaction
 */
public class ShipState extends TravellingState implements Serializable {

    private ShipCard currentCard;

    /**
     * @param game
     * @param card
     */
    public ShipState(Game game, ShipCard card) {
        super(game, card);
        currentCard = card;

    }

    /**
     * This function initialize ShipState
     */
    public void init(){
        super.init();
        game.notifyObservers(game, "ship");
    }

    /**
     * EjectPeopleEvent is possibile during Ship State
     * @param event
     */
    public void handleEvent(EjectPeopleEvent event){
        if(!event.player().equals(currentPlayer)){
            throw new IllegalEventException("It is not your turn");
        }
        else{
            int numberEject = 0;
            for(List<Integer> list : event.people()){
                numberEject += list.get(2);
            }
            if(numberEject!=currentCard.getCrewNumberLost()){
                throw new IllegalEventException("You selected " + numberEject + " instead of " + currentCard.getCrewNumberLost() + " people");
            }

            Ship s = event.player().getShip();
            EventHandler.handleEvent(event);
            EventHandler.moveBackward(s, currentCard.getDaysToLose(), game);
            s.setCredits(s.getCredits() + currentCard.getCredits());
            next();
        }
    }
    /**
     * NoChoiceEvent is possibile during Ship State
     * @param event
     */
    public void handleEvent(NoChoiceEvent event){
        if(!event.player().equals(currentPlayer)){
            throw new IllegalEventException("It is not your turn");
        }
        else{
            nextPlayer();
        }
    }

    /**
     * This function manages the turn of Players
     */
    protected void nextPlayer(){
        super.nextPlayer();
        if(currentPlayer == null){
            next();
        }
    }

    /**
     * This function handles Player,that disconnected from Game
     * @param p Player
     */
    @Override
    protected void disconnectionConsequences(Player p) {
        super.disconnectionConsequences(p);
    }
}
