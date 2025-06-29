package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;

/**
 * This event is triggered when a player rotates a tile on their ship.
 * @param player The player who is rotating the tile.
 * @param right Indicates the direction of rotation (e.g., "right" or "left").
 */
public record RotateTileEvent(Player player, String right) implements GameEvent, Serializable {
}
