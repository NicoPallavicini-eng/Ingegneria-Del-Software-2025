package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import java.io.Serializable;

/**
 * This event is triggered when a player connects to the game.
 * It contains the player's nickname and their IP address.
 */
public record ConnectEvent(String nickname, String IP) implements GameEvent, Serializable {
}
