package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Planet;
import it.polimi.ingsw.galaxytrucker.Model.Cards.PlanetsCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.*;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;
import java.util.*;

public class PlanetsState extends TravellingState implements Serializable {
    private PlanetsCard currentCard;
    private Player currentPlayer;
    private List<Planet> planets;
    private Map<Player, Planet> chosenPlanets;
    private List<Player> satisfiedPlayers;
    private boolean cargoLoadingPhase = false;

    public PlanetsState(Game game, PlanetsCard card) {
        super(game, card);
        currentCard = card;

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
        chosenPlanets = new LinkedHashMap<>();
        satisfiedPlayers = new ArrayList<>();
    }

    public void handleEvent(ChoosePlanetEvent event){
        if(!event.player().equals(currentPlayer) ){
            throw new IllegalEventException("It is not your turn to land");
        }
        else if(event.planetIndex() >= planets.size() || event.planetIndex() < 0){
            throw new IllegalEventException("you selecteded an illegal index");
        }
        else if(chosenPlanets.containsValue(planets.get(event.planetIndex()))){
            throw new IllegalEventException("The planet has already been chosen");
        }
        else{
            chosenPlanets.put(event.player(), planets.get(event.planetIndex()));
            nextPlayer();
        }
    }

    public void handleEvent(NoChoiceEvent event){
        if(!event.player().equals(currentPlayer) ){
            throw new IllegalEventException("It is not your turn to land");
        }
        else{
            synchronized (satisfiedPlayers) {
                satisfiedPlayers.add(event.player());
                if (satisfiedPlayers.containsAll(game.getListOfActivePlayers())) {
                    next();
                }
                nextPlayer();
            }
        }
    }

    private void loseDays(){
        List<Player> turns = new LinkedList<>(chosenPlanets.keySet());
        Collections.reverse(turns);
        for(Player player : turns){
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
        if(!cargoLoadingPhase){
            throw new IllegalEventException("Wait for everyone to choose a planet");
        }
        else if(satisfiedPlayers.contains(event.player())){
            throw new IllegalEventException("You have already concluded you selection");
        }
        else {
            List<Integer> availableResources = chosenPlanets.get(event.player()).getBlocks();
            EventHandler.handleEvent(event);
            synchronized (availableResources) {
                availableResources.add(event.resource());
            }

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
            synchronized (satisfiedPlayers) {
                satisfiedPlayers.add(event.player());
                if (satisfiedPlayers.containsAll(game.getListOfActivePlayers())) {
                    next();
                }
            }
        }
    }


}
