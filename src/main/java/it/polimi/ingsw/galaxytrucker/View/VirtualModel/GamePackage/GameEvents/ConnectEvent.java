package it.polimi.ingsw.galaxytrucker.View.VirtualModel.GamePackage.GameEvents;

public record ConnectEvent(String nickname, String IP) implements GameEvent {
}
