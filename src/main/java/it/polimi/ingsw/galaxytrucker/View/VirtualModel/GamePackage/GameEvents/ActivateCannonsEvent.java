package it.polimi.ingsw.galaxytrucker.View.VirtualModel.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.PlayerShip.Player;

import java.util.List;

//maybe change format to aggregate batteries
public record ActivateCannonsEvent(Player player, List<List<Integer>> cannons, List<List<Integer>> batteries) implements GameEvent {
}
