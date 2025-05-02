package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGameEvents;

public record ConnectEvent(String nickname, String IP) implements GameEvent {
}
