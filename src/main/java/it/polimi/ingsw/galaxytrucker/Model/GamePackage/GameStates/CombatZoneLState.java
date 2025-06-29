package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CombatZoneCardL;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.EjectPeopleEvent;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.EventHandler;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;

/**
 * This class represent one of the two types of CombatZone Card
 */
public class CombatZoneLState extends CombatZoneState implements Serializable {
    protected CombatZoneCardL currentCard;

    /**
     * @param game
     * @param card
     */
    public CombatZoneLState(Game game, CombatZoneCardL card) {
        super(game, card);
        currentCard = card;
    }

    /**
     * This function initialize CombatZoneLState
     */
    public void init(){
        super.init();
        game.notifyObservers(game, "combatZoneL");
        game.notifyObservers(game, "combatPeopleChallenge");
        currentChallenge = CombatZoneChallenge.PEOPLE;
        currentPenalty = CombatZonePenalty.DAYS;
        peoplePenalty();
    }

    /**
     * This function manages people penalty
     */
    protected void peoplePenalty(){
        super.peoplePenalty();
        game.notifyObservers(game, "peoplePenalty");
        EventHandler.moveBackward(currentLoser.getShip(), currentCard.getDaysLost(), game);
        nextChallenge();
    }

    /**
     * This function manages engines penalty
     */
    protected void enginesPenalty(){
        super.enginesPenalty();
        game.notifyObservers(game, "enginePenalty");
        Player p = currentLoser;
        if(p.getShip().getNumberOfInhabitants() <= currentCard.getCrewLost()){
            p.getShip().ejectAll();
            nextChallenge();
        }

    }

    /**
     * EjectPeopleEvent is possible during CombatZoneLState
     * @param event
     */
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

    /**
     * This function manages cannons penalty
     */
    protected void cannonsPenalty() {
        super.cannonsPenalty();
        cannonballStorm();
    }

    /**
     * this function change a challenge of this State
     */
    protected void nextChallenge(){
        super.init();
        currentLoser = null;
        switch(currentChallenge){
            case PEOPLE -> {
                game.notifyObservers(game, "combatEngineChallenge");
                currentChallenge = CombatZoneChallenge.ENGINES;
                currentPenalty = CombatZonePenalty.PEOPLE;

            }
            case ENGINES -> {
                game.notifyObservers(game, "combatCannonChallenge");
                currentChallenge = CombatZoneChallenge.CANNONS;
                currentPenalty = CombatZonePenalty.CANNONBALLS;
            }
            case CANNONS -> {next();}
        }
    }
}
