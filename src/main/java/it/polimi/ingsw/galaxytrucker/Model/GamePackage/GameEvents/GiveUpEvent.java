package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;

/*
 * This event is triggered when a player gives up during the game.
 * It contains the player who gave up.
 */
public record GiveUpEvent(Player player) implements GameEvent, Serializable {
}
