package it.polimi.ingsw.galaxytrucker.View.VirtualModel.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.PlayerShip.Player;

public record SwitchCargoEvent(Player player, int prevRow, int prevCol, int nextRow, int nextCol, Integer resource) implements GameEvent {
}
