package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;

/**
 * This event is triggered when a player activates their shield.
 * It contains the player who activated the shield, the coordinates of the shield,
 * and the coordinates of the battery used for activation.
 */
public record ActivateShieldEvent(Player player, int shieldRow, int shieldCol, int batteryRow, int batteryCol) implements GameEvent, Serializable {
}