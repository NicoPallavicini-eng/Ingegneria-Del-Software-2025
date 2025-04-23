package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

public record SetNumberOfPlayersEvent(int numberOfPlayers) implements GameEvent {
}
