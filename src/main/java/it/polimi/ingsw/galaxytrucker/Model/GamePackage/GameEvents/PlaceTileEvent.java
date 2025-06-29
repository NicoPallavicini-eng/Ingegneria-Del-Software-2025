package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;

/**
 * This event is triggered when a player places a tile on their ship.
 * @param player
 * @param row
 * @param col
 */
public record PlaceTileEvent(Player player, int row, int col) implements GameEvent, Serializable {
}
