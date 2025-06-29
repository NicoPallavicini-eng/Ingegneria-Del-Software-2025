package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import java.io.Serializable;

/**
 * This event is triggered when a player flips the hourglass.
 */
public record FlipHourglassEvent() implements GameEvent, Serializable {
}
