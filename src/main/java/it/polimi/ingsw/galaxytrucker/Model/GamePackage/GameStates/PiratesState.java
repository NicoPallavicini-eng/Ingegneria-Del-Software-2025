package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Cannonball;
import it.polimi.ingsw.galaxytrucker.Model.Cards.PiratesCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.ActivateCannonsEvent;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.ClaimRewardEvent;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.EventHandler;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.util.List;

public class PiratesState extends TravellingState{
    private PiratesCard currentCard;
    private Player piratesSlayer;
    private List<Player> defeatedPlayers;
    private boolean reckoningPhase = false;

    public PiratesState(Game game, PiratesCard card) {
        super(game, card);
    }

    public void init(){
        currentPlayer = game.getListOfPlayers().get(0);
    }

    public void handleInput(ActivateCannonsEvent event){
        if(!event.player().equals(currentPlayer)){
            throw new IllegalEventException("It is not your turn");
        }
        else if(piratesSlayer != null){
            throw new IllegalEventException("Pirates have already been slayed");
        }
        else{
            EventHandler.handleEvent(event);
            if(currentPlayer.getShip().getFirepower() < currentCard.getFirepower()){
                for(Cannonball c : currentCard.getCannonballList()){
                    defeatedPlayers.add(currentPlayer);
                }
                nextPlayer();
            }
            else if(currentPlayer.getShip().getFirepower() == currentCard.getFirepower()){
                nextPlayer();
            }
            else{
                piratesSlayer = currentPlayer;
                reckoningPhase = true;
            }
        }
    }

    @Override
    protected void nextPlayer(){
        super.nextPlayer();

    }

    public void handleEvent(ClaimRewardEvent event){
        if(!event.player().equals(piratesSlayer)){
            throw new IllegalEventException("You have not slain the pirates");
        }
        else{
            EventHandler.handleEvent(event);
        }
    }

    //todo come gestire cannonate e meteoriti

}
