package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.util.ArrayList;

// each list is made of row, col, number of people
public record EjectPeopleEvent(Player player, ArrayList<ArrayList<Integer>> people) implements GameEvent {
}
