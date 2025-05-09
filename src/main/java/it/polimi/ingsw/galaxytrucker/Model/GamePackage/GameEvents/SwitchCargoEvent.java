package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;

public record SwitchCargoEvent(Player player, int prevRow, int prevCol, int nextRow, int nextCol, Integer resource) implements GameEvent, Serializable {
}
