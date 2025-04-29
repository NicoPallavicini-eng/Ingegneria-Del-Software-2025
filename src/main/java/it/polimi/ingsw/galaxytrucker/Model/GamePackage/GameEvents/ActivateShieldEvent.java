package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import javafx.util.Pair;

public record ActivateShieldEvent(int shieldRow, int shieldCol, int batteryRow, int batteryCol) implements GameEvent {
}
