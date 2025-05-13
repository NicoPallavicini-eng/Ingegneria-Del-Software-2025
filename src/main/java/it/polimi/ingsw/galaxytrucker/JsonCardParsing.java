package it.polimi.ingsw.galaxytrucker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.galaxytrucker.Model.Cards.*;
import it.polimi.ingsw.galaxytrucker.Model.Direction;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;

import java.lang.reflect.Type;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class JsonCards{
    public String openSpaceCards;
    public String planetsCards;
    public String meteorsCards;
    public String stationCards;
    public String ShipCards;
    public String slaversCards;
    public String smugglersCards;
    public String piratesCards;
    public String stardustCards;
    public String combatZoneCards;
    public String epidemicCards;

    public JsonCards(){
        openSpaceCards = "{\"openSpaceCard1\":{\n" +
                "\t\t\"levelTwo\":false,\n" +
                "\t\t\"used\":false\n" +
                "\t},\n" +
                "\t\"openSpaceCard2\":{\n" +
                "\t\t\"levelTwo\":false,\n" +
                "\t\t\"used\":false\n" +
                "\t},\n" +
                "\t\"openSpaceCard3\":{\n" +
                "\t\t\"levelTwo\":false,\n" +
                "\t\t\"used\":false\n" +
                "\t},\n" +
                "\t\"openSpaceCard4\":{\n" +
                "\t\t\"levelTwo\":true,\n" +
                "\t\t\"used\":false\n" +
                "\t},\n" +
                "\t\"openSpaceCard5\":{\n" +
                "\t\t\"levelTwo\":true,\n" +
                "\t\t\"used\":false\n" +
                "\t},\n" +
                "\t\"openSpaceCard6\":{\n" +
                "\t\t\"levelTwo\":true,\n" +
                "\t\t\"used\":false\n" +
                "\t},\n" +
                "\t\"openSpaceCard7\":{\n" +
                "\t\t\"levelTwo\":false,\n" +
                "\t\t\"used\":false\n" +
                "\t}}";
        planetsCards = "{\"planetsCard1\":{\n" +
                "\t\t\"levelTwo\":false,\n" +
                "\t\t\"used\":false,\n" +
                "\t\t\"planets\":[{ \"blocks\": [4,4] },{ \"blocks\": [4,1,1] },{ \"blocks\": [3] }],\n" +
                "\t\t\"daysToLose\":2\n" +
                "\t},\n" +
                "\t\"planetsCard2\":{\n" +
                "\t\t\"levelTwo\":false,\n" +
                "\t\t\"used\":false,\n" +
                "\t\t\"planets\":[{ \"blocks\": [3,2,1,1] },{ \"blocks\": [3,3] }],\n" +
                "\t\t\"daysToLose\":3\n" +
                "\t},\n" +
                "\t\"planetsCard3\":{\n" +
                "\t\t\"levelTwo\":false,\n" +
                "\t\t\"used\":false,\n" +
                "\t\t\"planets\":[{ \"blocks\": [4,2,1,1,1] },{ \"blocks\": [4,3,1] },{ \"blocks\": [4,1,1,1] },{ \"blocks\": [4,2] }],\n" +
                "\t\t\"daysToLose\":3\n" +
                "\t},\n" +
                "\t\"planetsCard4\":{\n" +
                "\t\t\"levelTwo\":false,\n" +
                "\t\t\"used\":false,\n" +
                "\t\t\"planets\":[{ \"blocks\": [2,2] },{ \"blocks\": [3] },{ \"blocks\": [1,1,1]}],\n" +
                "\t\t\"daysToLose\":1\n" +
                "\t},\n" +
                "\t\"planetsCard5\":{\n" +
                "\t\t\"levelTwo\":true,\n" +
                "\t\t\"used\":false,\n" +
                "\t\t\"planets\":[{ \"blocks\": [4,3] },{ \"blocks\": [3,2,1] },{ \"blocks\": [2,2]},{ \"blocks\": [3]}],\n" +
                "\t\t\"daysToLose\":2\n" +
                "\t},\n" +
                "\t\"planetsCard6\":{\n" +
                "\t\t\"levelTwo\":true,\n" +
                "\t\t\"used\":false,\n" +
                "\t\t\"planets\":[{ \"blocks\": [4,4] },{ \"blocks\": [2,2,2,2] }],\n" +
                "\t\t\"daysToLose\":3\n" +
                "\t},\n" +
                "\t\"planetsCard7\":{\n" +
                "\t\t\"levelTwo\":true,\n" +
                "\t\t\"used\":false,\n" +
                "\t\t\"planets\":[{ \"blocks\": [2,2,2,2] },{ \"blocks\": [3,3] },{ \"blocks\": [1,1,1,1]}],\n" +
                "\t\t\"daysToLose\":3\n" +
                "\t},\n" +
                "\t\"planetsCard8\":{\n" +
                "\t\t\"levelTwo\":true,\n" +
                "\t\t\"used\":false,\n" +
                "\t\t\"planets\":[{ \"blocks\": [4,4,4,3] },{ \"blocks\": [4,4,2,2] },{ \"blocks\": [4,1,1,1,1]}],\n" +
                "\t\t\"daysToLose\":4\n" +
                "\t}}";
        meteorsCards = "{\"meteorsCard1\":{\n" +
                "\t\t\"levelTwo\":false,\n" +
                "\t\t\"used\":false,\n" +
                "\t\t\"meteors\":[{ \"bigMeteor\": true,\"direction\":\"NORTH\",\"rowOrColumn\":\"COLUMN\"},{ \"bigMeteor\": false,\"direction\":\"NORTH\",\"rowOrColumn\":\"COLUMN\"},{ \"bigMeteor\": true,\"direction\":\"NORTH\",\"rowOrColumn\":\"COLUMN\"}]\n" +
                "\t},\n" +
                "\t\"meteorsCard2\":{\n" +
                "\t\t\"levelTwo\":false,\n" +
                "\t\t\"used\":false,\n" +
                "\t\t\"meteors\":[{ \"bigMeteor\": true,\"direction\":\"NORTH\",\"rowOrColumn\":\"COLUMN\"},{ \"bigMeteor\": false,\"direction\":\"WEST\",\"rowOrColumn\":\"ROW\"},{ \"bigMeteor\": false,\"direction\":\"EAST\",\"rowOrColumn\":\"ROW\"}]\n" +
                "\t},\n" +
                "\t\"meteorsCard3\":{\n" +
                "\t\t\"levelTwo\":false,\n" +
                "\t\t\"used\":false,\n" +
                "\t\t\"meteors\":[{ \"bigMeteor\": false,\"direction\":\"NORTH\",\"rowOrColumn\":\"COLUMN\"},{ \"bigMeteor\": false,\"direction\":\"NORTH\",\"rowOrColumn\":\"COLUMN\"},{ \"bigMeteor\": false,\"direction\":\"WEST\",\"rowOrColumn\":\"ROW\"},{ \"bigMeteor\": false,\"direction\":\"EAST\",\"rowOrColumn\":\"ROW\"},{ \"bigMeteor\": false,\"direction\":\"SOUTH\",\"rowOrColumn\":\"COLUMN\"}]\n" +
                "\t},\n" +
                "\t\"meteorsCard4\":{\n" +
                "\t\t\"levelTwo\":true,\n" +
                "\t\t\"used\":false,\n" +
                "\t\t\"meteors\":[{ \"bigMeteor\": true,\"direction\":\"NORTH\",\"rowOrColumn\":\"COLUMN\"},{ \"bigMeteor\": true,\"direction\":\"NORTH\",\"rowOrColumn\":\"COLUMN\"},{ \"bigMeteor\": false,\"direction\":\"SOUTH\",\"rowOrColumn\":\"COLUMN\"},{ \"bigMeteor\": false,\"direction\":\"SOUTH\",\"rowOrColumn\":\"COLUMN\"}]\n" +
                "\t},\n" +
                "\t\"meteorsCard5\":{\n" +
                "\t\t\"levelTwo\":true,\n" +
                "\t\t\"used\":false,\n" +
                "\t\t\"meteors\":[{ \"bigMeteor\": false,\"direction\":\"NORTH\",\"rowOrColumn\":\"COLUMN\"},{ \"bigMeteor\": false,\"direction\":\"NORTH\",\"rowOrColumn\":\"COLUMN\"},{ \"bigMeteor\": true,\"direction\":\"WEST\",\"rowOrColumn\":\"ROW\"},{ \"bigMeteor\": false,\"direction\":\"WEST\",\"rowOrColumn\":\"ROW\"},{ \"bigMeteor\": false,\"direction\":\"WEST\",\"rowOrColumn\":\"ROW\"}]\n" +
                "\t},\n" +
                "\t\"meteorsCard6\":{\n" +
                "\t\t\"levelTwo\":true,\n" +
                "\t\t\"used\":false,\n" +
                "\t\t\"meteors\":[{ \"bigMeteor\": false,\"direction\":\"NORTH\",\"rowOrColumn\":\"COLUMN\"},{ \"bigMeteor\": false,\"direction\":\"NORTH\",\"rowOrColumn\":\"COLUMN\"},{ \"bigMeteor\": true,\"direction\":\"EAST\",\"rowOrColumn\":\"ROW\"},{ \"bigMeteor\": false,\"direction\":\"EAST\",\"rowOrColumn\":\"ROW\"},{ \"bigMeteor\": false,\"direction\":\"EAST\",\"rowOrColumn\":\"ROW\"}]\n" +
                "\t}}";
        stationCards = "{\"stationCard1\":{\n" +
                "\t\t\"levelTwo\":false,\n" +
                "\t\t\"used\":false,\n" +
                "\t\t\"crewNumberNeeded\":5,\n" +
                "\t\t\"daysToLose\":1,\n" +
                "\t\t\"blocks\":[3,2]\n" +
                "\t},\n" +
                "\t\"stationCard2\":{\n" +
                "\t\t\"levelTwo\":false,\n" +
                "\t\t\"used\":false,\n" +
                "\t\t\"crewNumberNeeded\":6,\n" +
                "\t\t\"daysToLose\":1,\n" +
                "\t\t\"blocks\":[4,4]\n" +
                "\t},\n" +
                "\t\"stationCard3\":{\n" +
                "\t\t\"levelTwo\":true,\n" +
                "\t\t\"used\":false,\n" +
                "\t\t\"crewNumberNeeded\":7,\n" +
                "\t\t\"daysToLose\":1,\n" +
                "\t\t\"blocks\":[4,3]\n" +
                "\t},\n" +
                "\t\"stationCard4\":{\n" +
                "\t\t\"levelTwo\":true,\n" +
                "\t\t\"used\":false,\n" +
                "\t\t\"crewNumberNeeded\":8,\n" +
                "\t\t\"daysToLose\":2,\n" +
                "\t\t\"blocks\":[3,3,2]\n" +
                "\t}}";
        ShipCards = "{\"ShipCard1\":{\n" +
                "\t\t\"levelTwo\":false,\n" +
                "\t\t\"used\":false,\n" +
                "\t\t\"crewNumberLost\":3,\n" +
                "\t\t\"credits\":4,\n" +
                "\t\t\"daysToLose\":1\n" +
                "\t},\n" +
                "\t\"ShipCard2\":{\n" +
                "\t\t\"levelTwo\":false,\n" +
                "\t\t\"used\":false,\n" +
                "\t\t\"crewNumberLost\":2,\n" +
                "\t\t\"credits\":3,\n" +
                "\t\t\"daysToLose\":1\n" +
                "\t},\n" +
                "\t\"ShipCard3\":{\n" +
                "\t\t\"levelTwo\":true,\n" +
                "\t\t\"used\":false,\n" +
                "\t\t\"crewNumberLost\":5,\n" +
                "\t\t\"credits\":8,\n" +
                "\t\t\"daysToLose\":2\n" +
                "\t},\n" +
                "\t\"ShipCard4\":{\n" +
                "\t\t\"levelTwo\":true,\n" +
                "\t\t\"used\":false,\n" +
                "\t\t\"crewNumberLost\":4,\n" +
                "\t\t\"credits\":6,\n" +
                "\t\t\"daysToLose\":1\n" +
                "\t}}";
        slaversCards = "{\"slaversCard1\":{\n" +
                "\t\t\"levelTwo\":false,\n" +
                "\t\t\"used\":false,\n" +
                "\t\t\"firepower\":6,\n" +
                "\t\t\"crewLost\":3,\n" +
                "\t\t\"credits\":5,\n" +
                "\t\t\"daysToLose\":1\n" +
                "\t},\n" +
                "\t\"slaversCard2\":{\n" +
                "\t\t\"levelTwo\":true,\n" +
                "\t\t\"used\":false,\n" +
                "\t\t\"firepower\":7,\n" +
                "\t\t\"crewLost\":4,\n" +
                "\t\t\"credits\":8,\n" +
                "\t\t\"daysToLose\":2\n" +
                "\t}}";
        smugglersCards = "{\"smugglersCard1\":{\n" +
                "\t\t\"levelTwo\":false,\n" +
                "\t\t\"used\":false,\n" +
                "\t\t\"firepower\":4,\n" +
                "\t\t\"blocks\":[3,2,1],\n" +
                "\t\t\"lostBlocksNumber\":2,\n" +
                "\t\t\"daysToLose\":1\n" +
                "\t},\n" +
                "\t\"smugglersCard2\":{\n" +
                "\t\t\"levelTwo\":true,\n" +
                "\t\t\"used\":false,\n" +
                "\t\t\"firepower\":8,\n" +
                "\t\t\"blocks\":[4,3,3],\n" +
                "\t\t\"lostBlocksNumber\":3,\n" +
                "\t\t\"daysToLose\":1\n" +
                "\t}}";
        piratesCards = "{\"piratesCard1\":{\n" +
                "\t\t\"levelTwo\":false,\n" +
                "\t\t\"used\":false,\n" +
                "\t\t\"firepower\":5,\n" +
                "\t\t\"credits\":4,\n" +
                "\t\t\"daysToLose\":1,\n" +
                "\t\t\"cannonballList\":[{\"bigCannonball\":false,\"direction\":\"NORTH\",\"rowOrColumn\":\"COLUMN\"},{\"bigCannonball\":true,\"direction\":\"NORTH\",\"rowOrColumn\":\"COLUMN\"},{\"bigCannonball\":false,\"direction\":\"NORTH\",\"rowOrColumn\":\"COLUMN\"}]\n" +
                "\t},\n" +
                "\t\"piratesCard2\":{\n" +
                "\t\t\"levelTwo\":true,\n" +
                "\t\t\"used\":false,\n" +
                "\t\t\"firepower\":6,\n" +
                "\t\t\"credits\":7,\n" +
                "\t\t\"daysToLose\":2,\n" +
                "\t\t\"cannonballList\":[{\"bigCannonball\":true,\"direction\":\"NORTH\",\"rowOrColumn\":\"COLUMN\"},{\"bigCannonball\":false,\"direction\":\"NORTH\",\"rowOrColumn\":\"COLUMN\"},{\"bigCannonball\":true,\"direction\":\"NORTH\",\"rowOrColumn\":\"COLUMN\"}]\n" +
                "\t}}";
        stardustCards = "{\"stardustCard1\":{\n" +
                "\t\t\"levelTwo\":false,\n" +
                "\t\t\"used\":false\n" +
                "\t},\n" +
                "\t\"stardustCard2\":{\n" +
                "\t\t\"levelTwo\":true,\n" +
                "\t\t\"used\":false\n" +
                "\t}}";
        combatZoneCards = "{\"combatZoneCard1\":{\n" +
                "\t\t\"levelTwo\":false,\n" +
                "\t\t\"used\":false,\n" +
                "\t\t\"daysLost\":3,\n" +
                "\t\t\"crewLost\":2,\n" +
                "\t\t\"cargoLost\":0,\n" +
                "\t\t\"cannonballList\":[{\"bigCannonball\":false,\"direction\":\"SOUTH\",\"rowOrColumn\":\"COLUMN\"},{\"bigCannonball\":true,\"direction\":\"SOUTH\",\"rowOrColumn\":\"COLUMN\"}]\n" +
                "\n" +
                "\t},\n" +
                "\t\"combatZoneCard2\":{\n" +
                "\t\t\"levelTwo\":true,\n" +
                "\t\t\"used\":false,\n" +
                "\t\t\"daysLost\":4,\n" +
                "\t\t\"crewLost\":0,\n" +
                "\t\t\"cargoLost\":3,\n" +
                "\t\t\"cannonballList\":[{\"bigCannonball\":false,\"direction\":\"NORTH\",\"rowOrColumn\":\"COLUMN\"},{\"bigCannonball\":false,\"direction\":\"WEST\",\"rowOrColumn\":\"ROW\"},{\"bigCannonball\":false,\"direction\":\"EAST\",\"rowOrColumn\":\"ROW\"},{\"bigCannonball\":true,\"direction\":\"SOUTH\",\"rowOrColumn\":\"COLUMN\"}]\n" +
                "\n" +
                "\t}}";
        epidemicCards = "{\"epidemicCard1\":{\n" +
                "\t\t\"levelTwo\":true,\n" +
                "\t\t\"used\":false\n" +
                "\t}}";

    }
}

class PirateCardParse {
    public boolean levelTwo;
    public boolean used;
    public int firepower;
    public int credits;
    public int daysToLose;
    public List<CannonballParse> cannonballList;
}
class CannonballParse {
    public boolean bigCannonball;
    public String direction;
    public String rowOrColumn;
}
class SpaceCardParse{
    public boolean levelTwo;
    public boolean used;
}
class StationCardParse{
    public boolean levelTwo;
    public boolean used;
    public int crewNumberNeeded;
    public int daysToLose;
    public List<Integer> blocks;
}
class AbbandonShipCardParse{
    public boolean levelTwo;
    public boolean used;
    public int crewNumberLost;
    public int daysToLose;
    public int credits;
}
class SlaversCardParse{
    public boolean levelTwo;
    public boolean used;
    public int firepower;
    public int crewLost;
    public int credits;
    public int daysToLose;
}
class SmuglersCardParse{
    public boolean levelTwo;
    public boolean used;
    public int firepower;
    public List<Integer> blocks;
    public int lostBlocksNumber;
    public int daysToLose;
}
class StardustCardParse{
    public boolean levelTwo;
    public boolean used;
}
class EpidemicCardParse{
    public boolean levelTwo;
    public boolean used;
}
class PlanetsCardParse{
    public boolean levelTwo;
    public boolean used;
    public List<PlanetParse> planets;
    public int daysToLose;
}
class PlanetParse{
    public List<Integer> blocks;
}
class MeteorParse {
    public boolean bigMeteor;
    public String direction;
    public String rowOrColumn;
}

class MeteorCardParse {
    public boolean levelTwo;
    public boolean used;
    public List<MeteorParse> meteors;
}
class BattleZoneCardParse {
    public boolean levelTwo;
    public boolean used;
    public int daysLost;
    public int crewLost;
    public int cargoLost;
    public List<CannonballParse> cannonballList;
}

public class JsonCardParsing {

    private ArrayList<PiratesCard> piratesCards;
    private ArrayList<MeteorsCard> meteorsCards;
    private ArrayList<PlanetsCard> planetsCards;
    private ArrayList<OpenSpaceCard> openSpaceCards;
    private ArrayList<SmugglersCard> smugglersCards;
    private ArrayList<EpidemicCard> epidemicCards;
    private ArrayList<ShipCard> shipCards;
    private ArrayList<StardustCard> stardustCards;
    private ArrayList<SlaversCard> slaversCards;
    private ArrayList<StationCard> stationCards ;
    private ArrayList<CombatZoneCard> combatZoneCards;

    public ArrayList<CombatZoneCard> getCombatZoneCards() {
        return combatZoneCards;
    }

    public ArrayList<StationCard> getStationCards() {
        return stationCards;
    }
    public ArrayList<SlaversCard> getSlaversCards() {
        return slaversCards;
    }
    public ArrayList<StardustCard> getStardustCards() {
        return stardustCards;
    }
    public ArrayList<ShipCard> getShipCards() {
        return shipCards;
    }
    public ArrayList<SmugglersCard> getSmugglersCards() {
        return smugglersCards;
    }
    public ArrayList<OpenSpaceCard> getSpaceCards(){
        return openSpaceCards;
    }
    public ArrayList<PlanetsCard> getPlanetsCards() {
        return planetsCards;
    }
    public ArrayList<MeteorsCard> getMeteorsCards() {
        return meteorsCards;
    }
    public ArrayList<PiratesCard> getPiratesCards() {
        return piratesCards;
    }
    public ArrayList<EpidemicCard> getEpidemicCards() {
        return epidemicCards;
    }

    public void setCombatZoneCards(ArrayList<CombatZoneCard> combatZoneCards) {
        this.combatZoneCards = combatZoneCards;
    }
    public void setStationCards(ArrayList<StationCard> stationCards) {
        this.stationCards = stationCards;
    }
    public void setSlaversCards(ArrayList<SlaversCard> slaversCards) {
        this.slaversCards = slaversCards;
    }
    public void setPlanetsCards(ArrayList<PlanetsCard> planetsCards) {
        this.planetsCards = planetsCards;
    }

    public void setEpidemicCards(ArrayList<EpidemicCard> epidemicCards) {
        this.epidemicCards = epidemicCards;
    }

    public void setMeteorsCards(ArrayList<MeteorsCard> meteorsCards) {
        this.meteorsCards = meteorsCards;
    }

    public void setPiratesCards(ArrayList<PiratesCard> piratesCards) {
        this.piratesCards = piratesCards;
    }

    public void setShipCards(ArrayList<ShipCard> shipCards) {
        this.shipCards = shipCards;
    }

    public void setSmugglersCards(ArrayList<SmugglersCard> smugglersCards) {
        this.smugglersCards = smugglersCards;
    }

    public void setSpaceCards(ArrayList<OpenSpaceCard> openSpaceCards) {
        this.openSpaceCards = openSpaceCards;
    }

    public void setStardustCards(ArrayList<StardustCard> stardustCards) {
        this.stardustCards = stardustCards;
    }

    public List<Card> getCompleteList(){
        List<Card> completeList = new ArrayList<>();
        completeList.addAll(piratesCards);
        completeList.addAll(planetsCards);
        completeList.addAll(meteorsCards);
        completeList.addAll(epidemicCards);
        completeList.addAll(combatZoneCards);
        completeList.addAll(openSpaceCards);
        completeList.addAll(stardustCards);
        completeList.addAll(smugglersCards);
        completeList.addAll(shipCards);
        completeList.addAll(stationCards);
        completeList.addAll(stardustCards);
        return completeList;
    }

    public List<Card> getCompleteListLevel1(){
        List<Card> completeList = new ArrayList<>();
        for(Card card:getCompleteList()){
            if(!card.isLevelTwo()){
                completeList.add(card);
            }
        }
        return completeList;
    }
    public List<Card> getCompleteListLevel2(){
        List<Card> completeList = new ArrayList<>();
        for(Card card:getCompleteList()){
            if(card.isLevelTwo()){
                completeList.add(card);
            }
        }
        return completeList;
    }

    public JsonCardParsing(){
        JsonCards jsonCards = new JsonCards();
        Gson gson = new Gson();

        ArrayList<OpenSpaceCard> openSpaceList = new ArrayList<>();

        Type type = new TypeToken<Map<String,SpaceCardParse>>(){}.getType();

        Map<String,SpaceCardParse> openSpaceCards = gson.fromJson(jsonCards.openSpaceCards, type);

        for (String connectorKey : openSpaceCards.keySet()) {
            SpaceCardParse openSpaceCardParse = openSpaceCards.get(connectorKey);

            OpenSpaceCard space = new OpenSpaceCard(openSpaceCardParse.levelTwo, openSpaceCardParse.used);
            openSpaceList.add(space);
        }
        setSpaceCards(openSpaceList);

        ArrayList<StationCard> stationCardList = new ArrayList<>();
        Type typeStation = new TypeToken<Map<String,StationCardParse>>(){}.getType();
        Map<String,StationCardParse> stationCards = gson.fromJson(jsonCards.stationCards, typeStation);
        for (String connectorKey : stationCards.keySet()) {
            StationCardParse stationCardParse = stationCards.get(connectorKey);
            StationCard station = new StationCard(stationCardParse.levelTwo,stationCardParse.used,stationCardParse.crewNumberNeeded,stationCardParse.blocks,stationCardParse.daysToLose);
            stationCardList.add(station);
        }

        setStationCards(stationCardList);

        ArrayList<ShipCard> shipCardList = new ArrayList<>();
        Type shipCardType = new TypeToken<Map<String,AbbandonShipCardParse>>(){}.getType();
        Map<String,AbbandonShipCardParse> shipCards = gson.fromJson(jsonCards.ShipCards, shipCardType);
        for (String connectorKey : shipCards.keySet()) {
            AbbandonShipCardParse ShipCardParse = shipCards.get(connectorKey);
            ShipCard ship = new ShipCard(ShipCardParse.levelTwo,ShipCardParse.used,ShipCardParse.crewNumberLost,ShipCardParse.credits,ShipCardParse.daysToLose);
            shipCardList.add(ship);
        }
        setShipCards(shipCardList);

        ArrayList<SlaversCard> slaversCardList = new ArrayList<>();
        Type slaversCardType = new TypeToken<Map<String,SlaversCardParse>>(){}.getType();
        Map<String,SlaversCardParse> slaversCards = gson.fromJson(jsonCards.slaversCards, slaversCardType);
        for (String connectorKey : slaversCards.keySet()) {
            SlaversCardParse slaversCardParse = slaversCards.get(connectorKey);
            SlaversCard slaver = new SlaversCard(slaversCardParse.levelTwo,slaversCardParse.used,slaversCardParse.firepower,slaversCardParse.credits,slaversCardParse.crewLost,slaversCardParse.daysToLose);
            slaversCardList.add(slaver);
        }
        setSlaversCards(slaversCardList);

        ArrayList<SmugglersCard> smugglersCardList = new ArrayList<>();
        Type smugglersCardType = new TypeToken<Map<String,SmuglersCardParse>>(){}.getType();
        Map<String,SmuglersCardParse> smuglersCards = gson.fromJson(jsonCards.smugglersCards, smugglersCardType);
        for (String connectorKey : smuglersCards.keySet()) {
            SmuglersCardParse smugglersCardParse = smuglersCards.get(connectorKey);
            SmugglersCard smuggler = new SmugglersCard(smugglersCardParse.levelTwo,smugglersCardParse.used,smugglersCardParse.firepower,smugglersCardParse.blocks,smugglersCardParse.lostBlocksNumber,smugglersCardParse.daysToLose);
            smugglersCardList.add(smuggler);
        }
        setSmugglersCards(smugglersCardList);

        ArrayList<StardustCard> stardustCardList = new ArrayList<>();
        Type stardustCard = new TypeToken<Map<String,StardustCardParse>>(){}.getType();
        Map<String,StardustCardParse> stardustCards = gson.fromJson(jsonCards.stardustCards, stardustCard);
        for (String connectorKey : stardustCards.keySet()) {
            StardustCardParse  stardustCardParse = stardustCards.get(connectorKey);
            StardustCard stardust = new StardustCard(stardustCardParse.levelTwo,stardustCardParse.used);
            stardustCardList.add(stardust);
        }
        setStardustCards(stardustCardList);

        ArrayList<EpidemicCard> epidemicCardList = new ArrayList<>();
        Type epidemicCard = new TypeToken<Map<String,EpidemicCardParse>>(){}.getType();
        Map<String,EpidemicCardParse> epidemicCards = gson.fromJson(jsonCards.epidemicCards, epidemicCard);
        for (String connectorKey : epidemicCards.keySet()) {
            EpidemicCardParse epidemicCardParse = epidemicCards.get(connectorKey);
            EpidemicCard epidemic = new EpidemicCard(epidemicCardParse.levelTwo,epidemicCardParse.used);
            epidemicCardList.add(epidemic);
        }
        setEpidemicCards(epidemicCardList);

        ArrayList<PlanetsCard> planetsCardList = new ArrayList<>();

        Type planetsCardType = new TypeToken<Map<String, PlanetsCardParse>>() {}.getType();
        Map<String, PlanetsCardParse> planetsCards = gson.fromJson(jsonCards.planetsCards, planetsCardType);

        for (String key : planetsCards.keySet()) {
            PlanetsCardParse parseCard = planetsCards.get(key);

            List<Planet> planetList = new ArrayList<>();
            for (PlanetParse planetParse : parseCard.planets) {
                planetList.add(new Planet(planetParse.blocks));
            }

            PlanetsCard planet = new PlanetsCard(parseCard.levelTwo, parseCard.used, planetList, parseCard.daysToLose);
            planetsCardList.add(planet);
        }
        setPlanetsCards(planetsCardList);

        ArrayList<MeteorsCard> meteorsCardList = new ArrayList<>();

        Type meteorsCardType = new TypeToken<Map<String, MeteorCardParse>>() {}.getType();
        Map<String, MeteorCardParse> meteorsCards = gson.fromJson(jsonCards.meteorsCards, meteorsCardType);

        for (String key : meteorsCards.keySet()) {
            MeteorCardParse meteorParseCard = meteorsCards.get(key);

            List<Meteor> meteorList = new ArrayList<>();
            for (MeteorParse meteorParse : meteorParseCard.meteors) {
                meteorList.add(new Meteor(meteorParse.bigMeteor, Direction.valueOf(meteorParse.direction), RowOrColumn.valueOf(meteorParse.rowOrColumn)));
            }

            MeteorsCard meteor = new MeteorsCard(meteorParseCard.levelTwo, meteorParseCard.used, meteorList);
            meteorsCardList.add(meteor);
        }

        setMeteorsCards(meteorsCardList);

        ArrayList<PiratesCard> piratesCardList = new ArrayList<>();

        Type piratesCardType = new TypeToken<Map<String, PirateCardParse>>() {}.getType();
        Map<String,PirateCardParse> piratesCards = gson.fromJson(jsonCards.piratesCards, piratesCardType);
        for (String key : piratesCards.keySet()) {
            PirateCardParse pirateParseCard = piratesCards.get(key);

            List<Cannonball> cannonball = new ArrayList<>();
            for(CannonballParse cannonballParse: pirateParseCard.cannonballList){
                cannonball.add(new Cannonball(cannonballParse.bigCannonball,Direction.valueOf(cannonballParse.direction),RowOrColumn.valueOf(cannonballParse.rowOrColumn)));
            }

            PiratesCard pirate = new PiratesCard(pirateParseCard.levelTwo,pirateParseCard.used,pirateParseCard.firepower,pirateParseCard.credits,pirateParseCard.daysToLose,cannonball);
            piratesCardList.add(pirate);
        }

        setPiratesCards(piratesCardList);

        ArrayList<CombatZoneCard> combatZoneCardList = new ArrayList<>();
        Type battleZoneCardType = new TypeToken<Map<String, BattleZoneCardParse>>(){}.getType();
        Map<String, BattleZoneCardParse> combatZoneCards = gson.fromJson(jsonCards.combatZoneCards, battleZoneCardType);

        for(String key : combatZoneCards.keySet()){
            BattleZoneCardParse battleZoneParseCard = combatZoneCards.get(key);
            List<Cannonball> cannonball2 = new ArrayList<>();
            for(CannonballParse cannonballParse: battleZoneParseCard.cannonballList){
                cannonball2.add(new Cannonball(cannonballParse.bigCannonball,Direction.valueOf(cannonballParse.direction),RowOrColumn.valueOf(cannonballParse.rowOrColumn)));
            }
            CombatZoneCard combatZoneCard = new CombatZoneCard(battleZoneParseCard.levelTwo,battleZoneParseCard.used,battleZoneParseCard.daysLost,battleZoneParseCard.crewLost,battleZoneParseCard.cargoLost,cannonball2);
            combatZoneCardList.add(combatZoneCard);
        }

        setCombatZoneCards(combatZoneCardList);

        System.out.println("Level two: "+openSpaceList.get(3).isLevelTwo());
        System.out.println("Used: "+openSpaceList.get(3).isUsed());

        System.out.println("\nLevel two: " + stationCardList.get(3).isLevelTwo());
        System.out.println("Used: " +  stationCardList.get(3).isUsed());
        System.out.println("Crew Numbers: " + stationCardList.get(3).getCrewNumberNeeded());
        System.out.println("Days to Lose: " + stationCardList.get(3).getDaysToLose());
        System.out.println("Blocks:" + stationCardList.get(3).getBlockList());

        System.out.println("\nLevel two: " + shipCardList.get(3).isLevelTwo());
        System.out.println("Used: " +  shipCardList.get(3).isUsed());
        System.out.println("Crew Numbers Loose: " + shipCardList.get(3).getCrewNumberLost());
        System.out.println("Days to Lose: " + shipCardList.get(3).getDaysToLose());
        System.out.println("Credits:" + shipCardList.get(3).getCredits());

        System.out.println("\nLevel two: " + slaversCardList.get(1).isLevelTwo());
        System.out.println("Used: " +  slaversCardList.get(1).isUsed());
        System.out.println("Crew Numbers Loose: " + slaversCardList.get(1).getNumberOfCrewLost());
        System.out.println("Days to Lose: " + slaversCardList.get(1).getNumberOfDaysToLose());
        System.out.println("Credits:" + slaversCardList.get(1).getNumberOfCredits());
        System.out.println("Firepower: " + slaversCardList.get(1).getFirepower());

        System.out.println("\nLevel two: " + smugglersCardList.get(1).isLevelTwo());
        System.out.println("Used: " +  smugglersCardList.get(1).isUsed());
        System.out.println("Firepower: " + smugglersCardList.get(1).getFirepower());
        System.out.println("Blocks: " + smugglersCardList.get(1).getBlocksList());
        System.out.println("Days to loose: " + smugglersCardList.get(1).getDaysToLose());
        System.out.println("Lost Blocks Number " + smugglersCardList.get(1).getLostBlocksNumber());

        System.out.println("\nLevel two: "+ stardustCardList.get(0).isLevelTwo());
        System.out.println("Used: "+ stardustCardList.get(0).isUsed());

        System.out.println("\nLevel two: "+ stardustCardList.get(0).isLevelTwo());
        System.out.println("Used: "+ stardustCardList.get(0).isUsed());

        System.out.println("\nLevel two: "+ planetsCardList.get(0).isLevelTwo());
        System.out.println("is Used: "+ planetsCardList.get(0).isUsed());
        System.out.println("Days to loose: "+ planetsCardList.get(0).getDaysToLose());
        System.out.println("Days to loose: "+ planetsCardList.get(0).getPlanetsList().toString());
        System.out.println("Days to loose: "+ planetsCardList.get(0).getPlanetsList().get(1).getBlocks());

        System.out.println("MeteorCard: ");
        System.out.println("Level Two: " + meteorsCardList.get(0).isLevelTwo());
        System.out.println("Used: " + meteorsCardList.get(0).isUsed());

        for (Meteor meteor : meteorsCardList.get(0).getMeteorsList()) {
            System.out.println("  Meteor:");
            System.out.println("  Big Meteor: " + meteor.bigMeteor());
            System.out.println("  Direction: " + meteor.direction());
            System.out.println("  Row/Column: " + meteor.rowOrColumn());
        }

        System.out.println("\nLevel 2: " + piratesCardList.get(0).isLevelTwo());
        System.out.println("Used: " + piratesCardList.get(0).isUsed());
        System.out.println("Firepower: " + piratesCardList.get(0).getFirepower());
        System.out.println("Credits: " + piratesCardList.get(0).getCredits());
        System.out.println("Days to Lose: " + piratesCardList.get(0).getDaysToLose());

        for (Cannonball cannonball : piratesCardList.get(0).getCannonballList()) {
            System.out.println("  Cannonball");
            System.out.println("  Big Cannonball: " + cannonball.bigCannonball());
            System.out.println("  Direction: " + cannonball.direction());
            System.out.println("  Row or Column: " + cannonball.rowOrColumn());
        }

        System.out.println("\nLevel 2: " + combatZoneCardList.get(0).isLevelTwo());
        System.out.println("Used: " + combatZoneCardList.get(0).isUsed());
        System.out.println("DaysLost: " + combatZoneCardList.get(0).getDaysLost());
        System.out.println("CrewLost: " + combatZoneCardList.get(0).getCrewLost());
        System.out.println("Cargo Lost: " + combatZoneCardList.get(0).getCargoLost());
        for (Cannonball cannonball : combatZoneCardList.get(0).getCannonballList()) {
            System.out.println("  Cannonball");
            System.out.println("  Big Cannonball: " + cannonball.bigCannonball());
            System.out.println("  Direction: " + cannonball.direction());
            System.out.println("  Row or Column: " + cannonball.rowOrColumn());
        }

    }

}
