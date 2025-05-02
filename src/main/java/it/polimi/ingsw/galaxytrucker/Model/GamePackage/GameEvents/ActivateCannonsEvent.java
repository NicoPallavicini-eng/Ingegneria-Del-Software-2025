package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

//maybe change format to aggregate batteries
public record ActivateCannonsEvent(Player player, List<List<Integer>> cannons, List<List<Integer>> batteries) implements GameEvent {
}
