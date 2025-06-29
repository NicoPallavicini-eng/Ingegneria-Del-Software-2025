package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;

/**
 * This event is triggered when a player views a specific deck in the game.
 * It contains the player who viewed the deck and the index of the deck.
 */
public record ViewDeckEvent(Player player, int deckIndex) implements GameEvent, Serializable {
}
