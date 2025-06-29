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
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;

/**
 * This Class handles the 2 subclasses CombatZoneLState and CombatZoneNotLState.
 * it uses the attributes current penalty and current challenge for every phase of the card,
 * when the challenge is completed a currentLoser is set and they have to endure the penalty
 */
//todo view si deve vedere challenge corrente e penalty corrente atrimenti non si capisce
public abstract class CombatZoneState extends TravellingState implements Serializable {

    protected Player currentLoser;
    protected CombatZoneChallenge currentChallenge;
    protected CombatZonePenalty currentPenalty;
    protected CombatZoneCard currentCard;
    protected Cannonball currentCannonball;
    protected boolean loserIsIllegal;


    /**
     * @param game
     * @param card
     */
    public CombatZoneState(Game game, CombatZoneCard card) {
        super(game, card);
        currentCard = card;
        loserIsIllegal = false;
    }


    /**
     * ActivateEnginesEvent is possible during CombatZoneState
     * @param event
     * @throws IllegalEventException
     */
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
    /**
     * ActivateCannonsEvent is possible during CombatZoneState
     * @param event
     * @throws IllegalEventException
     */
    public void handleEvent(ActivateCannonsEvent event) throws IllegalEventException{
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
    /**
     * NoChoiceEvent is possible during CombatZoneState
     * @param event
     * @throws IllegalEventException
     */
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
    /**
     * This function manages people penalty
     */
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
    /**
     * This function manages engines penalty
     */
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
    /**
     * This function manages cannons penalty
     */
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

    /**
     * ActivateShieldEvent is possible during CombatZoneState
     * @param event
     * @throws IllegalEventException
     */

    public void handleEvent(ActivateShieldEvent event) throws IllegalEventException {
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

    /**
     * This function checks whether shield defend or not
     * @param shieldOrientation
     * @param direction
     * @return
     */
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

    /**
     * This function shoot Cannonball to current Looser
     */
    public void cannonballStorm(){
        if(currentLoser.getShip().isShipBroken()){
            loserIsIllegal = true;
            return;
        }
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

    /**
     * This function returns a current looser
     * @return Player
     */
    public Player getCurrentLoser() {
        return currentLoser;
    }

    /**
     * This function returns Current Challenge
     * @return CombatZoneChallenge
     */
    public CombatZoneChallenge getCurrentChallenge() {
        return currentChallenge;
    }

    /**
     * This function return a actual Combat Zone Penality
     * @return CombatZonePenalty
     */
    public CombatZonePenalty getCurrentPenalty() {
        return currentPenalty;
    }

    /**
     * This function return a current Card
     * @return CombatZoneCard
     */
    @Override
    public CombatZoneCard getCurrentCard() {
        return currentCard;
    }

    /**
     * This function return a current Cannonball
     * @return Cannonball
     */
    public Cannonball getCurrentCannonball() {
        return currentCannonball;
    }

    /**
     * This function manages disconnection of a Player during Combat Zone Card
     * @param p Player
     */
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

    /**
     * This function decides which method of penalty to call
     */
    protected void penalty(){
        switch(currentChallenge){
            case PEOPLE -> peoplePenalty();
            case CANNONS -> cannonsPenalty();
            case ENGINES -> enginesPenalty();
        }
    }

    /**
     * This function change a challenge
     */
    protected void nextChallenge(){}


    /**
     * ChooseSubShipEvent is possible during CombatZoneState
     * @param event
     * @throws IllegalEventException
     */
    public void handleEvent(ChooseSubShipEvent event) throws IllegalEventException {
        if(!event.player().equals(currentLoser) || !loserIsIllegal){
            throw new IllegalEventException("You have already a functioning spaceship");
        }
        else{
            EventHandler.handleEvent(event);
            loserIsIllegal =  false;
            game.notifyObservers(game, "legalship");
            cannonballStorm();
        }
    }

}
