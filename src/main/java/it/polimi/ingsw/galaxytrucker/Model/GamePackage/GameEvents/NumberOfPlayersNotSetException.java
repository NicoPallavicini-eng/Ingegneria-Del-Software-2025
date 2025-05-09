package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import java.io.Serializable;

public class NumberOfPlayersNotSetException extends RuntimeException implements Serializable {
    public NumberOfPlayersNotSetException(String message) {
        super(message);
    }
}
