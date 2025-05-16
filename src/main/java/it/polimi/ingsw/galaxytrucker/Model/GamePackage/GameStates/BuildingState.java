package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.*;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.*;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Hourglass;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BuildingState extends GameState implements Serializable {
    private final Game game;
    private ArrayList<Player> finishedBuildingPlayers;
    private ArrayList<Player> playersWithLegalShips;
    private boolean timeIsUp = false;
    // first for orange and second for purple
    private Map<Player, Boolean[]> placedAliens;

    public BuildingState( Game game ) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void next() {
        //todo populate everything that is not an alien
        game.sortListOfActivePlayers();
        if (game.getDeck() == null){
            throw new IllegalStateException("The deck is null");
        }
        else if (game.getDeck().drawCard() == null){
            throw new IllegalEventException("Card is null");
        }
        Card nextCard = game.getDeck().drawCard();
        //TODO CHECK IF IT'S OK!
        if(nextCard instanceof EpidemicCard epidemicCard){
            game.setGameState(TravellingStateFactory.createGameState(game, epidemicCard));
            game.getGameState().init();
        }
        else if(nextCard instanceof MeteorsCard meteorsCard){
            game.setGameState(TravellingStateFactory.createGameState(game, meteorsCard));
            game.getGameState().init();
        }
        else if(nextCard instanceof OpenSpaceCard openSpaceCard){
            game.setGameState(TravellingStateFactory.createGameState(game, openSpaceCard));
            game.getGameState().init();
        }
        else if (nextCard instanceof PiratesCard){
            game.setGameState(TravellingStateFactory.createGameState(game, (PiratesCard) nextCard));
            game.getGameState().init();
        }
        else if (nextCard instanceof PlanetsCard planetsCard){
            game.setGameState(TravellingStateFactory.createGameState(game, planetsCard));
            game.getGameState().init();
        }
        else if (nextCard instanceof ShipCard shipCard){
            game.setGameState(TravellingStateFactory.createGameState(game, shipCard));
            game.getGameState().init();
        }
        else if (nextCard instanceof SlaversCard slaversCard){
            game.setGameState(TravellingStateFactory.createGameState(game, slaversCard));
            game.getGameState().init();
        }
        else if (nextCard instanceof SmugglersCard smugglersCard){
            game.setGameState(TravellingStateFactory.createGameState(game, smugglersCard));
            game.getGameState().init();
        }
        else if (nextCard instanceof StardustCard stardustCard){
            game.setGameState(TravellingStateFactory.createGameState(game, stardustCard));
            game.getGameState().init();
        }
        else if (nextCard instanceof StationCard stationCard){
            game.setGameState(TravellingStateFactory.createGameState(game, stationCard));
            game.getGameState().init();
        }
        else if (nextCard instanceof CombatZoneCardL){
            game.setGameState(TravellingStateFactory.createGameState(game, (CombatZoneCardL) nextCard));
            game.getGameState().init();
        }
        else if (nextCard instanceof CombatZoneCardNotL){
            game.setGameState(TravellingStateFactory.createGameState(game, (CombatZoneCardNotL) nextCard));
            game.getGameState().init();
        }
        else{
            throw new IllegalEventException("The card is not a valid card");
        }

    }

    public void init(){
        finishedBuildingPlayers = new ArrayList<>();
        playersWithLegalShips = new ArrayList<>();
        placedAliens = new HashMap<>();
        for(Player player : game.getListOfActivePlayers()){
            placedAliens.put(player, new Boolean[] {false, false});
        }
        game.setHourglass(new Hourglass(this));
        game.getHourglass().flip();
    }


    public void handleEvent(SetPositionEvent event) {
        if(finishedBuildingPlayers.contains(event.player())){
            throw new IllegalEventException("You have already placed your rocket");
        }
        else{
            EventHandler.handleEvent(event,this.game);
            finishedBuildingPlayers.add(event.player());
            if(event.player().getShip().checkFloorPlanConnection()) {
                playersWithLegalShips.add(event.player());
            }
            if(finishedBuildingPlayers.containsAll(game.getListOfPlayers())) {
                timeIsUp = true;
                //controlla le navi
                if(playersWithLegalShips.containsAll(game.getListOfPlayers())) {
                    game.updateListOfActivePlayers();
                    next();
                }
            }

        }
    }

    public void handleEvent(RemoveTileEvent event) {
        if(!finishedBuildingPlayers.containsAll(game.getListOfActivePlayers())) {
            throw new IllegalEventException("You have to wait for all rockets to be placed");
        }
        else if(playersWithLegalShips.contains(event.player())){
            throw new IllegalEventException("You have already a functioning spaceship");
        }
        else{
            EventHandler.handleEvent(event);
            // se la nave è legale
            playersWithLegalShips.add(event.player());
        }
    }

    public void handleEvent(PickUpTileEvent event) {
        if(finishedBuildingPlayers.contains(event.player()) || timeIsUp){
            throw new IllegalEventException("You can no longer pick up any tiles");
        }
        else{
            EventHandler.handleEvent(event,this.game);
        }
    }

    public void handleEvent(PutDownTileEvent event) {
        if(finishedBuildingPlayers.contains(event.player()) || timeIsUp){
            throw new IllegalEventException("You can no longer put down any tiles");
        }
        else{
            EventHandler.handleEvent(event,this.game);
        }
    }

    public void handleEvent(PickUpFromShipEvent event) {
        if(finishedBuildingPlayers.contains(event.player()) || timeIsUp){
            throw new IllegalEventException("You can no longer pick up any tiles");
        }
        else{
            EventHandler.handleEvent(event);
        }
    }

    public void handleEvent(PickUpReservedTileEvent event) {
        if(finishedBuildingPlayers.contains(event.player()) || timeIsUp){
            throw new IllegalEventException("You can no longer pick up any tiles");
        }
        else{
            EventHandler.handleEvent(event);
        }
    }

    public void handleEvent(PlaceTileEvent event) {
        if(finishedBuildingPlayers.contains(event.player()) || timeIsUp){
            throw new IllegalEventException("You can no longer place any tiles");
        }
        else{
            EventHandler.handleEvent(event);
        }
    }

    public void handleEvent(ReserveTileEvent event) {
        if(finishedBuildingPlayers.contains(event.player()) || timeIsUp){
            throw new IllegalEventException("You can no longer reserve any tiles");
        }
        else{
            EventHandler.handleEvent(event);
        }
    }

    public void handleEvent(RotateTileEvent event) {
        if(finishedBuildingPlayers.contains(event.player()) || timeIsUp){
            throw new IllegalEventException("You can no longer rotate any tiles");
        }
        else{
            EventHandler.handleEvent(event);
        }
    }

    public void handleEvent(PlaceOrangeAlienEvent event) {
        if(!playersWithLegalShips.contains(event.player())){
            throw new IllegalEventException("You can't populate your ship until it is a legal ship ready to takeoff");
        }
        else if(placedAliens.get(event.player())[0] == true){
            throw new IllegalEventException("You have already made this choice");
        }
        else{
            EventHandler.handleEvent(event);
            Boolean[] aliens = placedAliens.get(event.player());
            aliens[0] = true;
            placedAliens.put(event.player(), aliens);
            checkNext();
        }
    }

    public void handleEvent(PlacePurpleAlienEvent event) {
        if(!playersWithLegalShips.contains(event.player())){
            throw new IllegalEventException("You can't populate your ship until it is a legal ship ready to takeoff");
        }
        else if(placedAliens.get(event.player())[1] == true){
            throw new IllegalEventException("You have already made this choice");
        }
        else{
            EventHandler.handleEvent(event);
            Boolean[] aliens = placedAliens.get(event.player());
            aliens[0] = true;
            placedAliens.put(event.player(), aliens);
            checkNext();
        }
    }



    public void handleEvent(FlipHourglassEvent event) {
        if (timeIsUp) {
            throw new IllegalEventException("Hourglass flipping phase is over");
        }
        else{
            EventHandler.handleEvent(event, game);
        }
    }

    public void handleEvent(ViewDeckEvent event) {
        if(finishedBuildingPlayers.contains(event.player()) || timeIsUp){
            throw new IllegalEventException("You can no longer view deck");
        }
        else{
            EventHandler.handleEvent(event);
        }
    }

    private void checkNext(){
        boolean flag = false;
        for(Player player : game.getListOfActivePlayers()){
            if(placedAliens.get(player)[0] == false || placedAliens.get(player)[1] == false){
                flag = true;
                break;
            }
        }
        if (!flag){
            next();
        }
    }

    public void timeUp(){
        timeIsUp = true;
    }


}
