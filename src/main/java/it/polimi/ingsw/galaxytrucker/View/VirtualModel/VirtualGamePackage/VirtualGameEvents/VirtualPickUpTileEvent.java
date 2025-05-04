package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGameEvents;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualPlayerShip.VirtualPlayer;

public record VirtualPickUpTileEvent(VirtualPlayer player, int index) implements VirtualGameEvent {
}
