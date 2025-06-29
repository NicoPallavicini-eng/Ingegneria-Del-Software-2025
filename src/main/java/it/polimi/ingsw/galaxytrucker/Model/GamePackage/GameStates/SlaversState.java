package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.SlaversCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.*;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Following the turns all player decide whether to activate cannons or noaction,
 * their firepower then is confronted with the pirates' one to decide who wins
 * when the slavers are defeated or all players are defeated the reckoningPhase starts
 * it ends when all defeated players have ejected their people
 * and the slayer has decided whether they want to claim the reward or not
 */
public class SlaversState extends TravellingState implements Serializable {
    /**
     * @param game
     * @param card
     */
    public SlaversState(Game game, SlaversCard card) {
        super(game, card);
        currentCard = card;

    }

    private SlaversCard currentCard;
    private Player slaversSlayer;
    private List<Player> defeatedPlayers;
    private boolean reckoningPhase = false;

    /**
     * this function initialize Slavers State
     */
    public void init(){
        currentPlayer = game.getListOfActivePlayers().get(0);
        defeatedPlayers = new ArrayList<>();
        handledPlayers = new ArrayList<>();
        game.notifyObservers(game, "slavers");
    }

    /**
     * ActivateCannonsEvent is possible during Slavers State
     * @param event
     */
    public void handleEvent(ActivateCannonsEvent event){
        if(!event.player().equals(currentPlayer)){
            throw new IllegalEventException("It is not your turn");
        }
        else{
            EventHandler.handleEvent(event);
            if(currentPlayer.getShip().getFirepower() < currentCard.getFirepower()){
                synchronized (defeatedPlayers) {
                    defeatedPlayers.add(currentPlayer);
                }
                nextPlayer();
            }
            else if(currentPlayer.getShip().getFirepower() == currentCard.getFirepower()){
                nextPlayer();
            }
            else{
                slaversSlayer = currentPlayer;
                game.notifyObservers(game, "slaversDefeated");
                reckoning();
            }
        }
    }
    /**
     * ClaimRewardEvent is possible during Slavers State
     * @param event
     */
    public void handleEvent(ClaimRewardEvent event){
        if(!event.player().equals(slaversSlayer)){
            throw new IllegalEventException("You have not slain the slavers");
        }
        else if (handledPlayers.contains(slaversSlayer)) {
            throw new IllegalEventException("You have already decided whether to collect your reward");
        }
        else{
            slaversSlayer.getShip().setCredits(slaversSlayer.getShip().getCredits() + currentCard.getNumberOfCredits());
            EventHandler.moveBackward(slaversSlayer.getShip(), currentCard.getNumberOfDaysToLose(), game);
            handledPlayers.add(slaversSlayer);
            checkNext();
        }
    }

    /**
     * NoChoiceEvent is possible during Slavers State
     * @param event
     */
    public void handleEvent(NoChoiceEvent event){
        if(reckoningPhase){
            if(!event.player().equals(slaversSlayer)){
                throw new IllegalEventException("You have not slain the slavers");
            }
            else{
                handledPlayers.add(event.player());
                checkNext();
            }
        }
        else{
            if(!event.player().equals(currentPlayer)){
                throw new IllegalEventException("It is not your turn");
            }
            else{
                if(currentPlayer.getShip().getFirepower() < currentCard.getFirepower()){
                    synchronized (defeatedPlayers) {
                        defeatedPlayers.add(currentPlayer);
                    }
                    nextPlayer();
                }
                else if(currentPlayer.getShip().getFirepower() == currentCard.getFirepower()){
                    nextPlayer();
                }
                else{
                    slaversSlayer = currentPlayer;
                    reckoning();
                }
            }
        }

    }

    /**
     * EjectPeopleEvent is possible during Slavers State
     * @param event
     */
    public void handleEvent(EjectPeopleEvent event){
        if(!defeatedPlayers.contains(event.player()) || handledPlayers.contains(event.player())){
            throw new IllegalEventException("You don't have to give up your crew");
        }
        else{
            int ejected = event.people().stream().mapToInt(l -> l.get(2)).sum();
            if(ejected != currentCard.getNumberOfCrewLost()){
                throw new IllegalEventException("You have to give up " + currentCard.getNumberOfCrewLost() + " crew members, not " + ejected);
            }
            else{
                EventHandler.handleEvent(event);
                handledPlayers.add(event.player());
                checkNext();
            }
        }
    }

    /**
     * This function manages the turn of Players
     */
    @Override
    protected void nextPlayer() {
        super.nextPlayer();
        if(currentPlayer == null){
            reckoning();
        }
    }

    /**
     * This function checks if it can change State
     */
    private void checkNext(){
        if(handledPlayers.containsAll(game.getListOfActivePlayers())){
            next();
        }
    }

    /**
     * This function find Players that were defeated and add them to the list of defeated Players
     */
    private void reckoning(){
       reckoningPhase = true;
       currentPlayer = null;
       handledPlayers.addAll(game.getListOfActivePlayers());
       handledPlayers.removeAll(defeatedPlayers);
       handledPlayers.remove(slaversSlayer);
       //handle all ships with less crew
        for(Player p : defeatedPlayers){
            if(p.getShip().getNumberOfInhabitants() <= currentCard.getNumberOfCrewLost()){
                p.getShip().ejectAll();
                handledPlayers.add(p);
            }
        }
//        List<Player> iterator= new ArrayList<>(handledPlayers);
//        for(Player p : iterator){
//            if(p==null){
//                handledPlayers.remove(p);
//            }
//        }
       checkNext();
    }
    /**
     * This function handles Player,that disconnected from Game
     * @param p Player
     */
    @Override
    protected void disconnectionConsequences(Player p) {
        List<Player> connectedPlayers = game.getListOfPlayers().stream().filter(player->player.getOnlineStatus()).toList();
        if(connectedPlayers.size() == 1){
            Player winner = connectedPlayers.get(0);
            game.getHourglass().disconnectionTimer(game, winner);
        }
        if(p.equals(slaversSlayer)){
            slaversSlayer = null;
        }
        defeatedPlayers.remove(p);
        handledPlayers.remove(p);
        if(reckoningPhase){
            checkNext();
        }
        else{
            super.disconnectionConsequences(p);
        }
    }

    /**
     * This function return slaversSlayer
     * @return Player
     */
    public Player getSlaversSlayer() {
        return slaversSlayer;
    }

    public List<Player> getDefeatedPlayers() {
        return defeatedPlayers;
    }

    public boolean isReckoningPhase() {
        return reckoningPhase;
    }
}
