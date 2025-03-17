package it.polimi.ingsw.galaxytrucker.Model;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Card;
import it.polimi.ingsw.galaxytrucker.Model.Cards.Deck;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.TilePile;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final List<Player> listOfPlayers = new ArrayList<>();
    private Card currentCard;
    private int numberOfPlayers;
    private GamePhase gamePhase = WAITING_ROOM;
    private final Hourglass hourglass = new Hourglass();
    private final TilePile tilePile = new TilePile();
    private final Deck deck = new Deck();

    public Game (Player firstPlayer){
        ListOfPlayers.add(firstPlayer);
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

    public Card getCurrentCard() {
        return currentCard;
    }

    public void setCurrentCard(Card currentCard) {
        this.currentCard = currentCard;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public GamePhase getGamePhase() {
        return gamePhase;
    }

    public void setGamePhase() {
        this.gamePhase = gamePhase;
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
}



