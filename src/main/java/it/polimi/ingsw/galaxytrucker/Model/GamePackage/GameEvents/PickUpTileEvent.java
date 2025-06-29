package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;

/**
 * This event is triggered when a player picks up a tile from the pile.
 * It contains the player who picked up the tile and the index of the tile.
 */
public record PickUpTileEvent(Player player, int index) implements GameEvent, Serializable {
}
