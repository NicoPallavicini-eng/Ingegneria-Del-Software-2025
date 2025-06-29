package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;

/**
 * This event is triggered when a player picks up a component from their ship.
 * It contains the player who performed the action.
 */
public record PickUpFromShipEvent(Player player) implements GameEvent, Serializable {
}
