package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGameStates;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualCards.VirtualEpidemicCard;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGame;

import java.util.ArrayList;


public class VirtualEpidemicState extends VirtualTravellingState {
    private ArrayList<Thread> threads;

    public VirtualEpidemicState(VirtualGame game, VirtualEpidemicCard card) {
        super(game, card);
    }

    public ArrayList<Thread> getThreads() {
        return threads;
    }
}
