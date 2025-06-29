package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;

/**
 * This event is triggered when a player switches cargo between two positions on their ship.
 * It contains the player who performed the action, the previous and next positions of the cargo,
 * and the resource being switched.
 *
 * @param player    The player who switched the cargo.
 * @param prevRow   The row of the previous position of the cargo.
 * @param prevCol   The column of the previous position of the cargo.
 * @param nextRow   The row of the next position of the cargo.
 * @param nextCol   The column of the next position of the cargo.
 * @param resource  The resource being switched, or null if no resource is involved.
 */
public record SwitchCargoEvent(Player player, int prevRow, int prevCol, int nextRow, int nextCol, Integer resource) implements GameEvent, Serializable {
}
