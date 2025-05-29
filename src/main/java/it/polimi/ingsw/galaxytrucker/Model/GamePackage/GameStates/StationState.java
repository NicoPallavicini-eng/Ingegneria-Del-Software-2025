package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.StationCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.*;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;
import java.util.List;

/*Following turns each player has to decide whether they want to claim the reward
once a player lands they start a phase analogous to planets' cargoLoadingPhase
 */

public class StationState extends TravellingState implements Serializable {

    private Player rewardClaimer;
    protected StationCard currentCard;
    private List<Integer> availableResources;


    public StationState(Game game, StationCard card) {
        super(game, card);
        currentCard = card;

    }

    public void init(){
        super.init();
        availableResources = currentCard.getBlockList();
    }


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
        }
    }

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

    public void handleEvent(SwitchCargoEvent event){
        if(!event.player().equals(rewardClaimer)){
            throw new IllegalEventException("you have not landed on station");
        }
        else {
            EventHandler.handleEvent(event);
        }
    }

    public void handleEvent(DoneEvent event){
        if(!event.player().equals(rewardClaimer)){
            throw new IllegalEventException("you have not landed on station");
        }
        else {
            next();
        }
    }

    @Override
    protected void disconnectionConsequences(Player p) {
        if(rewardClaimer == null){
            super.disconnectionConsequences(p);
        }
        else if(p.equals(rewardClaimer)){
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
