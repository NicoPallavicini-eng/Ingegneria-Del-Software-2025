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
        lessPeople();
    }

    private void lessPeople(){
        OptionalInt min = game.getListOfActivePlayers().stream()
                .mapToInt(p -> p.getShip().getNumberOfInhabitants())
                .min();
        game.sortListOfActivePlayers();
        for(Player p : game.getListOfActivePlayers()){
            if(p.getShip().getNumberOfInhabitants() == min.getAsInt()){
                currentLoser = p;
                break;
            }
        }
        EventHandler.moveBackward(currentLoser.getShip(), currentCard.getDaysLost(), game);
        currentLoser = null;
        currentChallenge = CombatZoneChallenge.ENGINES;
        currentPenalty = CombatZonePenalty.PEOPLE;
        super.init();
    }

    public void handleEvent(ActivateEnginesEvent event)throws IllegalEventException {
        Ship ship = event.player().getShip();
        if (event.player().equals(currentPlayer)) {
            throw new IllegalEventException("It is not your turn");
        } else {
            EventHandler.handleEvent(event);
            if(ship.getEnginePower() == 0){
                ship.setTravelDays(null);
            }
            else {
                EventHandler.moveForward(ship, ship.getEnginePower(), game);
                nextPlayer();
                if (currentPlayer == null) {
                    peoplePenalty();
                }
            }
        }
    }

    public void handleEvent(NoChoiceEvent event)throws IllegalEventException {
        Ship ship = event.player().getShip();

        if(currentChallenge.equals(CombatZoneChallenge.ENGINES)) {
            if (!event.player().equals(currentPlayer)) {
                throw new IllegalEventException("It is not your turn");
            } else if (ship.getEnginePower() == 0) {
                ship.setTravelDays(null);
            } else {
                EventHandler.moveForward(ship, ship.getEnginePower(), game);
                nextPlayer();
                if (currentPlayer == null) {
                    peoplePenalty();
                }
            }
        }
        else if(currentChallenge.equals(CombatZoneChallenge.CANNONS)){
            if (!event.player().equals(currentPlayer)) {
                throw new IllegalEventException("It is not your turn");
            } else {
                nextPlayer();
                if (currentPlayer == null) {
                    cannonballsPenalty();
                }
            }
        }
    }

    private void peoplePenalty(){
        OptionalInt min = game.getListOfActivePlayers().stream()
                .mapToInt(p -> p.getShip().getEnginePower())
                .min();
        game.sortListOfActivePlayers();
        for(Player p : game.getListOfActivePlayers()){
            if(p.getShip().getFirepower() == min.getAsInt()){
                currentLoser = p;
                break;
            }
        }
        Player p = currentLoser;
        if(p.getShip().getNumberOfInhabitants() <= currentCard.getCrewLost()){
            p.getShip().ejectAll();
            currentChallenge = CombatZoneChallenge.CANNONS;
            currentPenalty = CombatZonePenalty.CANNONBALLS;
            currentLoser = null;
        }

    }

    public void handleEvent(EjectPeopleEvent event) {
        if (!currentChallenge.equals(CombatZonePenalty.PEOPLE)) {
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
            }
        }
    }

    public void handleEvent(ActivateCannonsEvent event){
        if(!event.player().equals(currentPlayer)){
            throw new IllegalEventException("It is not your turn");
        }
        else{
            EventHandler.handleEvent(event);
            nextPlayer();
            if (currentPlayer == null) {
                cannonballsPenalty();
            }
        }
    }



    private void cannonballsPenalty() {
        OptionalDouble min = game.getListOfActivePlayers().stream()
                .mapToDouble(p -> p.getShip().getFirepower())
                .min();
        game.sortListOfActivePlayers();
        for (Player p : game.getListOfActivePlayers()) {
            if (p.getShip().getFirepower() == min.getAsDouble()) {
                currentLoser = p;
                break;
            }
        }
        next();
    }

}
