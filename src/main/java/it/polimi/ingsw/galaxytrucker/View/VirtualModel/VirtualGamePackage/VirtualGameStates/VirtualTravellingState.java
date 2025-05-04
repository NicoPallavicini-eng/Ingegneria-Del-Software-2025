package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGameStates;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualCards.VirtualCard;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGame;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualPlayerShip.VirtualPlayer;

public abstract class VirtualTravellingState extends VirtualGameState {
    protected final VirtualGame game;
    protected final VirtualCard currentCard;
    protected int handledPlayers = 0;
    protected VirtualPlayer currentPlayer;

    public VirtualGame getGame() {
        return game;
    }

    public VirtualCard getCurrentCard() {
        return currentCard;
    }

    public int getHandledPlayers() {
        return handledPlayers;
    }

    public VirtualTravellingState(VirtualGame game, VirtualCard currentCard) {
        this.game = game;
        this.currentCard = currentCard;
    }

    public VirtualPlayer getCurrentPlayer() {
        return currentPlayer;
    }
}