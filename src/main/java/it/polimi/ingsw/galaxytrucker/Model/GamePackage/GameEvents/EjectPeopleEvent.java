package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;
import java.util.List;

/**
 * This event is triggered when a player ejects people from their ship.
 * It contains the player who performed the action and a list of 2 coordinates and the numer of ejected people.
 */
public record EjectPeopleEvent(Player player, List<List<Integer>> people) implements GameEvent, Serializable {
}
