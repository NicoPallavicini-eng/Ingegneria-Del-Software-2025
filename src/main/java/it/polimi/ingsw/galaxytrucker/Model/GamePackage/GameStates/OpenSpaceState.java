package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.OpenSpaceCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.EngineActivationEvent;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.GameEvent;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.RequestEngineActivationEvent;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.EngineTile;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;
import javafx.util.Pair;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;

public class OpenSpaceState extends TravellingState{

    private LinkedList<Player> turns;
    public OpenSpaceState(Game game, OpenSpaceCard card) {
        super(game, card);
    }



    @Override
    public void process() {
        if(handledPlayers == turns.size()) {
            next();
            game.getGameState().init();}
        else {
            if (handledPlayers == 0) {
                turns = new LinkedList<>(game.getListOfPlayers());
                Collections.reverse(turns);
            }
            game.addEventQueue(new RequestEngineActivationEvent(turns.get(handledPlayers)));
            handledPlayers++;
        }
    }

    //a different type of event uses the method handlevent(GameEvent) of the superclass
    //now activates all tiles that are engines but does not say you picked row,column where engines are not present
    public void handleInput(EngineActivationEvent event)throws IllegalEventException {

            event.coordinates().stream()
                    .map(c -> event.player().getShip().getTileOnFloorPlan(c.getKey(), c.getValue()))
                    .flatMap(Optional::stream)
                    .filter(t -> t instanceof EngineTile)
                    .map(t -> (EngineTile) t)
                    .forEach(e -> e.setActiveState(true));


            //needed a lot of logic for overtaking
            int gainedDays = event.player().getShip().getEnginePower();
            int position = event.player().getShip().getTravelDays();
            for(int i=0; i < gainedDays; i++) {
                position++;
                int finalPosition = position;//needed for stream
                while(game.getListOfPlayers().stream()
                        .map(p -> p.getShip().getTravelDays())
                        .anyMatch(pos -> pos == finalPosition)){
                    position++;
                    int finalPosition2 = position;
                }
            }
            event.player().getShip().setTravelDays(position);
            process();
    }
}
