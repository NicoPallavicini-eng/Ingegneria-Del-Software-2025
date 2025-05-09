package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Cannonball;
import it.polimi.ingsw.galaxytrucker.Model.Cards.PiratesCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.*;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*Players fight pirates according to their travel order,
once pirates have been slain or all player have been defeated
the reckoning phase starts for all defeated players
lastly the player that defeated pirates can loot them if they wish iin the claiming phase
*/

public class PiratesState extends TravellingState implements Serializable {
    private PiratesCard currentCard;
    private Player piratesSlayer;
    private List<Player> defeatedPlayers;
    private boolean reckoningPhase = false;
    private boolean claimingPhase = false;
    private Cannonball currentCannonball;
    private List<Player> defendedPlayers;

    public PiratesState(Game game, PiratesCard card) {
        super(game, card);
    }

    public void init(){
        currentPlayer = game.getListOfPlayers().get(0);
        defeatedPlayers = new ArrayList<>();
        defendedPlayers = new ArrayList<>();
    }

    public void handleInput(ActivateCannonsEvent event){
        if(!event.player().equals(currentPlayer)){
            throw new IllegalEventException("It is not your turn");
        }
        else{
            EventHandler.handleEvent(event);
            if(currentPlayer.getShip().getFirepower() < currentCard.getFirepower()){
                defeatedPlayers.add(currentPlayer);
                nextPlayer();
            }
            else if(currentPlayer.getShip().getFirepower() == currentCard.getFirepower()){
                nextPlayer();
            }
            else{
                piratesSlayer = currentPlayer;
                currentPlayer = null;
                reckoningPhase = true;
                consequences();
            }
        }
    }

    @Override
    protected void nextPlayer(){
        super.nextPlayer();
        if(currentPlayer == null){
            reckoningPhase = true;
            consequences();
        }
    }

    public void handleEvent(ClaimRewardEvent event){
        if(!event.player().equals(piratesSlayer)){
            throw new IllegalEventException("You have not slain the pirates");
        }
        else if (!claimingPhase) {
            throw new IllegalEventException("Wait for all defeated players to be attacked by pirates");
        }
        else{
            piratesSlayer.getShip().setCredits(piratesSlayer.getShip().getCredits() + currentCard.getCredits());
            EventHandler.moveBackward(piratesSlayer.getShip(), currentCard.getDaysToLose(), game);
            next();
        }
    }

    public void handleEvent(NoChoiceEvent event){
        if(claimingPhase){
            if(!event.player().equals(piratesSlayer)){
                throw new IllegalEventException("You have not slain the pirates");
            }
            else{
                next();
            }
        }
        else if(reckoningPhase) {
            if(!defeatedPlayers.contains(event.player()) || defendedPlayers.contains(event.player())){
                throw new IllegalEventException("no choice to make");
            }
            else{
                currentCannonball.getHit(event.player().getShip());
                defendedPlayers.add(event.player());
            }
        }
        else{
            if(!event.player().equals(currentPlayer)){
                throw new IllegalEventException("It is not your turn");
            }
            else{
                if(currentPlayer.getShip().getFirepower() < currentCard.getFirepower()){
                defeatedPlayers.add(currentPlayer);
                nextPlayer();
                }
                else if(currentPlayer.getShip().getFirepower() == currentCard.getFirepower()){
                    nextPlayer();
                }
                else{
                    piratesSlayer = currentPlayer;
                    currentPlayer = null;
                    consequences();
                }
            }
        }

    }

    private void consequences(){
        if(currentCard.getCannonballList().isEmpty()){
            reckoningPhase = false;
            claimingPhase = true;
            if(piratesSlayer == null){
                next();
            }
        }
        else{
            defendedPlayers.clear();
            currentCannonball = currentCard.getCannonballList().getFirst();
            currentCard.getCannonballList().removeFirst();
            if(currentCannonball.bigCannonball()){
                for(Player player : defeatedPlayers){
                    currentCannonball.getHit(player.getShip());
                }
                consequences();
            }
        }
    }

    public void handleEvent(ActivateShieldEvent event){
        if(!reckoningPhase){
            throw new IllegalEventException("Not time for acivating shield");
        }
        else if(!defeatedPlayers.contains(event.player()) || defendedPlayers.contains(event.player())){
            throw new IllegalEventException("you shall not defend");
        }
        else{
            EventHandler.handleEvent(event);
            defendedPlayers.add(event.player());
            currentCannonball.getHit(event.player().getShip());
            if(defendedPlayers.containsAll(defeatedPlayers)){
                consequences();
            }
        }
    }


}
