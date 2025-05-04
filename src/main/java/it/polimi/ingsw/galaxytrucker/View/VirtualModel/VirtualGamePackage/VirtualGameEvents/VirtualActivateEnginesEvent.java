package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGameEvents;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualPlayerShip.VirtualPlayer;

import java.util.List;

//maybe change format to aggregate batteries
public record VirtualActivateEnginesEvent(VirtualPlayer player, List<List<Integer>> engines, List<List<Integer>> batteries) implements VirtualGameEvent {
}
