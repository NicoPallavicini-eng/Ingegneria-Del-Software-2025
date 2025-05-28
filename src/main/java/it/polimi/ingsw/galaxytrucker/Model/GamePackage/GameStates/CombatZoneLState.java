package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CombatZoneCardL;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.EjectPeopleEvent;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.EventHandler;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;

public class CombatZoneLState extends CombatZoneState implements Serializable {
    protected CombatZoneCardL currentCard;

    public CombatZoneLState(Game game, CombatZoneCardL card) {
        super(game, card);
        currentCard = card;
    }

    public void init(){
        super.init();
        currentChallenge = CombatZoneChallenge.PEOPLE;
        currentPenalty = CombatZonePenalty.DAYS;
        peoplePenalty();
    }

    protected void peoplePenalty(){
        super.peoplePenalty();
        EventHandler.moveBackward(currentLoser.getShip(), currentCard.getDaysLost(), game);
        nextChallenge();
    }

    protected void enginesPenalty(){
        super.enginesPenalty();
        Player p = currentLoser;
        if(p.getShip().getNumberOfInhabitants() <= currentCard.getCrewLost()){
            p.getShip().ejectAll();
            nextChallenge();
        }

    }

    public void handleEvent(EjectPeopleEvent event) {
        if (!currentChallenge.equals(CombatZonePenalty.PEOPLE)) {
            throw new IllegalEventException("not people ejecting phase");
        }
        if (!currentLoser.equals(event.player())) {
            throw new IllegalEventException("You don't have to give up your crew");
        } else {
            int ejected = event.people().stream().mapToInt(l -> l.get(2)).sum();
            if (ejected != currentCard.getCrewLost()) {
                throw new IllegalEventException("You have to give up " + currentCard.getCrewLost() + " crew members, not " + ejected);
            } else {
                EventHandler.handleEvent(event);
                currentChallenge = CombatZoneChallenge.CANNONS;
                currentPenalty = CombatZonePenalty.CANNONBALLS;
                currentLoser = null;
                super.init();
            }
        }
    }

    protected void cannonsPenalty() {
        super.cannonsPenalty();
        cannonballStorm();
    }

    protected void nextChallenge(){
        super.init();
        currentLoser = null;
        switch(currentChallenge){
            case PEOPLE -> {
                currentChallenge = CombatZoneChallenge.ENGINES;
                currentPenalty = CombatZonePenalty.PEOPLE;
            }
            case ENGINES -> {
                currentChallenge = CombatZoneChallenge.CANNONS;
                currentPenalty = CombatZonePenalty.CANNONBALLS;
            }
            case CANNONS -> {next();}
        }
    }

}
