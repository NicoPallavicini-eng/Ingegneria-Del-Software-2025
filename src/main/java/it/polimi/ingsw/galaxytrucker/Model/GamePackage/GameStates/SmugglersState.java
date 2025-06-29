package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.SmugglersCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.*;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.BatteryTile;

import java.io.Serializable;
import java.util.*;

/** * This class represents the state of the game when smugglers are involved.
 * It extends the TravellingState and handles the smugglers' events and actions.
 *
 * Following turns all player decide whether to activate cannons or not
 * when the smugglers have been defeated and a smugglerSlayer has been crowned
 * or all players have lost the reckoning phase starts.
 *
 * in this phase the defeated players have to remove their most valuable cargo and then batteries
 * to match the smugglers' greed,
 * the smugglersSlayer has to decide whether they want to commit and claim the reward,
 * if they commit they enter a phase analogous to the planets' cargoLoadingPhase.
 *
 * Once all defeated players have removed enough cargo/batteries
 * and the slayer has signaled done/not committed next() is called.
 */

public class SmugglersState extends TravellingState implements Serializable {

    private Map<Player, Integer> cargoToLose;
    protected SmugglersCard currentCard;
    private Player smugglersSlayer;
    private Boolean slayerCommits;
    private boolean reckoningPhase;
    private List<Integer> availableResources;
    private List<Player> cargoless; //used for players that have <= cargo than asked from smugglers
    private List<Player> playersAfterSlayer;


    /**
     * Constructor for the SmugglersState.
     * Initializes the state with the given game and smugglers card.
     *
     * @param game The current game instance.
     * @param card The smugglers card associated with this state.
     */
    public SmugglersState(Game game, SmugglersCard card) {
        super(game, card);
        currentCard = card;

    }

    /**
     * nextPlayer method is overridden to handle the transition to the next player.
     */
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

    /**
     * Initializes the SmugglersState.
     * Sets up the necessary variables and notifies observers.
     */
    public void init(){
        super.init();
        cargoToLose = new LinkedHashMap<>();
        reckoningPhase = false;
        availableResources = currentCard.getBlocksList();
        cargoless = new ArrayList<>();
        game.notifyObservers(game, "smugglers");
        this.setHandledPlayers(new ArrayList<>());
    }

    /**
     * Handles the ActivateCannonsEvent.
     * Checks if it's the current player's turn and processes the event accordingly.
     *
     * @param event The ActivateCannonsEvent to handle.
     */
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
                game.notifyObservers(game, "smugglersDefeated");
                reckoning();
            }
        }
    }

    /**
     * Handles the ClaimRewardEvent.
     * Checks if the player is the smugglers slayer and processes the event accordingly.
     *
     * @param event The ClaimRewardEvent to handle.
     */
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
            event.player().getShip().addBlocks(new ArrayList<>(currentCard.getBlocksList()));
        }
    }

    /**
     * Handles the NoChoiceEvent.
     * Checks if the player is the smugglers slayer and processes the event accordingly.
     *
     * @param event The NoChoiceEvent to handle.
     */
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
                handledPlayers.add(smugglersSlayer);
                game.notifyObservers(game, "smugglersNoChoice");
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

    /** checkNext method is used to check if all players have handled their events
     * and if so, it transitions to the next state.
     * It is called after each player's action during the reckoning phase.
     */
    private void checkNext(){
        if(reckoningPhase && handledPlayers.containsAll(game.getListOfActivePlayers())){
            next();
        }
    }

    /**
     * Handles the AddCargoEvent.
     * Checks if the player is the smugglers slayer and processes the event accordingly.
     *
     * @param event The AddCargoEvent to handle.
     */
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

    /**
     * Handles the RemoveCargoEvent.
     * Checks if the player is in the reckoning phase and processes the event accordingly.
     *
     * @param event The RemoveCargoEvent to handle.
     */
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
                handledPlayers.add(p);
                checkNext();
            }
        }
        else if(!event.player().equals(smugglersSlayer) || !slayerCommits){
            throw new IllegalEventException("you have not landed");
        }
        else {
            synchronized (availableResources) {
                EventHandler.handleEvent(event);
                availableResources.add(event.resource());
            }
        }
    }

    /**
     * Handles the RemoveBatteriesEvent.
     * Checks if the player is in the reckoning phase and processes the event accordingly.
     *
     * @param event The RemoveBatteriesEvent to handle.
     */
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
                handledPlayers.add(p);
                checkNext();
            }
        }
    }

    /**
     * Handles the SwitchCargoEvent.
     * Checks if the player is the smugglers slayer and processes the event accordingly.
     * @param event
     */
    public void handleEvent(SwitchCargoEvent event){
        if(!event.player().equals(smugglersSlayer) && slayerCommits){
            throw new IllegalEventException("you have not landed");
        }
        else {
            EventHandler.handleEvent(event);
        }
    }

    /**
     * Handles the DoneEvent.
     * Checks if the player is the smugglers slayer and processes the event accordingly.
     * If all players have handled their events, it transitions to the reckoning phase.
     *
     * @param event The DoneEvent to handle.
     */
    public void handleEvent(DoneEvent event){
        if(!event.player().equals(smugglersSlayer) && slayerCommits){
            throw new IllegalEventException("you have not landed");
        }
        else {
            event.player().getShip().resetCargoFromCards();
            slayerCommits = false;
            handledPlayers.add(smugglersSlayer);
            for(Player p : game.getListOfActivePlayers()){
                if(!cargoToLose.containsKey(p)){
                    handledPlayers.add(p);
                }
            }
            reckoning();
            checkNext();
        }
    }

    /**
     * This method initiates the reckoning phase, in which players who have lost to the smugglers
     * have to remove cargo and batteries from their ships.
     */
    private void reckoning(){
        reckoningPhase = true;
        ArrayList<Player> deleteList = new ArrayList<>();
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
                handledPlayers.add(p);
            }
            else{
                available = p.getShip().getListOfBattery().stream()
                        .mapToInt(BatteryTile::getSlotsFilled)
                        .sum();
                if(available <= cargoToLose.get(p)) {
                    p.getShip().removeAllBatteries();
                    deleteList.add(p);
                    //cargoToLose.remove(p);
                    handledPlayers.add(p);
                }

            }
        }
        for(Player p : deleteList){
            cargoToLose.remove(p);
        }
        checkNext();
    }

    /**
     * Handles the disconnection consequences for a player.
     * If the player is the smugglers slayer, it sets the smugglers slayer to null.
     * It also removes the player from cargoToLose and handledPlayers.
     * If reckoningPhase is true, it checks for the next player.
     * Otherwise, it calls the superclass method.
     *
     * @param p The player who disconnected.
     */
    @Override
    protected void disconnectionConsequences(Player p) {
        List<Player> connectedPlayers = game.getListOfPlayers().stream().filter(player->player.getOnlineStatus()).toList();
        if(connectedPlayers.size() == 1){
            Player winner = connectedPlayers.get(0);
            game.getHourglass().disconnectionTimer(game, winner);
        }
        if(p.equals(smugglersSlayer)){
            smugglersSlayer = null;
        }
        cargoToLose.remove(p);
        handledPlayers.remove(p);
        if(reckoningPhase){
            checkNext();
        }
        else{
            super.disconnectionConsequences(p);
        }
    }


    public Map<Player, Integer> getCargoToLose() {
        return cargoToLose;
    }

    public Player getSmugglersSlayer() {
        return smugglersSlayer;
    }

    public Boolean getSlayerCommits() {
        return slayerCommits;
    }

    public boolean isReckoningPhase() {
        return reckoningPhase;
    }

    public List<Integer> getAvailableResources() {
        return availableResources;
    }

    public List<Player> getCargoless() {
        return cargoless;
    }
}
