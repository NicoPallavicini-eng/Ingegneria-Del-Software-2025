package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;

/**
 * This event is triggered when a player removes cargo from their ship.
 * It contains the player who performed the action, the row and column of the cargo,
 * and the type of resource being removed.
 */
public record RemoveCargoEvent(Player player, int row, int col, Integer resource) implements GameEvent, Serializable {
}
