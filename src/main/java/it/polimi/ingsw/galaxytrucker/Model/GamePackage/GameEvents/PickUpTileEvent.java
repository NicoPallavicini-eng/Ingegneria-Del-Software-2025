package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

public record PickUpTileEvent(Player player, int row, int column) implements GameEvent {
}
