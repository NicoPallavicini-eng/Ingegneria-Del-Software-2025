package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;

/**
 * This event is triggered when a player places an orange alien on the board.
 * It contains the player who placed the alien and the coordinates (row, col) where the alien is placed.
 */
public record PlaceOrangeAlienEvent(Player player, int row, int col) implements GameEvent, Serializable {
}
