package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.util.List;

//maybe change format to aggregate batteries
public record ActivateEnginesEvent(Player player, List<List<Integer>> engines, List<List<Integer>> batteries) implements GameEvent {
}
