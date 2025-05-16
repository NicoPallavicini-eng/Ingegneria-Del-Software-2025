package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.*;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;

import java.io.Serializable;

public class TravellingStateFactory implements Serializable {

    public static GameState createGameState(Game game, CombatZoneCardL card){
        return new CombatZoneLState(game, card);
    }

    public static GameState createGameState(Game game, CombatZoneCardNotL card){
        return new CombatZoneNotLState(game, card);
    }

    public static GameState createGameState(Game game, EpidemicCard card){
        return new EpidemicState(game, card);
    }

    public static GameState createGameState(Game game, MeteorsCard card){
        return new MeteorsState(game, card);
    }

    public static GameState createGameState(Game game, OpenSpaceCard card){
        return new OpenSpaceState(game, card);
    }

    public static GameState createGameState(Game game, PiratesCard card){
        return new PiratesState(game, card);
    }

    public static GameState createGameState(Game game, PlanetsCard card){
        return new PlanetsState(game, card);
    }

    public static GameState createGameState(Game game, ShipCard card){
        return new ShipState(game, card);
    }

    public static GameState createGameState(Game game, SlaversCard card){
        return new SlaversState(game, card);
    }

    public static GameState createGameState(Game game, SmugglersCard card){
        return new SmugglersState(game, card);
    }

    public static GameState createGameState(Game game, StardustCard card){
        return new StardustState(game, card);
    }

    public static GameState createGameState(Game game, StationCard card){
        return new StationState(game, card);
    }

    // shall never be called, here just for overloads, maybe put exception
    public static GameState createGameState(Game game, Card card){
        return null;
    }

}
