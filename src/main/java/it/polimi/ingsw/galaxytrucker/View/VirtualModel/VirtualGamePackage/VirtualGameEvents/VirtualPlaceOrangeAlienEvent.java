package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGameEvents;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualPlayerShip.VirtualPlayer;

public record VirtualPlaceOrangeAlienEvent(VirtualPlayer player, int row, int col) implements VirtualGameEvent {
}
