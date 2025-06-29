package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;

/**
 * This event is triggered when a player disconnects from the game.
 * It contains the player who disconnected.
 */
public record DisconnectEvent(Player player) implements GameEvent, Serializable {
}
