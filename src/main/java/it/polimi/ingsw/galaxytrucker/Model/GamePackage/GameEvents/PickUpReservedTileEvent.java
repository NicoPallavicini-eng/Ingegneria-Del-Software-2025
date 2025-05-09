package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;

public record PickUpReservedTileEvent(Player player, int index) implements GameEvent, Serializable {
}
