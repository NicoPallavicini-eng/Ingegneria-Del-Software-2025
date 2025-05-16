package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CombatZoneCard;
import it.polimi.ingsw.galaxytrucker.Model.Cards.CombatZoneCardL;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.*;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;

import java.io.Serializable;
import java.util.OptionalDouble;
import java.util.OptionalInt;

public class CombatZoneLState extends CombatZoneState implements Serializable {
    protected CombatZoneCardL currentCard;

    public CombatZoneLState(Game game, CombatZoneCard card) {
        super(game, card);
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
        currentLoser = null;
        currentChallenge = CombatZoneChallenge.ENGINES;
        currentPenalty = CombatZonePenalty.PEOPLE;
        super.init();
    }

    protected void enginesPenalty(){
        super.enginesPenalty();
        Player p = currentLoser;
        if(p.getShip().getNumberOfInhabitants() <= currentCard.getCrewLost()){
            p.getShip().ejectAll();
            currentChallenge = CombatZoneChallenge.CANNONS;
            currentPenalty = CombatZonePenalty.CANNONBALLS;
            currentLoser = null;
            super.init();
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
    }

}
