package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.SmugglersCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.*;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SmugglersState extends TravellingState implements Serializable {

    private Map<Player, Integer> cargoToLose;
    protected SmugglersCard currentCard;
    private Player smugglersSlayer;
    private Boolean slayerCommits;
    private boolean reckoningPhase;
    private List<Integer> availableResources;


    public SmugglersState(Game game, SmugglersCard card) {
        super(game, card);
    }

    @Override
    protected void nextPlayer() {
        super.nextPlayer();
        if(currentPlayer == null){
            reckoningPhase = true;
            currentCard = null;
            slayerCommits = false;
            checkNext();
        }
    }

    public void init(){
        super.init();
        cargoToLose = new LinkedHashMap<>();
        reckoningPhase = false;
        availableResources = currentCard.getBlocksList();
    }

    public void handleInput(ActivateCannonsEvent event){
        if(!event.player().equals(currentPlayer)){
            throw new IllegalEventException("It is not your turn");
        }
        else{
            EventHandler.handleEvent(event);
            if(currentPlayer.getShip().getFirepower() < currentCard.getFirepower()){
                cargoToLose.put(currentPlayer, currentCard.getLostBlocksNumber());
                nextPlayer();
            }
            else if(currentPlayer.getShip().getFirepower() == currentCard.getFirepower()){
                nextPlayer();
            }
            else{
                smugglersSlayer = currentPlayer;
                reckoningPhase = true;
            }
        }
    }

    public void handleEvent(ClaimRewardEvent event){
        if(!event.player().equals(smugglersSlayer)){
            throw new IllegalEventException("You have not slain the smugglers");
        }
        else if (handledPlayers.contains(smugglersSlayer)) {
            throw new IllegalEventException("You have already decided whether to collect your reward");
        }
        else{
            EventHandler.moveBackward(smugglersSlayer.getShip(), currentCard.getDaysToLose(), game);
            slayerCommits = true;
        }
    }

    public void handleEvent(NoChoiceEvent event){
        if(reckoningPhase){
            if(!event.player().equals(smugglersSlayer)){
                throw new IllegalEventException("You have not slain the smugglers");
            }
            if(slayerCommits != null){
                throw new IllegalEventException("You have already made a choice");
            }
            else{
                slayerCommits = false;
                checkNext();
            }
        }
        else{
            if(!event.player().equals(currentPlayer)){
                throw new IllegalEventException("It is not your turn");
            }
            else{
                if(currentPlayer.getShip().getFirepower() < currentCard.getFirepower()){
                    cargoToLose.put(currentPlayer, currentCard.getLostBlocksNumber());
                    nextPlayer();
                }
                else if(currentPlayer.getShip().getFirepower() == currentCard.getFirepower()){
                    nextPlayer();
                }
                else{
                    smugglersSlayer = currentPlayer;
                    reckoningPhase = true;
                }
            }
        }

    }

    private void checkNext(){
        if(reckoningPhase && cargoToLose.isEmpty() && !slayerCommits){
            next();
        }
    }

    public void handleEvent(AddCargoEvent event){
        if(!event.player().equals(smugglersSlayer) && slayerCommits){
            throw new IllegalEventException("you have not right over these cargos");
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
        if(!event.player().equals(smugglersSlayer) && slayerCommits){
            throw new IllegalEventException("you have not landed");
        }
        else {
            synchronized (availableResources) {
                EventHandler.handleEvent(event);
                availableResources.add(event.resource());
            }
        }
    }

    public void handleEvent(SwitchCargoEvent event){
        if(!event.player().equals(smugglersSlayer) && slayerCommits){
            throw new IllegalEventException("you have not landed");
        }
        else {
            EventHandler.handleEvent(event);
        }
    }

    public void handleEvent(DoneEvent event){
        if(!event.player().equals(smugglersSlayer) && slayerCommits){
            throw new IllegalEventException("you have not landed");
        }
        else {
            next();
        }
    }

}
