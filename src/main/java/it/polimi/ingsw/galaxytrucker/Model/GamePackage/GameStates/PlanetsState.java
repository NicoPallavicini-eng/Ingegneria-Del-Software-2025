package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Planet;
import it.polimi.ingsw.galaxytrucker.Model.Cards.PlanetsCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.*;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;
import java.util.*;

/*Each player chooses a planet or noaction.
once everyone has made a choice the cargoLoadingPhase starts
in this phase players can add, remove, switch cargo until they signal done
The satisfied players list hold the players that have signaled noAction or Done
 */

public class PlanetsState extends TravellingState implements Serializable {
    private PlanetsCard currentCard;
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

    @Override
    public void init(){
        super.init();
        planets = currentCard.getPlanetsList();
        chosenPlanets = new LinkedHashMap<>();
        satisfiedPlayers = new ArrayList<>();
        game.notifyObservers(game, "planets");
    }

    public void handleEvent(ChoosePlanetEvent event){
        int index = event.planetIndex() - 1;
        if(!event.player().equals(currentPlayer) ){
            throw new IllegalEventException("It is not your turn to land");
        }
        else if(index >= planets.size() || index < 0){
            throw new IllegalEventException("you selected an illegal index");
        }
        else if(chosenPlanets.containsValue(planets.get(index))){
            throw new IllegalEventException("The planet has already been chosen");
        }
        else{
            chosenPlanets.put(event.player(), planets.get(index));
            event.player().getShip().addBlocks(new ArrayList<>(planets.get(index).getBlocks()));
            game.notifyObservers(game, "planetsSelection");
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
                game.notifyObservers(game, "planetsNoSelection");
                if (satisfiedPlayers.containsAll(game.getListOfActivePlayers())) {
                    next();
                } else {
                    nextPlayer();
                }
            }
        }
    }

    private void loseDays(){
        List<Player> turns = new LinkedList<>(chosenPlanets.keySet());
        Collections.reverse(turns);
        for(Player player : turns){
            EventHandler.moveBackward(player.getShip(), currentCard.getDaysToLose(), game);
            game.notifyObservers(game, "loseDays"); //TODO: DO it
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
                event.player().getShip().resetCargoFromCards();
                satisfiedPlayers.add(event.player());
                if (satisfiedPlayers.containsAll(game.getListOfActivePlayers())) {
                    next();
                }
            }
        }
    }

    protected void disconnectionConsequences(Player p){
        List<Player> connectedPlayers = game.getListOfPlayers().stream().filter(player->player.getOnlineStatus()).toList();
        if(connectedPlayers.size() == 1){
            Player winner = connectedPlayers.get(0);
            game.getHourglass().disconnectionTimer(game, winner);
        }
        if(cargoLoadingPhase){
            chosenPlanets.remove(p);
        }
        else{
            super.disconnectionConsequences(p);
        }

        if(satisfiedPlayers.containsAll(game.getListOfActivePlayers())){
            next();
        }
    }

    public Map<Player, Planet> getChosenPlanets() {
        return chosenPlanets;
    }

    public List<Player> getSatisfiedPlayers() {
        return satisfiedPlayers;
    }

    public boolean isCargoLoadingPhase() {
        return cargoLoadingPhase;
    }
}
