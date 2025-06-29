package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;

/* * This event is triggered when a player chooses a subship to use after their ship breaks.
 * It contains the player who made the choice and the coordinates of one of the subship's tiles.
 */
public record ChooseSubShipEvent(Player player, /*int SubshipIndex*/ int row, int col) implements GameEvent, Serializable {
}
