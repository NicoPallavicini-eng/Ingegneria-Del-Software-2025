package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGameStates;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualCards.VirtualPlanet;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualCards.VirtualPlanetsCard;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGame;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualPlayerShip.VirtualPlayer;

import java.util.List;
import java.util.Map;

public class VirtualPlanetsState extends VirtualTravellingState {
    private VirtualPlanetsCard currentCard;
    private VirtualPlayer currentPlayer;
    private List<VirtualPlanet> planets;
    private Map<VirtualPlayer, VirtualPlanet> chosenPlanets;
    private List<VirtualPlayer> satisfiedPlayers;
    private boolean cargoLoadingPhase = false;

    public VirtualPlanetsState(VirtualGame game, VirtualPlanetsCard card) {
        super(game, card);
    }

    public List<VirtualPlanet> getPlanets() {
        return planets;
    }

    public Map<VirtualPlayer, VirtualPlanet> getChosenPlanets() {
        return chosenPlanets;
    }

    public List<VirtualPlayer> getSatisfiedPlayers() {
        return satisfiedPlayers;
    }

    public boolean isCargoLoadingPhase() {
        return cargoLoadingPhase;
    }
}
