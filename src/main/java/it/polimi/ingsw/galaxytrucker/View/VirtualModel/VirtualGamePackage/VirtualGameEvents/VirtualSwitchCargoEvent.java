package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGameEvents;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualPlayerShip.VirtualPlayer;

public record VirtualSwitchCargoEvent(VirtualPlayer player, int prevRow, int prevCol, int nextRow, int nextCol, Integer resource) implements VirtualGameEvent {
}
