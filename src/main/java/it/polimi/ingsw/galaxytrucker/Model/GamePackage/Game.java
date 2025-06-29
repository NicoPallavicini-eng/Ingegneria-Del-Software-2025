package it.polimi.ingsw.galaxytrucker.Model.GamePackage;


import it.polimi.ingsw.galaxytrucker.Controller.Server.Observer;
import it.polimi.ingsw.galaxytrucker.JsonCardParsing;
import it.polimi.ingsw.galaxytrucker.JsonParsing;
import it.polimi.ingsw.galaxytrucker.Model.Cards.Deck;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.BuildingState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.GameState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.WaitingState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.CabinTile;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.TilePile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Game Class unite different Classes,such as Players,GameDeck,TilePile,GameState,Hourglass
 */
public class Game implements Serializable, Observable {
    private final List<Observer> observers = new ArrayList<>();
    private final List<Player> listOfActivePlayers = new ArrayList<>();
    private final List<Player> listOfPlayers = new ArrayList<>();
    private int numberOfPlayers = -1;
    private GameState gameState = new WaitingState(this);
    private Hourglass hourglass = new Hourglass(new BuildingState(this));
    private TilePile tilePile;  
    private Deck deck;
    private final int lapLenght = 24;
    private List<CabinTile> mainCabins = new ArrayList<>();

    /**
     * Constructor of the Game creates Deck,Cards
     */
    public Game (){
        JsonParsing jsonTile = new JsonParsing();
        tilePile = new TilePile(jsonTile.getCompleteList()); // shuffle is now automated in TilePile class
        mainCabins = jsonTile.getMainCabins();
        JsonCardParsing jsonCardParsing = new JsonCardParsing();
        deck = new Deck(jsonCardParsing.getCompleteListLevel1(), jsonCardParsing.getCompleteListLevel2());
    }

    /**
     * This function returns main Cabins
     * @return List<CabinTile> getMainCabins
     */
    public List<CabinTile> getMainCabins() {
        return mainCabins;
    }

    /**
     * This function return a List of players that were connected once to the Game
     * @return List<Player>
     */
    public List<Player> getListOfPlayers() {
        return listOfPlayers;
    }

    /**
     * This function returns a List of Players that are online
     * @return List<Player>
     */
    public List<Player> getListOfActivePlayers() {
        return listOfActivePlayers;
    }

    /**
     * This function updates A list of Active Players
     */
    public void updateListOfActivePlayers() {
        synchronized (this.listOfActivePlayers) {
            List<Player> list = listOfPlayers.stream().filter(p -> p.getShip().getTravelDays() != null && p.getOnlineStatus()).toList();
            this.listOfActivePlayers.clear();
            this.listOfActivePlayers.addAll(list);
        }
    }

    /**
     * this function adds Player to a list of Players,and in the list of Active Players
     * @param newPlayer
     */
    public void addPlayer(Player newPlayer){
        listOfPlayers.add(newPlayer);
        listOfActivePlayers.add(newPlayer);
        notifyObservers(this, "game");
    }

    /**
     * This function return the number of players
     * @return int
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * This fuction sets the number of players
     * @param numberOfPlayers
     */
    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    /**
     * This function sort the list of active Players by Travel Days
     */
    public void sortListOfActivePlayers(){
        synchronized (listOfActivePlayers) {
            if(listOfActivePlayers.isEmpty()){
                return;
            }
            listOfActivePlayers.sort((p1, p2) -> p2.getShip().getTravelDays() - p1.getShip().getTravelDays());
        }
    }

    /**
     * This function removes Player from Active List
     * @param player
     */
    public void removePlayerFromActiveList(Player player){
        if(listOfActivePlayers.contains(player)){
            listOfActivePlayers.remove(player);
        }
    }

    /**
     * This function returns actual GameState
     * @return GameState
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * This function sets a new Game State of Game
     * @param gameState
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * This function return a Hourglass
     * @return Hourglass
     */
    public Hourglass getHourglass() {
        return hourglass;
    }

    /**
     * This function return a TilePile
     * @return TilePile
     */
    public TilePile getTilePile() {
        return tilePile;
    }

    /**
     * This function return Deck
     * @return Deck
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * This function returns a size of lapLength of the Board
     * @return int
     */
    public int getLapLenght() {
        return lapLenght;
    }

    /**
     * This function sets hourgllas Parameter of Game
     * @param hourglass
     */
    public void setHourglass(Hourglass hourglass) {
        this.hourglass = hourglass;
    }

    /**
     * This function adds observer to the Game
     * @param observer
     */
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * This function removes Observer from Game
     * @param observer
     */
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * This function notify observers of any change
     * @param game
     * @param message
     */
    @Override
    public void notifyObservers(Game game, String message) {
        for (Observer observer : observers) {
            observer.update(game, message);
        }
    }

}
