package it.polimi.ingsw.galaxytrucker.View.VirtualModel.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.Cards.*;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.GamePackage.Game;

public class TravellingStateFactory {

    public static GameState createGameState(Game game, CombatZoneCard card){
        return new CombatZoneState(game, card);
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
