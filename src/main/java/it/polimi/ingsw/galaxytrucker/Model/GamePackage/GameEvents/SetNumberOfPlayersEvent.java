package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import java.io.Serializable;

/**
 * This event is triggered when the number of players in the game is set.
 * It contains the number of players.
 */
public record SetNumberOfPlayersEvent(int numberOfPlayers) implements GameEvent, Serializable {
}
