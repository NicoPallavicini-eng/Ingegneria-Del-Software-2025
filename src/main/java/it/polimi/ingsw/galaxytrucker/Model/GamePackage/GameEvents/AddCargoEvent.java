package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;

/**
 * This event is triggered when a player adds cargo to their ship.
 * It contains the player who added the cargo, the row and column where the cargo was added,
 * and the resource type of the cargo.
 */
public record AddCargoEvent(Player player, int row, int column, Integer resource) implements GameEvent, Serializable {
}
