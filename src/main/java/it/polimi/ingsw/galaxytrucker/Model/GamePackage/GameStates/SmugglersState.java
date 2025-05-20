package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.SmugglersCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.*;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.BatteryTile;

import java.io.Serializable;
import java.util.*;

public class SmugglersState extends TravellingState implements Serializable {

    private Map<Player, Integer> cargoToLose;
    private Map<Player, Integer> batteriesToLose;
    protected SmugglersCard currentCard;
    private Player smugglersSlayer;
    private Boolean slayerCommits;
    private boolean reckoningPhase;
    private List<Integer> availableResources;
    private List<Player> cargoless;


    public SmugglersState(Game game, SmugglersCard card) {
        super(game, card);
        currentCard = card;

    }

    @Override
    protected void nextPlayer() {
        super.nextPlayer();
        if(currentPlayer == null){
            reckoning();
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
        cargoless = new ArrayList<>();
    }

    public void handleEvent(ActivateCannonsEvent event){
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
                reckoning();
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
                    reckoning();
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
        Player p = event.player();
        if(reckoningPhase && cargoToLose.keySet().contains(p)){
            Optional<Integer> mvcargo = p.getShip().getListOfCargo().stream()
                            .flatMap(c -> c.getTileContent().stream())
                            .max((a,b) -> a-b);
            if(mvcargo.isPresent() && mvcargo.get() > event.resource()){
                throw new IllegalEventException("you have to remove your most valuable cargo");
            }
            EventHandler.handleEvent(event);
            cargoToLose.put(p, cargoToLose.get(p) - 1);
            if(cargoToLose.get(p) == 0){
                cargoToLose.remove(p);
                checkNext();
            }
        }
        else if(!event.player().equals(smugglersSlayer) && slayerCommits){
            throw new IllegalEventException("you have not landed");
        }
        else {
            synchronized (availableResources) {
                EventHandler.handleEvent(event);
                availableResources.add(event.resource());
            }
        }
    }

    public void handleEvent(RemoveBatteriesEvent event){
        Player p = event.player();
        if(!reckoningPhase || !cargoToLose.keySet().contains(p)){
            throw new IllegalEventException("you don't have to remove batteries");
        }
        else if(event.batteries().get(2) > cargoToLose.get(p)){
            throw new IllegalEventException("you don't need to remove all these batteries");
        }
        else {
            EventHandler.handleEvent(event);
            cargoToLose.put(p, cargoToLose.get(p) - event.batteries().get(2));
            if(cargoToLose.get(p) == 0){
                cargoToLose.remove(p);
                checkNext();
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
            slayerCommits = false;
            checkNext();
        }
    }

    private void reckoning(){
        reckoningPhase = true;
        for(Player p : cargoToLose.keySet()){
            long available = p.getShip().getListOfCargo().stream()
                    .flatMap(c -> c.getTileContent().stream())
                    .count();
            if(available <= currentCard.getLostBlocksNumber()){
                p.getShip().removeAllCargo();
                cargoToLose.put(p, (int) (cargoToLose.get(p) - available));
                cargoless.add(p);
            }
            if(cargoToLose.get(p) == 0){
                cargoToLose.remove(p);
            }
            else{
                available = p.getShip().getListOfBattery().stream()
                        .mapToInt(BatteryTile::getSlotsFilled)
                        .sum();
                if(available <= cargoToLose.get(p)) {
                    p.getShip().removeAllBatteries();
                    cargoToLose.remove(p);
                }

            }
        }

    }

}
