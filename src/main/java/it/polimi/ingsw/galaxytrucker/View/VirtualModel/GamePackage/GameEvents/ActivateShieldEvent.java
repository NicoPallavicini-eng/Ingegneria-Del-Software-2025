package it.polimi.ingsw.galaxytrucker.View.VirtualModel.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.PlayerShip.Player;

public record ActivateShieldEvent(Player player, int shieldRow, int shieldCol, int batteryRow, int batteryCol) implements GameEvent {
}