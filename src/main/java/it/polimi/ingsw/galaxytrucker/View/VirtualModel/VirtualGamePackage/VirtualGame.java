package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualCards.VirtualDeck;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGameStates.VirtualGameState;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGameStates.VirtualWaitingState;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualPlayerShip.VirtualPlayer;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualTiles.VirtualTilePile;

import java.util.ArrayList;
import java.util.List;

public class VirtualGame {
    private final List<VirtualPlayer> listOfPlayers = new ArrayList<>();
    private int numberOfPlayers;
    private VirtualGameState gameState = new VirtualWaitingState(this);
    private final VirtualHourglass hourglass = new VirtualHourglass();
    private VirtualTilePile tilePile;
    private VirtualDeck deck;

    public VirtualGame() {}

    public List<VirtualPlayer> getListOfPlayers() {
        return listOfPlayers;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public VirtualGameState getGameState() {
        return gameState;
    }

    public VirtualHourglass getHourglass() {
        return hourglass;
    }

    public VirtualTilePile getTilePile() {
        return tilePile;
    }

    public VirtualDeck getDeck() {
        return deck;
    }
}
