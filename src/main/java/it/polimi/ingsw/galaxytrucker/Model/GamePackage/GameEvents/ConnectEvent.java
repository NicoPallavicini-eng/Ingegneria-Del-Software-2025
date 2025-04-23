package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

public record ConnectEvent(String nickname, String IP) implements GameEvent {
}
