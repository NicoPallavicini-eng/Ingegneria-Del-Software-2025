package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import javafx.util.Pair;

public record ActivateShieldEvent(Pair<Integer,Integer> shield, Pair<Integer,Integer> battery) implements GameEvent {
}
