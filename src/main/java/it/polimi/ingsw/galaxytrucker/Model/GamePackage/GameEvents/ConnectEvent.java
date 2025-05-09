package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;

import java.io.Serializable;

public record ConnectEvent(String nickname, String IP) implements GameEvent, Serializable {
}
