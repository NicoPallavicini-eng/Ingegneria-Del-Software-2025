package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.StationCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.*;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



/*
Following turns each player has to decide whether they want to claim the reward
once a player lands they start a phase analogous to planets' cargoLoadingPhase
 */
/**
 * This class represents the state of the game when a player lands on a station.
 * It extends the TravellingState and handles events related to claiming rewards and managing cargo.
 * Following turns each player has to decide whether they want to claim the reward
 * once a player lands they start a phase analogous to planets' cargoLoadingPhase
 */
public class StationState extends TravellingState implements Serializable {

    private Player rewardClaimer;
    protected StationCard currentCard;
    private List<Integer> availableResources;


    /**
     * Constructor for StationState.
     * @param game
     * @param card
     */
    public StationState(Game game, StationCard card) {
        super(game, card);
        currentCard = card;
    }

    /**
     * Initializes the StationState by setting the available resources from the current card
     * and notifying observers about the station state.
     */
    public void init(){
        super.init();
        availableResources = currentCard.getBlockList();
        game.notifyObservers(game, "station");
    }

    /**
     * Handles the event when a player claims a reward at the station.
     * Validates the player's turn and checks if they have enough crew to claim the reward.
     * If valid, moves the player's ship backward and adds the blocks from the current card to their ship.
     * @param event The ClaimRewardEvent containing the player who is claiming the reward.
     */
    public void handleEvent(ClaimRewardEvent event) {
        if(!event.player().equals(currentPlayer)){
            throw new IllegalEventException("It is not your turn");
        }
        else if(event.player().getShip().getNumberOfInhabitants() < currentCard.getCrewNumberNeeded()){
            throw new IllegalEventException("not enough crew");
        }
        else{
            EventHandler.moveBackward(event.player().getShip(), currentCard.getDaysToLose(), game);
            rewardClaimer = event.player();
            currentPlayer = null;
            game.notifyObservers(game, "stationAction");
            event.player().getShip().addBlocks(new ArrayList<>(currentCard.getBlockList()));
        }
    }

    /**
     * Handles the event when a player decides not to claim the reward at the station.
     * Validates the player's turn and moves to the next player or ends the station state if no players are left.
     * @param event The NoChoiceEvent containing the player who is skipping the reward.
     */
    public void handleEvent(NoChoiceEvent event) {
        if(!event.player().equals(currentPlayer)){
            throw new IllegalEventException("It is not your turn");
        }
        else{
            nextPlayer();
            if(currentPlayer == null){
                next();
            }
        }
    }

    /**
     * Handles the event when a player adds cargo to their ship at the station.
     * Validates that the player is the reward claimer and that the resource is available.
     * If valid, processes the event and removes the resource from the available resources.
     * @param event The AddCargoEvent containing the player and resource to be added.
     */
    public void handleEvent(AddCargoEvent event){
        if(!event.player().equals(rewardClaimer)){
            throw new IllegalEventException("you have not landed on station");
        }
        else {
            synchronized (availableResources) {
                if (!availableResources.contains(event.resource())) {
                    throw new IllegalEventException("the block you are trying to add is not present");
                } else {
                    EventHandler.handleEvent(event);
                    availableResources.remove(event.resource());
                }
            }
        }
    }

    /**
     * Handles the event when a player removes cargo from their ship at the station.
     * Validates that the player is the reward claimer and processes the event.
     * If valid, adds the removed resource back to the available resources.
     * @param event The RemoveCargoEvent containing the player and resource to be removed.
     */
    public void handleEvent(RemoveCargoEvent event){
        if(!event.player().equals(rewardClaimer)){
            throw new IllegalEventException("you have not landed on station");
        }
        else {
            EventHandler.handleEvent(event);
            synchronized (availableResources) {
                availableResources.add(event.resource());
            }
        }
    }

/**
     * Handles the event when a player switches cargo at the station.
     * Validates that the player is the reward claimer and processes the event.
     * @param event The SwitchCargoEvent containing the player and resources to be switched.
     */
    public void handleEvent(SwitchCargoEvent event){
        if(!event.player().equals(rewardClaimer)){
            throw new IllegalEventException("you have not landed on station");
        }
        else {
            EventHandler.handleEvent(event);
        }
    }

    /**
     * Handles the event when a player completes their turn at the station.
     * Validates that the player is the reward claimer and resets their ship's cargo from cards.
     * If valid, moves to the next state.
     * @param event The DoneEvent containing the player who has completed their turn.
     */
    public void handleEvent(DoneEvent event){
        if(!event.player().equals(rewardClaimer)){
            throw new IllegalEventException("you have not landed on station");
        }
        else {
            event.player().getShip().resetCargoFromCards();
            next();
        }
    }

    /**
     * Handles the event when a player disconnects during the station state.
     * If the disconnected player is the reward claimer, it moves to the next state.
     * @param p The player who has disconnected.
     */
    @Override
    protected void disconnectionConsequences(Player p) {
        super.disconnectionConsequences(p);
        if(p.equals(rewardClaimer)){
            next();
        }
    }

    public Player getRewardClaimer() {
        return rewardClaimer;
    }

    public List<Integer> getAvailableResources() {
        return availableResources;
    }
}
