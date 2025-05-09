package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Planet;
import it.polimi.ingsw.galaxytrucker.Model.Cards.PlanetsCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.*;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlanetsState extends TravellingState implements Serializable {
    private PlanetsCard currentCard;
    private Player currentPlayer;
    private List<Planet> planets;
    private Map<Player, Planet> chosenPlanets;
    private List<Player> satisfiedPlayers;
    private boolean cargoLoadingPhase = false;

    public PlanetsState(Game game, PlanetsCard card) {
        super(game, card);
    }

    public void nextPlayer(){
        super.nextPlayer();
        if(currentPlayer == null){
            loseDays();
            cargoLoadingPhase = true;
        }
    }

    public void init(){
        super.init();
        planets = currentCard.getPlanetsList();
        chosenPlanets = new HashMap<>();
        satisfiedPlayers = new ArrayList<>();
    }

    public void handleEvent(ChoosePlanetEvent event){
        // todo check index
        if(!event.player().equals(currentPlayer) ){
            throw new IllegalEventException("It is not your turn to land");
        }
        else{
            chosenPlanets.put(event.player(), planets.get(event.planetIndex()));
            nextPlayer();
        }
    }

    private void handleEvent(NoChoiceEvent event){
        if(!event.player().equals(currentPlayer) ){
            throw new IllegalEventException("It is not your turn to land");
        }
        else{
            satisfiedPlayers.add(event.player());
            if(satisfiedPlayers.containsAll(game.getListOfPlayers())){
                next();
            }
            nextPlayer();
        }
    }

    private void loseDays(){
        for(Player player : chosenPlanets.keySet()){
            EventHandler.moveBackward(player.getShip(), currentCard.getDaysToLose(), game);
        }
    }

    public void handleEvent(AddCargoEvent event){
        if(!cargoLoadingPhase){
            throw new IllegalEventException("Wait for everyone to choose a planet");
        }
        else if(satisfiedPlayers.contains(event.player())){
            throw new IllegalEventException("You have already concluded you selection");
        }
        else {
            List<Integer> availableResources = chosenPlanets.get(event.player()).getBlocks();
            if (!availableResources.contains(event.resource())) {
                throw new IllegalEventException("the block you are trying to add is not present");
            } else {
                EventHandler.handleEvent(event);
                availableResources.remove(event.resource());
            }
        }
    }

    public void handleEvent(RemoveCargoEvent event){
        if(!cargoLoadingPhase){
            throw new IllegalEventException("Wait for everyone to choose a planet");
        }
        else if(satisfiedPlayers.contains(event.player())){
            throw new IllegalEventException("You have already concluded you selection");
        }
        else {
            List<Integer> availableResources = chosenPlanets.get(event.player()).getBlocks();
            EventHandler.handleEvent(event);
            availableResources.add(event.resource());

        }
    }

    public void handleEvent(SwitchCargoEvent event){
        if(!cargoLoadingPhase){
            throw new IllegalEventException("Wait for everyone to choose a planet");
        }
        else if(satisfiedPlayers.contains(event.player())){
            throw new IllegalEventException("You have already concluded you selection");
        }
        else {
            EventHandler.handleEvent(event);
        }
    }

    public void handleEvent(DoneEvent event){
        if(!cargoLoadingPhase){
            throw new IllegalEventException("Wait for everyone to choose a planet");
        }
        else if(satisfiedPlayers.contains(event.player())){
            throw new IllegalEventException("You have already concluded you selection");
        }
        else {
            satisfiedPlayers.add(event.player());
            if(satisfiedPlayers.containsAll(game.getListOfPlayers())){
                next();
            }
        }
    }


}
