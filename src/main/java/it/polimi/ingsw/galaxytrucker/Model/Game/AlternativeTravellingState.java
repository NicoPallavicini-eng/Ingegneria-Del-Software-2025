package it.polimi.ingsw.galaxytrucker.Model.Game;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Card;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;

import java.util.ArrayList;
import java.util.List;

// Handles CombatZone, Epidemic, Meteors, Stardust Cards
public class AlternativeTravellingState implements GameState {
    private final Game game;
    private final Card currentCard;
    private Card nextCard;
    private GameState nextState;

    public Game getGame() {
        return game;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public AlternativeTravellingState(Game game, Card currentCard) {
        this.game = game;
        this.currentCard = currentCard;
    }

    public GameState getNextState() {
        return nextState;
    }

    public void setNextState(GameState nextState) {
        this.nextState = nextState;
    }

    @Override
    public GameState next() {
        nextCard = getGame().getDeck().drawCard();
        if (nextCard == null) {
            return new FinalState(game);
        } else {
            nextCard.acceptNextVisitor(this, nextCard.getCardVisitor(), game);
            return nextState;
        }
    }

    public void process() {
        List<Player> players = game.getListOfPlayers();

        // Get list of ships
        List <Ship> ships = new ArrayList<>();
        for (Player player : players) {
            ships.add(player.getShip());
        }

        // Find ship with the least crew
        List <Ship> lessCrewShips = new ArrayList<>();
        lessCrewShips.add(ships.getFirst());
        for (Ship ship : ships) {
            if (ship.getNumberOfCrewMembers() == lessCrewShips.getFirst().getNumberOfCrewMembers()) {
                lessCrewShips.add(ship);
            } else if (ship.getNumberOfCrewMembers() < lessCrewShips.getFirst().getNumberOfCrewMembers()) {
                lessCrewShips.removeAll(lessCrewShips);
                lessCrewShips.add(ship);
            }
        }

        // Find ship with the least engine power
        List <Ship> lessEngineShips = new ArrayList<>();
        lessEngineShips.add(ships.getFirst());
        for (Ship ship : ships) {
            if (ship.getEnginePower() == lessEngineShips.getFirst().getEnginePower()) {
                lessEngineShips.add(ship);
            } else if (ship.getEnginePower() < lessEngineShips.getFirst().getEnginePower()) {
                lessEngineShips.removeAll(lessEngineShips);
                lessEngineShips.add(ship);
            }
        }

        // Find ship with the least firepower
        List <Ship> lessFirepowerShips = new ArrayList<>();
        lessFirepowerShips.add(ships.getFirst());
        for (Ship ship : ships) {
            if (ship.getFirepower() == lessFirepowerShips.getFirst().getFirepower()) {
                lessFirepowerShips.add(ship);
            } else if (ship.getFirepower() < lessFirepowerShips.getFirst().getFirepower()) {
                lessFirepowerShips.removeAll(lessFirepowerShips);
                lessFirepowerShips.add(ship);
            }
        }

        currentCard.acceptCardVisitorAlternative(this, currentCard.getCardVisitor(), lessCrewShips, lessEngineShips, lessFirepowerShips);
    }

}
