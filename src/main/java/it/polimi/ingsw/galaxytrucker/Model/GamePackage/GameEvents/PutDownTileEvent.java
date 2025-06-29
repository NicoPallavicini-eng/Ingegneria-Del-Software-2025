package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;

/**
 * This event is triggered when a player puts down a tile on the pile.
 * It contains the player who performed the action.
 */
public record PutDownTileEvent(Player player) implements GameEvent, Serializable {
}
