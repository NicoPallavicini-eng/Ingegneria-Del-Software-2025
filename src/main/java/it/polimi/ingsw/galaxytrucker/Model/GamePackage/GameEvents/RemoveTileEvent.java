package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;

/**
 * This event is triggered when a player removes a tile from their ship.
 * It contains the player who performed the action, as well as the row and column of the tile being removed.
 */
public record RemoveTileEvent(Player player, int row, int col) implements GameEvent, Serializable {
}
