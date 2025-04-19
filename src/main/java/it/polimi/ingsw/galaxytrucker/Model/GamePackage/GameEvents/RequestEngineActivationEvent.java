package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

public record RequestEngineActivationEvent(Player player/*, int gameID*/) implements GameEvent{
}
