package it.polimi.ingsw.galaxytrucker.Model.Tiles;

import java.io.Serializable;

public class InhabitantsException extends RuntimeException implements Serializable {
    public InhabitantsException(String message) {
        super(message);
    }
}
