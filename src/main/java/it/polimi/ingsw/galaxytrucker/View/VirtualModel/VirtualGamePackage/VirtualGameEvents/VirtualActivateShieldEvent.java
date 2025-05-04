package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGameEvents;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualPlayerShip.VirtualPlayer;

public record VirtualActivateShieldEvent(VirtualPlayer player, int shieldRow, int shieldCol, int batteryRow, int batteryCol) implements VirtualGameEvent {
}