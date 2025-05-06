package it.polimi.ingsw.galaxytrucker.Model;

import it.polimi.ingsw.galaxytrucker.Controller.ViewObserver.Listener;
import jdk.jfr.Event;

import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private List<Listener> listeners = new ArrayList <>();

    public void subscribe(Listener listener) {}

    public void unsubscribe(Listener listener) {}

    public void notify(Event event) {} // Instance of (?)
}
