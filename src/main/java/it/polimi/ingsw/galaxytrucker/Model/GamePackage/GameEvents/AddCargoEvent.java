package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

public record AddCargoEvent(Player player, int row, int column, Integer resource) implements GameEvent {
}
