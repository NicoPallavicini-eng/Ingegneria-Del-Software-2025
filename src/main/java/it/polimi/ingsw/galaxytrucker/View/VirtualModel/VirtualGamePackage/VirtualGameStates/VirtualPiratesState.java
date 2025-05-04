package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGameStates;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualCards.VirtualPiratesCard;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGame;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualPlayerShip.VirtualPlayer;

import java.util.List;

public class VirtualPiratesState extends VirtualTravellingState {
    private VirtualPiratesCard currentCard;
    private VirtualPlayer piratesSlayer;
    private List<VirtualPlayer> defeatedPlayers;
    private boolean reckoningPhase = false;

    public VirtualPiratesState(VirtualGame game, VirtualPiratesCard card) {
        super(game, card);
    }

    public boolean isReckoningPhase() {
        return reckoningPhase;
    }

    public VirtualPlayer getPiratesSlayer() {
        return piratesSlayer;
    }

    public List<VirtualPlayer> getDefeatedPlayers() {
        return defeatedPlayers;
    }
}
