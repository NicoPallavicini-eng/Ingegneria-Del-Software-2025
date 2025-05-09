package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;

public record RemoveCargoEvent(Player player, int row, int col, Integer resource) implements GameEvent, Serializable {
}
