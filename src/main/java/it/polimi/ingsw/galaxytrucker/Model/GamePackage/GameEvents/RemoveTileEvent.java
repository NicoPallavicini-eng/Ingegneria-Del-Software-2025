package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;

public record RemoveTileEvent(Player player, int row, int col) implements GameEvent, Serializable {
}
