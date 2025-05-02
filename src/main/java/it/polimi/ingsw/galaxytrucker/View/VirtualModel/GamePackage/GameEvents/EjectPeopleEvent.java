package it.polimi.ingsw.galaxytrucker.View.VirtualModel.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.PlayerShip.Player;

import java.util.List;

// each list is made of row, col, number of people
public record EjectPeopleEvent(Player player, List<List<Integer>> people) implements GameEvent {
}
