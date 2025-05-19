package it.polimi.ingsw.galaxytrucker.Model.GamePackage;

import it.polimi.ingsw.galaxytrucker.Controller.Server.Observer;

public interface Observable {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(Game game);
}
