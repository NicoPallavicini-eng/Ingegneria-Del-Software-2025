package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;

public record ConnectEvent(String nickname, String IP) implements GameEvent {
}
