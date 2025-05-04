package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGameEvents;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualPlayerShip.VirtualPlayer;

public record VirtualClaimRewardEvent(VirtualPlayer player, boolean engages) implements VirtualGameEvent {
}
