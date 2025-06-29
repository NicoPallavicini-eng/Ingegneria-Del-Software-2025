package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Card;
import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.*;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Hourglass;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * This phase starts when the desired number of players has connected to the game:
 * At first the players can build their ship by picking up tiles and placing them on their ship,
 * then they finish this phase by setting their position,
 * if the hourglass expires setting their position is the only available option.
 * After all positions have been set (all players in finishedBuildingPlayers) the legality check starts
 * the check populates the playerWithLegalShips list,
 * the players with illegal ships can join the list by removing tiles from their ship.
 * When the list is full the players enter the population phase,
 * in which they can place aliens and signal "done".
 * Once all players have placed their aliens next() is called.
 */
public class BuildingState extends GameState implements Serializable {
    private ArrayList<Player> finishedBuildingPlayers;
    private ArrayList<Player> playersWithLegalShips;
    private ArrayList<Player> playersWithIllegalShips;
    private boolean timeIsUp = false;
    // first for orange and second for purple
    private Map<Player, Boolean[]> placedAliens;

    /**
     * @param game
     */
    public BuildingState( Game game ) {
        this.game = game;
    }

    /**
     * This function return Game
     * @return Game
     */
    public Game getGame() {
        return game;
    }

    /** * This function sets the next Game Set
     */
    public void next() {
        for(Player p : game.getListOfActivePlayers()){
            p.getShip().fill();
        }
        game.updateListOfActivePlayers();
        game.sortListOfActivePlayers();
        Card nextCard = getGame().getDeck().drawCard();
        getGame().setGameState(nextCard.createGameState(game));
        game.getGameState().init();

    }

    /**
     * This function initialize Building State
     */
    public void init(){
        finishedBuildingPlayers = new ArrayList<>();
        playersWithLegalShips = new ArrayList<>();
        playersWithIllegalShips = new ArrayList<>();
        placedAliens = new HashMap<>();
        for(Player player : game.getListOfActivePlayers()){
            placedAliens.put(player, new Boolean[] {false, false});
        }
        game.setHourglass(new Hourglass(this));
        game.getHourglass().flip();
        game.notifyObservers(game, "time");
    }


    /**
     * This function is used to set a predefined ship for testing purposes.
     */
    public void handleEvent(Ship ship){

        CabinTile cabin1 = new CabinTile(ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.NONE, CabinInhabitants.NONE,false, Color.NONE,1,0);
        CabinTile cabin2 = new CabinTile(ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,CabinInhabitants.NONE,false,Color.NONE,0,0);
        CannonTile cannon1 = new CannonTile(ConnectorType.CANNON_SINGLE,ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.NONE,false,true);
        CannonTile cannon2 = new CannonTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.CANNON_SINGLE,ConnectorType.NONE,false,true);
        EngineTile engine1 = new EngineTile(true,false,ConnectorType.DOUBLE,ConnectorType.ENGINE_DOUBLE,ConnectorType.NONE,ConnectorType.UNIVERSAL);
        EngineTile engine2 = new EngineTile(false,true,ConnectorType.DOUBLE,ConnectorType.ENGINE_SINGLE,ConnectorType.NONE,ConnectorType.NONE);
        BioadaptorTile bioadaptorTile = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.NONE,AlienColor.PURPLE);
        CargoTile cargoTile = new CargoTile(ConnectorType.NONE,ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.NONE,2,false,null);
        BatteryTile batteryTile = new BatteryTile(ConnectorType.NONE,ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.DOUBLE,2,2);
        ShieldTile shieldTile = new ShieldTile(ConnectorType.NONE,ConnectorType.DOUBLE,ConnectorType.UNIVERSAL,ConnectorType.NONE,ShieldOrientation.NORTHWEST,false);
        Tile tile = new Tile(ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.DOUBLE,ConnectorType.NONE);

        //ship.setTileOnFloorPlan(2,3,centralCabin);
        ship.setTileOnFloorPlan(1,3,cannon1);
        ship.setTileOnFloorPlan(3,3,engine1);
        ship.setTileOnFloorPlan(2,2,bioadaptorTile);
        ship.setTileOnFloorPlan(1,2,cargoTile);
        ship.setTileOnFloorPlan(3,2,cabin1);
        ship.setTileOnFloorPlan(2,4,cabin2);
        ship.setTileOnFloorPlan(1,4,shieldTile);
        ship.setTileOnFloorPlan(3,4,tile);
        ship.setTileOnFloorPlan(4,4,engine2);
        ship.setTileOnFloorPlan(2,5,cannon2);
        ship.setTileOnFloorPlan(1,5,batteryTile);

        cabin1.setFacingUp(true);
        cabin2.setFacingUp(true);
        cannon1.setFacingUp(true);
        cannon2.setFacingUp(true);
        engine1.setFacingUp(true);
        engine2.setFacingUp(true);
        bioadaptorTile.setFacingUp(true);
        cargoTile.setFacingUp(true);
        batteryTile.setFacingUp(true);
        shieldTile.setFacingUp(true);
        tile.setFacingUp(true);
    }

    /**This function handles ChooseSubShipEvent
     *
     * @param event
     */
    public void handleEvent(ChooseSubShipEvent event){
        if(!playersWithIllegalShips.contains(event.player())){
            throw new IllegalEventException("You have already a functioning spaceship");
        }
        else{
            EventHandler.handleEvent(event);
            synchronized (playersWithIllegalShips) {
                if(event.player().getShip().checkFloorPlanConnection()){
                    playersWithIllegalShips.remove(event.player());
                    playersWithLegalShips.add(event.player());
                    game.notifyObservers(game, "legalship");
                }
            }
        }
    }

    /**This function handles SetPositionEvent, after checking if the player has already placed their rocket.
     * If the player has not placed their rocket yet, it processes the event and updates the game state.
     * If all players have placed their rockets, it checks the legality of each player's ship.
     * If a player's ship is legal, they are added to the playersWithLegalShips list,
     * otherwise, they are added to the playersWithIllegalShips list.
     * @param event
     */
    public void handleEvent(SetPositionEvent event) {
        if(finishedBuildingPlayers.contains(event.player())){
            throw new IllegalEventException("You have already placed your rocket");
        }
        else{
            EventHandler.handleEvent(event,this.game);
            game.notifyObservers(game, "leaderboard");
            finishedBuildingPlayers.add(event.player());
            if(finishedBuildingPlayers.containsAll(game.getListOfActivePlayers())) {
                timeIsUp = true;
                for(Player player : game.getListOfActivePlayers()) {
                    if(player.getShip().checkFloorPlanConnection()){
                        synchronized(playersWithLegalShips) {
                            playersWithLegalShips.add(player);
                            /*
                            if (playersWithIllegalShips.contains(player)) {
                                playersWithIllegalShips.remove(player);
                            }
                             */
                        }
                    }
                    else {
                        synchronized (playersWithLegalShips) {
                            playersWithIllegalShips.add(player);
                        }
                    }
                }
                game.notifyObservers(game, "shipPlaced");
            }

        }
    }

    /** This function handles RemoveTileEvent, which is used to remove a tile from a player's ship.
     * It checks if all players have finished building their ships,
     * if not, it throws an exception.
     * If the player has already a legal ship, it throws an exception as well.
     * If the player has not a legal ship, it processes the event and checks if the ship is still legal after the removal.
     * @param event
     */
    public void handleEvent(RemoveTileEvent event) {
        if(!finishedBuildingPlayers.containsAll(game.getListOfActivePlayers())) {
            throw new IllegalEventException("You have to wait for all rockets to be placed");
        }
        else if(playersWithLegalShips.contains(event.player())){
            throw new IllegalEventException("You have already a functioning spaceship");
        }
        else{
            EventHandler.handleEvent(event);
            if(event.player().getShip().checkFloorPlanConnection()){
                synchronized (playersWithLegalShips) {
                    playersWithLegalShips.add(event.player());
                    game.notifyObservers(game, "legalship");
                    playersWithIllegalShips.remove(event.player());
                }
            }
            else{
                game.notifyObservers(game, "illegalship");
            }
        }
    }

    /**
     * this function handles PickUpTileEvent, which is used to pick up a tile from the game board.
     * It checks if the player has already finished building their ship or if the time is up.
     * @param event
     */
    public void handleEvent(PickUpTileEvent event) {
        if(finishedBuildingPlayers.contains(event.player()) || timeIsUp){
            throw new IllegalEventException("You can no longer pick up any tiles");
        }
        else{
            EventHandler.handleEvent(event,this.game);
        }
    }

    /**
     * This function handles PutDownTileEvent, which is used to put down a tile on a player's ship.
     * It checks if the player has already finished building their ship or if the time is up.
     * @param event
     */
    public void handleEvent(PutDownTileEvent event) {
        if(finishedBuildingPlayers.contains(event.player()) || timeIsUp){
            throw new IllegalEventException("You can no longer put down any tiles");
        }
        else{
            EventHandler.handleEvent(event,this.game);
        }
    }
    /**
     * This function handles PickUpFromShipEvent, which is used to pick up a tile from a player's ship.
     * It checks if the player has already finished building their ship or if the time is up.
     * @param event
     */
    public void handleEvent(PickUpFromShipEvent event) {
        if(finishedBuildingPlayers.contains(event.player()) || timeIsUp){
            throw new IllegalEventException("You can no longer pick up any tiles");
        }
        else{
            EventHandler.handleEvent(event);
        }
    }
    /**
     * this function handles PickUpReservedTileEvent, which is used to pick up a reserved tile.
     * It checks if the player has already finished building their ship or if the time is up.
     * @param event
     */
    public void handleEvent(PickUpReservedTileEvent event) {
        if(finishedBuildingPlayers.contains(event.player()) || timeIsUp){
            throw new IllegalEventException("You can no longer pick up any tiles");
        }
        else{
            EventHandler.handleEvent(event);
        }
    }
    /**
     * This function handles PlaceTileEvent, which is used to place a tile on a player's ship.
     * It checks if the player has already finished building their ship or if the time is up.
     * @param event
     */
    public void handleEvent(PlaceTileEvent event) {
        if(finishedBuildingPlayers.contains(event.player()) || timeIsUp){
            throw new IllegalEventException("You can no longer place any tiles");
        }
        else{
            EventHandler.handleEvent(event);
        }
    }
    /**
     * This function handles PlaceTileEvent, which is used to place a tile on a player's ship.
     * It checks if the player has already finished building their ship or if the time is up.
     * @param event
     */
    public void handleEvent(ReserveTileEvent event) {
        if(finishedBuildingPlayers.contains(event.player()) || timeIsUp){
            throw new IllegalEventException("You can no longer reserve any tiles");
        }
        else{
            EventHandler.handleEvent(event);
        }
    }
    /**
     * This function handles PlaceTileEvent, which is used to place a tile on a player's ship.
     * It checks if the player has already finished building their ship or if the time is up.
     * @param event
     */
    public void handleEvent(RotateTileEvent event) {
        if(finishedBuildingPlayers.contains(event.player()) || timeIsUp){
            throw new IllegalEventException("You can no longer rotate any tiles");
        }
        else{
            EventHandler.handleEvent(event);
        }
    }
    /**
     * THis function handles PlaceOrangeAlienEvent, which is used to place an orange alien on a player's ship.
     * It checks if the player has a legal ship ready to takeoff and if they have already placed an orange alien.
     * If the player has not placed an orange alien yet, it processes the event and updates the placedAliens map.
     * @param event
     */
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
    /**
     * This function handles PlacePurpleAlienEvent, which is used to place a purple alien on a player's ship.
     * It checks if the player has a legal ship ready to takeoff and if they have already placed a purple alien.
     * If the player has not placed a purple alien yet, it processes the event and updates the placedAliens map.
     * @param event
     */
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
    /**
     * This function handles DoneEvent, which is used to signal that a player has finished placing their aliens.
     * It checks if the player has a legal ship ready to takeoff and if they have already signaled done.
     * If the player has not signaled done yet, it updates the placedAliens map and checks if all players have finished placing their aliens.
     * @param event
     */
    public void handleEvent(DoneEvent event) {
        if(!playersWithLegalShips.contains(event.player())){
            throw new IllegalEventException("You can't populate your ship until it is a legal ship ready to takeoff");
        }
        else{
            placedAliens.put(event.player(), new Boolean[]{true, true});
            checkNext();
        }
    }
    /**
     * This function handles FlipHourglassEvent, which is used to flip the hourglass during the building phase.
     * It checks if the time is already up, and if so, it throws an exception.
     * If the time is not up, it processes the event using the EventHandler.
     * @param event
     */
    public void handleEvent(FlipHourglassEvent event,Game game) {
        if (timeIsUp) {
            throw new IllegalEventException("Hourglass flipping phase is over");
        }
        else{
            EventHandler.handleEvent(event, game);
        }
    }
    /**
     * This function handles ViewDeckEvent, which is used to view the deck during the building phase.
     * It checks if the player has already finished building their ship or if the time is up.
     * If the player has finished building their ship or if the time is up, it throws an exception.
     * @param event
     */
    public void handleEvent(ViewDeckEvent event) {
        if(finishedBuildingPlayers.contains(event.player()) || timeIsUp){
            throw new IllegalEventException("You can no longer view deck");
        }
    }

    /**
     * This function checks if all players have placed their aliens.
     * If all players have placed both their orange and purple aliens,
     * it calls the next() function to proceed to the next game state.
     */
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

    /**
     * This function notify observers when time is finished
     */
    public void timeUp(){
        timeIsUp = true;
        game.notifyObservers(game, "timeisup");
    }

    /**
     * This function manage disconnection of Player
     * @param p Player
     */
    protected void disconnectionConsequences(Player p){
        super.disconnectionConsequences(p);
        if(finishedBuildingPlayers.containsAll(game.getListOfActivePlayers())) {
            timeIsUp = true;
            for(Player player : game.getListOfActivePlayers()) {
                if(player.getShip().checkFloorPlanConnection()){
                    playersWithLegalShips.add(player);
                }
            }
        }
        placedAliens.get(p)[0] = true;
        placedAliens.get(p)[1] = true;
        checkNext();
    }

    /**
     * ConnectEvent is allowed during Building State
     * @param event
     * @param game
     */
    public void handleEvent(ConnectEvent event, Game game) {
        super.handleEvent(event, game);
        game.updateListOfActivePlayers();
    }

    /**
     * This function return array list of players that finished building their Ship
     * @return ArrayList<Player>
     */
    public ArrayList<Player> getFinishedBuildingPlayers() {
        return finishedBuildingPlayers;
    }

    /**
     * This function return array list of Players with Legal Ships
     * @return ArrayList<Player>
     */
    public ArrayList<Player> getPlayersWithLegalShips() {
        return playersWithLegalShips;
    }
    /**
     * This function return array list of Players with illegal Ships
     * @return ArrayList<Player>
     */
    public ArrayList<Player> getPlayersWithIllegalShips() {
        return playersWithIllegalShips;
    }

    /**
     * This function tells you whether time is ginsihed or not
     * @return boolean
     */
    public boolean isTimeIsUp() {
        return timeIsUp;
    }

    /**
     * This function return placed Aliens
     * @return Map<Player, Boolean[]>
     */
    public Map<Player, Boolean[]> getPlacedAliens() {
        return placedAliens;
    }
}
