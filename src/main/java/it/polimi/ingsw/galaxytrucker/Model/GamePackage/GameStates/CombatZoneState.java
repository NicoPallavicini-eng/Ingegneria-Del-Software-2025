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
 * This abstract class represents the state of the game during the combat zone phase.
 * It handles the challenges and penalties that players face in this phase.
 */
public abstract class CombatZoneState extends TravellingState implements Serializable {

    protected Player currentLoser;
    protected CombatZoneChallenge currentChallenge;
    protected CombatZonePenalty currentPenalty;
    protected CombatZoneCard currentCard;
    protected Cannonball currentCannonball;
    protected boolean loserIsIllegal;


    /**
     * Constructor for CombatZoneState.
     * Initializes the game state with the given game and CombatZoneCard.
     *
     * @param game The current game instance.
     * @param card The CombatZoneCard associated with this state.
     */
    public CombatZoneState(Game game, CombatZoneCard card) {
        super(game, card);
        currentCard = card;
        loserIsIllegal = false;
    }


    /**
     * This function handle engines activation event.
     * It checks if the current challenge is CANNONS, if the player is the current player,
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
     * This function handle cannons activation event.
     * It checks if the current challenge is ENGINES, if the player is the current player,
     * @param event
     * @throws IllegalEventException
     */
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

    /**
     * This function handles the event of a player giving up during the combat zone phase.
     * It checks if the current challenge is PEOPLE, if the player is the current loser,
     * and if the number of ejected crew members matches the required amount.
     *
     * @param event The EjectPeopleEvent containing the player who is giving up.
     * @throws IllegalEventException If the event is not valid.
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
     * This function computes the penalty for the current challenge based on the player's ship attributes.
     * It determines the player with the minimum number of inhabitants.
     * It sorts the list of active players and sets the current loser to the player with the minimum inhabitants.
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
     * This function computes the penalty for the current challenge based on the player's ship attributes.
     * It determines the player with the minimum engine power.
     * It sorts the list of active players and sets the current loser to the player with the minimum engine power.
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
     * This function computes the penalty for the current challenge based on the player's ship attributes.
     * It determines the player with the minimum firepower.
     * It sorts the list of active players and sets the current loser to the player with the minimum firepower.
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
     * This function handles the event of a player activating their shield during the combat zone phase.
     * It checks if the current penalty is CANNONBALLS, if the player is the current loser,
     * and if the selected shield can defend against the current cannonball.
     *
     * @param event The ActivateShieldEvent containing the player and shield details.
     * @throws IllegalEventException If the event is not valid.
     */
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

    /**
     * This function checks if the shield can defend against the cannonball based on its orientation and direction.
     *
     * @param shieldOrientation The orientation of the shield.
     * @param direction The direction of the cannonball.
     * @return true if the shield can defend against the cannonball, false otherwise.
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
     * This function handles the cannonball storm event.
     * It checks if the current loser has a functioning ship and if there are cannonballs left in the current card.
     * If so, it processes the cannonball and recursively calls itself until all cannonballs are processed.
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

    /**
     * This function initializes the CombatZoneState by notifying observers about the combat zone state
     * and the current challenge.
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

    protected void penalty(){
        switch(currentChallenge){
            case PEOPLE -> peoplePenalty();
            case CANNONS -> cannonsPenalty();
            case ENGINES -> enginesPenalty();
        }
    }

    protected void nextChallenge(){}


    /**
     * This function handles the event of a player choosing a sub-ship during the combat zone phase.
     * It checks if the player is the current loser and if the loser is illegal.
     * If valid, it processes the event and notifies observers about the legal ship.
     *
     * @param event The ChooseSubShipEvent containing the player and ship details.
     */
    public void handleEvent(ChooseSubShipEvent event) {
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
