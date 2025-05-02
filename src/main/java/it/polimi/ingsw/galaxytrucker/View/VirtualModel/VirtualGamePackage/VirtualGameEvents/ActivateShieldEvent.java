package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGameEvents;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualPlayerShip.Player;

public record ActivateShieldEvent(Player player, int shieldRow, int shieldCol, int batteryRow, int batteryCol) implements GameEvent {
}