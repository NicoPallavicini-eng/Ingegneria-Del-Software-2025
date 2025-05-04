package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGameEvents;

public record VirtualConnectEvent(String nickname, String IP) implements VirtualGameEvent {
}
