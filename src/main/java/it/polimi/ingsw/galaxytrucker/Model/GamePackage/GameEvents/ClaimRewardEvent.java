package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;

/**
 * This event is triggered when a player claims a reward.
 * It contains the player who claimed the reward and a boolean indicating whether the player engages in the reward.
 */
public record ClaimRewardEvent(Player player, boolean engages) implements GameEvent, Serializable {
}
