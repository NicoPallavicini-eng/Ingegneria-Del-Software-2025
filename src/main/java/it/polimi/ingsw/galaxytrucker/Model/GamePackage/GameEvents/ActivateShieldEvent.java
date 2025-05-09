package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;

public record ActivateShieldEvent(Player player, int shieldRow, int shieldCol, int batteryRow, int batteryCol) implements GameEvent, Serializable {
}