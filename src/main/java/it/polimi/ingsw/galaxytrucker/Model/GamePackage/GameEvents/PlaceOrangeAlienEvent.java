package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;

public record PlaceOrangeAlienEvent(Player player, int row, int col) implements GameEvent, Serializable {
}
