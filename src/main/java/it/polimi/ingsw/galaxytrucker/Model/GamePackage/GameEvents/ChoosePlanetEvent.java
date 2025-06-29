package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;

/**
 * This event is triggered when a player chooses a planet to land on.
 * It contains the player who made the choice and the index of the chosen planet.
 */
public record ChoosePlanetEvent(Player player, int planetIndex) implements GameEvent, Serializable {
}
