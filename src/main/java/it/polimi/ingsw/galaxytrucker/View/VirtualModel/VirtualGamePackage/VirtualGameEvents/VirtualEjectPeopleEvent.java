package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGameEvents;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualPlayerShip.VirtualPlayer;

import java.util.List;

// each list is made of row, col, number of people
public record VirtualEjectPeopleEvent(VirtualPlayer player, List<List<Integer>> people) implements VirtualGameEvent {
}
