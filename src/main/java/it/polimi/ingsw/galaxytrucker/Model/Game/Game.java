package it.polimi.ingsw.galaxytrucker.Model.Game;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Deck;
import it.polimi.ingsw.galaxytrucker.Model.Player;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.TilePile;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final List<Player> listOfPlayers = new ArrayList<>();
    private int numberOfPlayers;
    private GameState gameState = new WaitingState(this);
    private final Hourglass hourglass = new Hourglass();
    private TilePile tilePile;
    private Deck deck;

    public Game (Player firstPlayer){
        listOfPlayers.add(firstPlayer);
        // TODO assemble gameDeck (?)
    }

    public List<Player> getListOfPlayers() {
        return listOfPlayers;
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

    public GameState getGameState() {
        return gameState;
    }

    public void nextGameState() {
        this.gameState = gameState.next();
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
}
