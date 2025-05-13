package it.polimi.ingsw.galaxytrucker.Model.GamePackage;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Deck;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.GameState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.WaitingState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
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
    private final Hourglass hourglass = new Hourglass();
    private TilePile tilePile;  
    private Deck deck;
    private final int lapLenght = 24;


    public Game (){
        // TODO assemble gameDeck (?)
    }

    public List<Player> getListOfPlayers() {
        return listOfPlayers;
    }

    public List<Player> getListOfActivePlayers() {
        return listOfActivePlayers;
    }

    public void updateListOfActivePlayers() {

        List<Player> list = listOfPlayers.stream().filter(p -> p.getShip().getTravelDays() != null).collect(Collectors.toList());
        listOfActivePlayers.clear();
        listOfActivePlayers.addAll(list);
    }

    public void addPlayer(Player newPlayer){
        listOfPlayers.add(newPlayer);
    }

    public void removePlayer(Player disconnectedPlayer){
        listOfPlayers.remove(disconnectedPlayer);
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public void sortListOfPlayers(){
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

    public void setTilePile(TilePile tilePile) {
        this.tilePile = tilePile;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public int getLapLenght() {
        return lapLenght;
    }
}
