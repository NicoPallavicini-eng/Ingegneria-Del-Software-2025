package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import javafx.util.Pair;

import java.util.ArrayList;

//maybe change format to aggregate batteries
public record ActivateCannonsEvent(Player player, ArrayList<Pair<Integer, Integer>> cannons, ArrayList<Pair<Integer, Integer>> batteries) implements GameEvent {
}
