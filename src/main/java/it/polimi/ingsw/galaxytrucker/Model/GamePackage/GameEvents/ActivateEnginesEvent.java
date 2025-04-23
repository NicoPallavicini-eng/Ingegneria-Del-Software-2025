package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import javafx.util.Pair;

import java.util.ArrayList;

//maybe change format to aggregate batteries
public record ActivateEnginesEvent(Player player, /* int gameID,*/ArrayList<Pair<Integer,Integer>> engines, ArrayList<Pair<Integer,Integer>> batteries) implements GameEvent {
}
