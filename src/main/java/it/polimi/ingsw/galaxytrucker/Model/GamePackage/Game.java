package it.polimi.ingsw.galaxytrucker.Model.GamePackage;

import it.polimi.ingsw.galaxytrucker.JsonCardParsing;
import it.polimi.ingsw.galaxytrucker.JsonParsing;
import it.polimi.ingsw.galaxytrucker.Model.Cards.Deck;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.GameState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.WaitingState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.CabinTile;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.TilePile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Game implements Serializable {
    private final List<Player> listOfActivePlayers = new ArrayList<>();
    private final List<Player> listOfPlayers = new ArrayList<>();
    private int numberOfPlayers = -1;
    private GameState gameState = new WaitingState(this);
    private Hourglass hourglass;
    private TilePile tilePile;  
    private Deck deck;
    private final int lapLenght = 24;
    private List<CabinTile> mainCabins = new ArrayList<>();

    public Game (){
        JsonParsing jsonTile = new JsonParsing();
        tilePile = new TilePile(jsonTile.getCompleteList()); // shuffle is now automated in TilePile class
        mainCabins = jsonTile.getMainCabins();
        JsonCardParsing jsonCardParsing = new JsonCardParsing();
        deck = new Deck(jsonCardParsing.getCompleteListLevel1(), jsonCardParsing.getCompleteListLevel2());
    }

    public List<CabinTile> getMainCabins() {
        return mainCabins;
    }

    public List<Player> getListOfPlayers() {
        return listOfPlayers;
    }

    public List<Player> getListOfActivePlayers() {
        return listOfActivePlayers;
    }

    public void updateListOfActivePlayers() {

        List<Player> list = listOfPlayers.stream().filter(p -> p.getShip().getTravelDays() != null && p.getOnlineStatus()).collect(Collectors.toList());
        listOfActivePlayers.clear();
        listOfActivePlayers.addAll(list);
    }

    public void addPlayer(Player newPlayer){
        listOfPlayers.add(newPlayer);
        listOfActivePlayers.add(newPlayer);
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public void sortListOfActivePlayers(){
        listOfActivePlayers.sort((p1, p2) -> p1.getShip().getTravelDays() - p2.getShip().getTravelDays());
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState( GameState gameState) {
        this.gameState = gameState;
    }

    public Hourglass getHourglass() {
        return hourglass;
    }

    public TilePile getTilePile() {
        return tilePile;
    }

    public Deck getDeck() {
        return deck;
    }

    public int getLapLenght() {
        return lapLenght;
    }

    public void setHourglass(Hourglass hourglass) {
        this.hourglass = hourglass;
    }
}
