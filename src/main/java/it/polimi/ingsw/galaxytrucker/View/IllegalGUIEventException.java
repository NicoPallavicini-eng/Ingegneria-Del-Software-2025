package it.polimi.ingsw.galaxytrucker.View;
/**
 * The IllegalGUIEventException class represents a custom runtime exception
 * that is thrown when an illegal or invalid event occurs in the GUI.
 */
public class IllegalGUIEventException extends RuntimeException {
    /**
     * Constructs a new IllegalGUIEventException with the specified detail message.
     *
     * @param message The detail message explaining the reason for the exception.
     */
    public IllegalGUIEventException(String message) {
        super(message);
    }
}
