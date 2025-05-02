package it.polimi.ingsw.galaxytrucker.View.VirtualModel.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.PlayerShip.Player;

public record ClaimRewardEvent(Player player, boolean engages) implements GameEvent {
}
