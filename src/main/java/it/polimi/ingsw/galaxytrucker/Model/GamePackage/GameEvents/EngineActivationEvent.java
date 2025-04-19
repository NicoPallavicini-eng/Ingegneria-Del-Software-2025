package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import javafx.util.Pair;

import java.util.ArrayList;

public record EngineActivationEvent(Player player, /* int gameID,*/ArrayList<Pair<Integer,Integer>> coordinates) {
}
