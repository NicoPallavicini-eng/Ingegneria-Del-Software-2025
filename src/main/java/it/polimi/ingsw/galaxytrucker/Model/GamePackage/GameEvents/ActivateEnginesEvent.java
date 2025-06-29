package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;
import java.util.List;

/**
 * This event is triggered when a player activates their engines.
 * It contains the player who activated the engines, the list of engines used, saved as a list of coordinates,
 * and the list of batteries used for activation, saved as a list of coordinates and the number of batteries used.
 */
public record ActivateEnginesEvent(Player player, List<List<Integer>> engines, List<List<Integer>> batteries) implements GameEvent, Serializable {
}
