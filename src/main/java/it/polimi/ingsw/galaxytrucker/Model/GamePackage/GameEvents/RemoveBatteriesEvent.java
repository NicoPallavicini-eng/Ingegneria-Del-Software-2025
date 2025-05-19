package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.util.List;

public record RemoveBatteriesEvent(Player player, List<List<Integer>> batteries) implements GameEvent {
}
