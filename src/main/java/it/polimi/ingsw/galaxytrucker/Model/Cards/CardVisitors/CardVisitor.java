package it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors;

import it.polimi.ingsw.galaxytrucker.Model.Cards.*;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.SequentialTravellingState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;

import java.util.List;

public interface CardVisitor {

    // handle cards
    public void handleStationCard(SequentialTravellingState state, StationCard stationCard, Player player);
    public void handleShipCard(SequentialTravellingState state, ShipCard shipCard, Player player);
    public void handleOpenSpaceCard(OpenSpaceCard openSpaceCard, List<Player> players);
    public void handleStardustCard(StardustCard stardustCard, Ship ship);
    public void handleEpidemicCard(EpidemicCard epidemicCard, Ship ship);
    public void handleCombatZoneCardLessCrew(CombatZoneCard combatZoneCard, Ship ship);
    public void handleCombatZoneCardLessEngine(CombatZoneCard combatZoneCard, Ship ship);
    public void handleCombatZoneCardLessFirepower(CombatZoneCard combatZoneCard, Ship ship);
    public void handlePlanetsCard(PlanetsCard planetsCard, List <Player> players);
    public void handleSmugglersCard(SequentialTravellingState state, SmugglersCard smugglersCard, Player player);
    public void handleMeteorsCard(MeteorsCard meteorsCard, Ship ship);
    public void handleSlaversCard(SequentialTravellingState state, SlaversCard slaversCard, Player player);
    public void handlePiratesCard(SequentialTravellingState state, PiratesCard piratesCard, Player player);

    // set next states
    public void setNextStateCombatZoneCard(GameState state, Game game, CombatZoneCard card);
    public void setNextStateEpidemicCard(GameState state, Game game, EpidemicCard card);
    public void setNextStateMeteorsCard(GameState state, Game game, MeteorsCard card);
    public void setNextStateOpenSpaceCard(GameState state, Game game, OpenSpaceCard card);
    public void setNextStatePiratesCard(GameState state, Game game, PiratesCard card);
    public void setNextStatePlanetsCard(GameState state, Game game, PlanetsCard card);
    public void setNextStateShipCard(GameState state, Game game, ShipCard card);
    public void setNextStateSlaversCard(GameState state, Game game, SlaversCard card);
    public void setNextStateSmugglersCard(GameState state, Game game, SmugglersCard card);
    public void setNextStateStardustCard(GameState state, Game game, StardustCard card);
    public void setNextStateStationCard(GameState state, Game game, StationCard card);

}
