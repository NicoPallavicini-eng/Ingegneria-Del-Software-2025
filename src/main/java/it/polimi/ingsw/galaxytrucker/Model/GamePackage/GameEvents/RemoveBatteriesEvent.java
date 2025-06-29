package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.util.List;

/**
 * This event is triggered when a player removes batteries from their ship.
 * It contains the player who performed the action and a list of battery coordinates and amount that were removed.
 */
public record RemoveBatteriesEvent(Player player, List<Integer> batteries) implements GameEvent {
}
