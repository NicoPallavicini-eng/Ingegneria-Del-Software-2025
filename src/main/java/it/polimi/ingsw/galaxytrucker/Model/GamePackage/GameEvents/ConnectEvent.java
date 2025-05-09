package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import java.io.Serializable;

public record ConnectEvent(String nickname, String IP) implements GameEvent, Serializable {
}
