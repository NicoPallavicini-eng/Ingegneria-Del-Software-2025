package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGameStates;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGame;

public class VirtualWaitingState extends VirtualGameState {
    private final VirtualGame game;
    public VirtualWaitingState(VirtualGame game ) {
        this.game = game;
    }

    public VirtualGame getGame() {
        return game;
    }
}
