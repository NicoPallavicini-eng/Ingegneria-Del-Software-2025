package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CombatZoneCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.*;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;

import java.io.Serializable;
import java.util.OptionalDouble;
import java.util.OptionalInt;

public abstract class CombatZoneState extends TravellingState implements Serializable {

    public CombatZoneState(Game game, CombatZoneCard card) {
        super(game, card);
    }
        protected Player currentLoser;
        protected CombatZoneChallenge currentChallenge;
        protected CombatZonePenalty currentPenalty;
        protected CombatZoneCard currentCard;


    public void handleEvent(ActivateEnginesEvent event)throws IllegalEventException {
        if(currentChallenge.equals(CombatZoneChallenge.CANNONS)){
            throw new IllegalEventException("not engine time");
        }
        if (event.player().equals(currentPlayer)) {
            throw new IllegalEventException("It is not your turn");
        } else {
            EventHandler.handleEvent(event);
            nextPlayer();
            if (currentPlayer == null) {
                enginesPenalty();
            }
        }
    }

    public void handleEvent(ActivateCannonsEvent event){
        if(currentChallenge.equals(CombatZoneChallenge.CANNONS)){
            throw new IllegalEventException("not cannon time");
        }
        if(!event.player().equals(currentPlayer)){
            throw new IllegalEventException("It is not your turn");
        }
        else{
            EventHandler.handleEvent(event);
            nextPlayer();
            if (currentPlayer == null) {
                cannonsPenalty();
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
                    cannonsPenalty();
                }
            }
        }
        else {
            throw new IllegalEventException("nobody asked");
        }
    }

    protected void peoplePenalty() {
        OptionalInt min = game.getListOfActivePlayers().stream()
                .mapToInt(p -> p.getShip().getNumberOfInhabitants())
                .min();
        game.sortListOfActivePlayers();
        for (Player p : game.getListOfActivePlayers()) {
            if (p.getShip().getNumberOfInhabitants() == min.getAsInt()) {
                currentLoser = p;
                break;
            }
        }
    }

    protected void enginesPenalty() {
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
    }

    protected void cannonsPenalty() {
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
    }

}
