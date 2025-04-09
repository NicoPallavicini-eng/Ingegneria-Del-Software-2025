package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.PlanetsCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlanetsCardTest {
    List <Planet> planets = new ArrayList<>();

    List <Integer> earthBlocks = new ArrayList<>();
    List <Integer> marsBlocks = new ArrayList<>();
    List <Integer> venusBlocks = new ArrayList<>();
    List <Integer> mercuryBlocks = new ArrayList<>();

    Integer b1earth = 4;
    Integer b2earth = 3;
    Integer b3earth = 3;

    Integer b1mars = 4;
    Integer b2mars = 2;
    Integer b3mars = 1;

    Integer b1venus = 3;
    Integer b2venus = 2;
    Integer b3venus = 1;

    Integer b1mercury = 2;
    Integer b2mercury = 1;
    Integer b3mercury = 1;

    PlanetsCardVisitor planetsCardVisitor = new PlanetsCardVisitor();
    PlanetsCard planetCard = new PlanetsCard(true, true, planetsCardVisitor, planets, 4);

    @Test
    void PlanetsProcessTest() {
        Player p = new Player("IP", "nick", Color.RED);
        Ship s = p.getShip();
        s.setTravelDays(20);

        earthBlocks.add(b1earth);
        earthBlocks.add(b2earth);
        earthBlocks.add(b3earth);

        marsBlocks.add(b1mars);
        marsBlocks.add(b2mars);
        marsBlocks.add(b3mars);

        venusBlocks.add(b1venus);
        venusBlocks.add(b2venus);
        venusBlocks.add(b3venus);

        mercuryBlocks.add(b1mercury);
        mercuryBlocks.add(b2mercury);
        mercuryBlocks.add(b3mercury);

        Planet earth = new Planet(earthBlocks);
        Planet mars = new Planet(marsBlocks);
        Planet venus = new Planet(venusBlocks);
        Planet mercury = new Planet(mercuryBlocks);

        planets.add(earth);
        planets.add(mars);
        planets.add(venus);
        planets.add(mercury);

        p.setEngages(true);
        // input is Mars
        p.setInputEngine(1); // will have to rename "setInput" (chooses planet here)

        planetCard.process(p);

        assertEquals(mars.getShipLanded(), s);
        assertEquals(s.getCargoFromCards(), marsBlocks);
        assertEquals(s.getTravelDays(), 20 - planetCard.getDaysToLose());
    }
}