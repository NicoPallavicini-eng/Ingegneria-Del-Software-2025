package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.util.ArrayList;
import java.util.List;

// each list is made of row, col, number of people
public record EjectPeopleEvent(Player player, List<List<Integer>> people) implements GameEvent {
}
