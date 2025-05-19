package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CombatZoneCardNotL;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.EjectPeopleEvent;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.EventHandler;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;
import java.util.OptionalInt;

public class CombatZoneNotLState extends CombatZoneState implements Serializable {

    private CombatZoneCardNotL card;

    public CombatZoneNotLState(Game game, CombatZoneCardNotL card) {
        super(game, card);
        currentCard = card;
    }

    public void init(){
        super.init();
        currentChallenge = CombatZoneChallenge.CANNONS;
        currentPenalty = CombatZonePenalty.DAYS;
    }

    protected void cannonsPenalty(){
        super.cannonsPenalty();
        EventHandler.moveBackward(currentLoser.getShip(), currentCard.getDaysLost(), game);
        currentLoser = null;
        currentChallenge = CombatZoneChallenge.ENGINES;
        currentPenalty = CombatZonePenalty.CARGO;
    }

    protected void enginesPenalty(){
        super.enginesPenalty();
        //if total cargo << remove all
    }


}


