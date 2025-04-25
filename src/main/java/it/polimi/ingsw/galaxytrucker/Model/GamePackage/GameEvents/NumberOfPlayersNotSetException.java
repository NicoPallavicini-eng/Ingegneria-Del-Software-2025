package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

public class NumberOfPlayersNotSetException extends RuntimeException {
    public NumberOfPlayersNotSetException(String message) {
        super(message);
    }
}
