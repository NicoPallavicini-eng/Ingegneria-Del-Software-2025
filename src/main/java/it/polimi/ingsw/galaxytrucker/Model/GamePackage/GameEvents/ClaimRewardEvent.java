package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;

public record ClaimRewardEvent(Player player, boolean engages) implements GameEvent, Serializable {
}
