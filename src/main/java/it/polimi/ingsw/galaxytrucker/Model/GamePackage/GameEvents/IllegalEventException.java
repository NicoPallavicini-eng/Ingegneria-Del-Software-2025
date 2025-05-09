package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import java.io.Serializable;

public class IllegalEventException extends RuntimeException implements Serializable {
    public IllegalEventException(String message) {
        super(message);
    }
}
