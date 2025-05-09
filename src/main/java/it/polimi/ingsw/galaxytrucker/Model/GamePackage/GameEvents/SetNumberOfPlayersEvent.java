package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import java.io.Serializable;

public record SetNumberOfPlayersEvent(int numberOfPlayers) implements GameEvent, Serializable {
}
