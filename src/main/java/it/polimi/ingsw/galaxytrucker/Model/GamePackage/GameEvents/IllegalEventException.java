package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import java.io.Serializable;

/**
 * This exception is thrown when an illegal event is encountered in the game.
 * It extends RuntimeException and implements Serializable for serialization purposes.
 */
public class IllegalEventException extends RuntimeException implements Serializable {
    public IllegalEventException(String message) {
        super(message);
    }
}
