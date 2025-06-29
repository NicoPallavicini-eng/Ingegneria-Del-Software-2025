package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;

/**
 * This event is triggered when a player chooses no.
 * It contains the player who made the choice.
 */
public record NoChoiceEvent(Player player) implements GameEvent, Serializable {
}
