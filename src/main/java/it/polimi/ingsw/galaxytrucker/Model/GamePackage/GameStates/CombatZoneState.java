package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Cannonball;
import it.polimi.ingsw.galaxytrucker.Model.Cards.CombatZoneCard;
import it.polimi.ingsw.galaxytrucker.Model.Direction;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.*;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.ShieldOrientation;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.TilesVisitor.ShieldTileVisitor;

import java.io.Serializable;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;

/*This Class handles the 2 subclasses CombatZoneLState and CombatZoneNotLState.
it uses the attributes current penalty and current challenge for every phase of the card,
when the challenge is completed a currentLoser is set and they have to endure the penalty
 */

public abstract class CombatZoneState extends TravellingState implements Serializable {

    protected Player currentLoser;
    protected CombatZoneChallenge currentChallenge;
    protected CombatZonePenalty currentPenalty;
    protected CombatZoneCard currentCard;
    protected Cannonball currentCannonball;


    public CombatZoneState(Game game, CombatZoneCard card) {
        super(game, card);
        currentCard = card;
    }



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

        if(currentChallenge.equals(CombatZoneChallenge.ENGINES) && currentLoser==null) {
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
        else if(currentChallenge.equals(CombatZoneChallenge.CANNONS) && currentLoser==null){
            if (!event.player().equals(currentPlayer)) {
                throw new IllegalEventException("It is not your turn");
            } else {
                nextPlayer();
                if (currentPlayer == null) {
                    cannonsPenalty();
                }
            }
        }
        else if(currentPenalty == CombatZonePenalty.CANNONBALLS && event.player().equals(currentLoser)){
            currentCannonball.getHit(event.player().getShip());
            cannonballStorm();
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

    public void handleEvent(ActivateShieldEvent event) {
        if (!currentPenalty.equals(CombatZonePenalty.CANNONBALLS)) {
            throw new IllegalEventException("Not time for activating shield");
        } else if (!currentLoser.equals(event.player())) {
            throw new IllegalEventException("you shall not defend");
        }
        Optional<Tile> tile = event.player().getShip().getTileOnFloorPlan(event.shieldRow(), event.shieldCol());
        ShieldTileVisitor stv = new ShieldTileVisitor();
        tile.ifPresent(t -> t.accept(stv));
        if (stv.getList().isEmpty() || !shieldDefends(stv.getList().getFirst().getOrientation(), currentCannonball.direction())) {
            throw new IllegalEventException("You didn't select a shield able to defend you");
        } else {
            EventHandler.handleEvent(event);
            cannonballStorm();
        }
    }

    private boolean shieldDefends(ShieldOrientation shieldOrientation, Direction direction) {
        switch (shieldOrientation) {
            case NORTHEAST -> {
                return direction == Direction.NORTH || direction == Direction.EAST;
            }
            case NORTHWEST -> {
                return direction == Direction.NORTH || direction == Direction.WEST;
            }
            case SOUTHEAST -> {
                return direction == Direction.SOUTH || direction == Direction.EAST;
            }
            case SOUTHWEST -> {
                return direction == Direction.SOUTH || direction == Direction.WEST;
            }
        }
        return false;
    }

    public void cannonballStorm(){
        if(currentCard.getCannonballList().isEmpty()){
            next();
        }
        else{
            currentCannonball = currentCard.getCannonballList().getFirst();
            currentCard.getCannonballList().removeFirst();
            if(currentCannonball.bigCannonball()){
                currentCannonball.getHit(currentLoser.getShip());
                cannonballStorm();
            }
        }
    }

    public Player getCurrentLoser() {
        return currentLoser;
    }

    public CombatZoneChallenge getCurrentChallenge() {
        return currentChallenge;
    }

    public CombatZonePenalty getCurrentPenalty() {
        return currentPenalty;
    }

    @Override
    public CombatZoneCard getCurrentCard() {
        return currentCard;
    }

    public Cannonball getCurrentCannonball() {
        return currentCannonball;
    }

    @Override
    protected void disconnectionConsequences(Player p) {
        super.disconnectionConsequences(p);
        if(currentPlayer == null){
            penalty();
        }
        if(currentLoser.equals(p)){
            nextChallenge();
        }
    }

    protected void penalty(){
        switch(currentChallenge){
            case PEOPLE -> peoplePenalty();
            case CANNONS -> cannonsPenalty();
            case ENGINES -> enginesPenalty();
        }
    }

    protected void nextChallenge(){}
}
