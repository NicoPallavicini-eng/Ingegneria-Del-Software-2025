package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.ShipCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.EjectPeopleEvent;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.EventHandler;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.NoChoiceEvent;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;

import java.io.Serializable;

public class ShipState extends TravellingState implements Serializable {

    private ShipCard currentCard;

    public ShipState(Game game, ShipCard card) {
        super(game, card);
    }

    public void init(){
        super.init();
    }

    public void handleEvent(EjectPeopleEvent event){
        if(!event.player().equals(currentPlayer)){
            throw new IllegalEventException("It is not your turn");
        }
        else{
            Ship s = event.player().getShip();;
            EventHandler.handleEvent(event);
            EventHandler.moveBackward(s, currentCard.getDaysToLose(), game);
            s.setCredits(s.getCredits() + currentCard.getCredits());
            next();
        }
    }

    public void handleEvent(NoChoiceEvent event){
        if(!event.player().equals(currentPlayer)){
            throw new IllegalEventException("It is not your turn");
        }
        else{
            nextPlayer();
        }
    }

    protected void nextPlayer(){
        super.nextPlayer();
        if(currentPlayer == null){
            next();
        }
    }

}
