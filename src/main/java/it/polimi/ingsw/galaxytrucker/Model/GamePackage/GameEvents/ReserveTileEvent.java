package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;

/**
 * This event is triggered when a player reserves a tile from their hand.
 * It contains the player who reserved the tile.
 * @param player The player who reserved the tile.
 */
public record ReserveTileEvent(Player player) implements GameEvent, Serializable {
}
