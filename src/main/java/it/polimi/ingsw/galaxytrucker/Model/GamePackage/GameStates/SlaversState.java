package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.SlaversCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.*;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
when the slavers are defeated or all players are defeated the reckoningPhase starts
it ends when all defeated players have ejected their people
and the slayer has decided whether they want to claim the reward or not
 */
public class SlaversState extends TravellingState implements Serializable {
    public SlaversState(Game game, SlaversCard card) {
        super(game, card);
    }

    private SlaversCard currentCard;
    private Player slaversSlayer;
    private List<Player> defeatedPlayers;
    private boolean reckoningPhase = false;

    public void init(){
        currentPlayer = game.getListOfActivePlayers().get(0);
        defeatedPlayers = new ArrayList<>();
        handledPlayers = new ArrayList<>();
    }

    public void handleInput(ActivateCannonsEvent event){
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
                reckoning();
            }
        }
    }

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
            handledPlayers.add(currentPlayer);
            checkNext();
        }
    }

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

    @Override
    protected void nextPlayer() {
        super.nextPlayer();
        if(currentPlayer == null){
            reckoning();
        }
    }

    private void checkNext(){
        if(handledPlayers.containsAll(game.getListOfActivePlayers())){
            next();
        }
    }

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
            }
        }
       checkNext();
    }

}
