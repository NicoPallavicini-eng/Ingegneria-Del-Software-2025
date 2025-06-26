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


/* This phase starts when the desired number of players has connected to the game:
At first the players can build their ship by picking up tiles and placing them on their ship,
then they finish this phase by setting their position,
if the hourglass expires setting their position is the only available option.
After all positions have been set (all players in finishedBuildingPlayers) the legality check starts
the check populates the playerWithLegalShips list,
the players with illegal ships can join the list by removing tiles from their ship.
When the list is full the players enter the population phase,
in which they can place aliens and signal "done".
Once all players have placed their aliens next() is called.
 */
public class BuildingState extends GameState implements Serializable {
    private final Game game;
    private ArrayList<Player> finishedBuildingPlayers;
    private ArrayList<Player> playersWithLegalShips;
    private ArrayList<Player> playersWithIllegalShips;
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
        for(Player p : game.getListOfActivePlayers()){
            p.getShip().fill();
        }
        game.updateListOfActivePlayers();
        game.sortListOfActivePlayers();
        Card nextCard = getGame().getDeck().drawCard();
        getGame().setGameState(nextCard.createGameState(game));
        game.getGameState().init();

    }

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

    public void handleEvent(DoneEvent event) {
        if(!playersWithLegalShips.contains(event.player())){
            throw new IllegalEventException("You can't populate your ship until it is a legal ship ready to takeoff");
        }
        else{
            placedAliens.put(event.player(), new Boolean[]{true, true});
            checkNext();
        }
    }

    public void handleEvent(FlipHourglassEvent event,Game game) {
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
            //todo lock for gui, the event shall be handled in the controller
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
        game.notifyObservers(game, "timeisup");
    }

    protected void disconnectionConsequences(Player p){
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

    public void handleEvent(ConnectEvent event, Game game) {
        super.handleEvent(event, game);
        game.updateListOfActivePlayers();
    }

    public ArrayList<Player> getFinishedBuildingPlayers() {
        return finishedBuildingPlayers;
    }

    public ArrayList<Player> getPlayersWithLegalShips() {
        return playersWithLegalShips;
    }

    public ArrayList<Player> getPlayersWithIllegalShips() {
        return playersWithIllegalShips;
    }

    public boolean isTimeIsUp() {
        return timeIsUp;
    }

    public Map<Player, Boolean[]> getPlacedAliens() {
        return placedAliens;
    }
}
