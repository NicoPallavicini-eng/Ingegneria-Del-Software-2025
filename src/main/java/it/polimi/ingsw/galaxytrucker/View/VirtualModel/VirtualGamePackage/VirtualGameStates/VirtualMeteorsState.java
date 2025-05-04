package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGameStates;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualCards.VirtualMeteor;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualCards.VirtualMeteorsCard;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGame;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualPlayerShip.VirtualPlayer;

import java.util.ArrayList;
import java.util.List;

public class VirtualMeteorsState extends VirtualTravellingState {
    private VirtualMeteorsCard currentCard;
    private ArrayList<VirtualPlayer> handledPlayers;
    private List<VirtualMeteor> meteors;
    private VirtualMeteor currentMeteor;

    public VirtualMeteorsState(VirtualGame game, VirtualMeteorsCard card) {
        super(game, card);
    }

    public List<VirtualMeteor> getMeteors() {
        return meteors;
    }

    public VirtualMeteor getCurrentMeteor() {
        return currentMeteor;
    }
}
