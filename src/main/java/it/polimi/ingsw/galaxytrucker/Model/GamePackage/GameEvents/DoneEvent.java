package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;

/**
 * This event is triggered when a player indicates they are done with their choice.
 * @param player
 */
public record DoneEvent(Player player) implements GameEvent, Serializable {
}
