package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Card;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.*;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Hourglass;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

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
        placedAliens = new HashMap<>();
        for(Player player : game.getListOfActivePlayers()){
            placedAliens.put(player, new Boolean[] {false, false});
        }
        game.setHourglass(new Hourglass(this));
        game.getHourglass().flip();
        game.notifyObservers(game, "time");
    }


    public void handleEvent(SetPositionEvent event) {
        if(finishedBuildingPlayers.contains(event.player())){
            throw new IllegalEventException("You have already placed your rocket");
        }
        else{
            EventHandler.handleEvent(event,this.game);
            finishedBuildingPlayers.add(event.player());
            if(finishedBuildingPlayers.containsAll(game.getListOfPlayers())) {
                timeIsUp = true;
                for(Player player : game.getListOfActivePlayers()) {
                    if(player.getShip().checkFloorPlanConnection()){
                        playersWithLegalShips.add(player);
                    }
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
            if(event.player().getShip().checkFloorPlanConnection()){
                playersWithLegalShips.add(event.player());
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

    public ArrayList<Player> getFinishedBuildingPlayers() {
        return finishedBuildingPlayers;
    }

    public ArrayList<Player> getPlayersWithLegalShips() {
        return playersWithLegalShips;
    }

    public boolean isTimeIsUp() {
        return timeIsUp;
    }

    public Map<Player, Boolean[]> getPlacedAliens() {
        return placedAliens;
    }
}
