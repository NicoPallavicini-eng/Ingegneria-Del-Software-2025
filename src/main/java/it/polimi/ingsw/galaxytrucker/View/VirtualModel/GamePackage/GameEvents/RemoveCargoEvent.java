package it.polimi.ingsw.galaxytrucker.View.VirtualModel.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.PlayerShip.Player;

public record RemoveCargoEvent(Player player, int row, int col, Integer resource) implements GameEvent {
}
