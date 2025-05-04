package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;

public record ConnectEvent(Game game, String nickname, String IP) implements GameEvent {
}
