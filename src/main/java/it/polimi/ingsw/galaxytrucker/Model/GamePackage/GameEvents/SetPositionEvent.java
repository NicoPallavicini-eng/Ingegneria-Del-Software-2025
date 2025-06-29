package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;

/**
 * This event is triggered when a player sets their position after finishing building.
 * It contains the player who performed the action and their new position.
 */
public record SetPositionEvent(Player player, int position) implements GameEvent, Serializable {
}
