package it.polimi.ingsw.galaxytrucker.Model.GamePackage;

import it.polimi.ingsw.galaxytrucker.JsonCardParsing;
import it.polimi.ingsw.galaxytrucker.JsonParsing;
import it.polimi.ingsw.galaxytrucker.Model.Cards.Deck;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.GameState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.WaitingState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.TilePile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Game implements Serializable {
    private final List<Player> listOfPlayers = new ArrayList<>();
    private int numberOfPlayers = -1;
    private GameState gameState = new WaitingState(this);
    private final Hourglass hourglass = new Hourglass();
    private TilePile tilePile;  
    private Deck deck;

    public Game (){
        // TODO assemble gameDeck (?)
        JsonParsing jsonTile = new JsonParsing();
        tilePile = new TilePile(jsonTile.getCompleteList());

        JsonCardParsing jsonCardParsing = new JsonCardParsing();
        deck = new Deck(jsonCardParsing.getCompleteListLevel1(), jsonCardParsing.getCompleteListLevel2());
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

    public void sortListOfPlayers(){
        listOfPlayers.sort((p1, p2) -> p1.getShip().getTravelDays() - p2.getShip().getTravelDays());
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
}
