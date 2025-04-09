package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.PlanetsCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlanetsCardTest { //example
    List <Planet> planets = new ArrayList<Planet>();
    PlanetsCardVisitor planetsCardVisitor = new PlanetsCardVisitor();
    PlanetsCard planetCard = new PlanetsCard(true, true, planetsCardVisitor, planets, 4);
    @Test
    void PlanetsProcessTest() {
        Player p = new Player("IP", "nick", Color.RED);

        planetCard.process(p);

        assert//...;
    }
}