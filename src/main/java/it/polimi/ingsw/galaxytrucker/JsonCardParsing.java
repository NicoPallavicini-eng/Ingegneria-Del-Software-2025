package it.polimi.ingsw.galaxytrucker;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.galaxytrucker.Model.Cards.*;
import it.polimi.ingsw.galaxytrucker.Model.Direction;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * JsonCards class is  used to store Json Strings
 */
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
    public String combatZoneCardsL;
    public String combatZoneCardsNotL;
    public String epidemicCards;

    /**
     * Constructor of JsonCards,it builds Json Srtrings of each Card
     */
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
        combatZoneCardsL = "{\"combatZoneCardL\":{\n" +
            "\t\t\"levelTwo\":false,\n" +
            "\t\t\"used\":false,\n" +
            "\t\t\"daysLost\":3,\n" +
            "\t\t\"crewLost\":2,\n" +
            "\t\t\"cargoLost\":0,\n" +
            "\t\t\"cannonballList\":[{\"bigCannonball\":false,\"direction\":\"SOUTH\",\"rowOrColumn\":\"COLUMN\"},{\"bigCannonball\":true,\"direction\":\"SOUTH\",\"rowOrColumn\":\"COLUMN\"}]\n" +
            "\n" +
            "\t}}";
        combatZoneCardsNotL ="{\"combatZoneCardNotL\":{\n" +
            "\t\t\"levelTwo\":false,\n" +
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

/**
 * PiratesCardParse is used to translate the parameters from Json to actual PirateCard class
 */
class PiratesCardParse {
    public boolean levelTwo;
    public boolean used;
    public int firepower;
    public int credits;
    public int daysToLose;
    public List<CannonballParse> cannonballList;
}
/**
 * CannonballParse is used to translate the parameters from Json to actual Cannonball class
 */
class CannonballParse {
    public boolean bigCannonball;
    public String direction;
    public String rowOrColumn;
}
/**
 * SpaceCardParse is used to translate the parameters from Json to actual OpenSpaceCard class
 */
class SpaceCardParse {
    public boolean levelTwo;
    public boolean used;
}
/**
 * StationCardParse is used to translate the parameters from Json to actual StationCard class
 */
class StationCardParse {
    public boolean levelTwo;
    public boolean used;
    public int crewNumberNeeded;
    public int daysToLose;
    public List<Integer> blocks;
}
/**
 * ShipCardParse is used to translate the parameters from Json to actual ShipCard class
 */
class ShipCardParse {
    public boolean levelTwo;
    public boolean used;
    public int crewNumberLost;
    public int daysToLose;
    public int credits;
}
/**
 * SlaversCardParse is used to translate the parameters from Json to actual SlaversCard class
 */
class SlaversCardParse{
    public boolean levelTwo;
    public boolean used;
    public int firepower;
    public int crewLost;
    public int credits;
    public int daysToLose;
}

/**
 *  SmugglersCardParse is used to translate the parameters from Json to actual  SmugglersCard class
 */
class SmugglersCardParse {
    public boolean levelTwo;
    public boolean used;
    public int firepower;
    public List<Integer> blocks;
    public int lostBlocksNumber;
    public int daysToLose;
}
/**
 *  StardustCardParse is used to translate the parameters from Json to actual  StardustCard class
 */
class StardustCardParse{
    public boolean levelTwo;
    public boolean used;
}
/**
 *  EpidemicCardParse is used to translate the parameters from Json to actual  EpidemicCard class
 */
class EpidemicCardParse{
    public boolean levelTwo;
    public boolean used;
}
/**
 *  PlanetsCardParse is used to translate the parameters from Json to actual PlanetsCard class
 */
class PlanetsCardParse{
    public boolean levelTwo;
    public boolean used;
    public List<PlanetParse> planets;
    public int daysToLose;
}
/**
 *  PlanetsParse is used to translate the parameters from Json to actual Planets class
 */
class PlanetParse{
    public List<Integer> blocks;
}
/**
 *  MeteorParse is used to translate the parameters from Json to actual Meteor class
 */
class MeteorParse {
    public boolean bigMeteor;
    public String direction;
    public String rowOrColumn;
}
/**
 *  MeteorsCardParse is used to translate the parameters from Json to actual MeteorsCard class
 */
class MeteorsCardParse {
    public boolean levelTwo;
    public boolean used;
    public List<MeteorParse> meteors;
}
/**
 *  BattleZoneCardLParse  is used to translate the parameters from Json to actual BattleZoneCardL class
 */
class BattleZoneCardLParse {
    public boolean levelTwo;
    public boolean used;
    public int daysLost;
    public int crewLost;
    public int cargoLost;
    public List<CannonballParse> cannonballList;
}
/**
 *  BattleZoneCardNotLParse  is used to translate the parameters from Json to actual BattleZoneCardNotL class
 */
class BattleZoneCardNotLParse {
    public boolean levelTwo;
    public boolean used;
    public int daysLost;
    public int crewLost;
    public int cargoLost;
    public List<CannonballParse> cannonballList;
}

/**
 * JsonCardParsing is class,that actually converts Json Strings (using Json) into actual Card Class
 */
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
    private ArrayList<CombatZoneCardL> combatZoneLCards;
    private ArrayList<CombatZoneCardNotL> combatZoneNotLCards;

    /**
     * This function return a array-list of CombatZoneCardL
     * @return ArrayList<CombatZoneCardL>
     */
    public ArrayList<CombatZoneCardL> getCombatZoneLCards() {
        return combatZoneLCards;
    }

    /**
     * This function return a array-list of CombatZoneCardNotL
     * @return ArrayList<CombatZoneCardNotL>
     */
    public ArrayList<CombatZoneCardNotL> getCombatZoneNotLCards() {
        return combatZoneNotLCards;
    }

    /**
     * This function return a array-list of CombatZoneCardNotL
     * @return ArrayList<StationCard>
     */
    public ArrayList<StationCard> getStationCards() {
        return stationCards;
    }

    /**
     * This function return a array-list of SlaversCard
     * @return ArrayList<SlaversCard>
     */
    public ArrayList<SlaversCard> getSlaversCards() {
        return slaversCards;
    }

    /**
     * This function return a array-list of StardustCard
     * @return ArrayList<StardustCard>
     */
    public ArrayList<StardustCard> getStardustCards() {
        return stardustCards;
    }

    /**
     * This function return a array-list of ShipCard
     * @return ArrayList<ShipCard>
     */
    public ArrayList<ShipCard> getShipCards() {
        return shipCards;
    }

    /**
     * This function return an array-list of SmugglersCard
     * @return ArrayList<SmugglersCard>
     */
    public ArrayList<SmugglersCard> getSmugglersCards() {
        return smugglersCards;
    }

    /**
     * This function return an array-list of SpaceCards
     * @return ArrayList<OpenSpaceCard>
     */
    public ArrayList<OpenSpaceCard> getSpaceCards(){
        return openSpaceCards;
    }

    /**
     * This function return an array-list of PlanetsCard
     * @return ArrayList<PlanetsCard>
     */
    public ArrayList<PlanetsCard> getPlanetsCards() {
        return planetsCards;
    }

    /**
     * This function return an array-list of MeteorsCard
     * @return ArrayList<MeteorsCard>
     */
    public ArrayList<MeteorsCard> getMeteorsCards() {
        return meteorsCards;
    }

    /**
     * This function return an array-list of PiratesCard
     * @return ArrayList<PiratesCard>
     */
    public ArrayList<PiratesCard> getPiratesCards() {
        return piratesCards;
    }

    /**
     * This function return an array-list of EpidemicCard
     * @return ArrayList<EpidemicCard>
     */
    public ArrayList<EpidemicCard> getEpidemicCards() {
        return epidemicCards;
    }

    /**
     * Sets combatZoneLCards ArrayList in JsonCardParsing
     * @param combatZoneLCards
     */
    public void setCombatZoneLCards(ArrayList<CombatZoneCardL> combatZoneLCards) {
        this.combatZoneLCards = combatZoneLCards;
    }

    /**
     * Sets combatZoneNotLCards ArrayList in JsonCardParsing
     * @param combatZoneNotLCards
     */
    public void setCombatZoneNotLCards(ArrayList<CombatZoneCardNotL> combatZoneNotLCards) {
        this.combatZoneNotLCards = combatZoneNotLCards;
    }

    /**
     * Sets stationCards ArrayList in JsonCardParsing
     * @param stationCards
     */
    public void setStationCards(ArrayList<StationCard> stationCards) {
        this.stationCards = stationCards;
    }

    /**
     * Sets slaversCards ArrayList in JsonCardParsing
     * @param slaversCards
     */
    public void setSlaversCards(ArrayList<SlaversCard> slaversCards) {
        this.slaversCards = slaversCards;
    }

    /**
     * Sets planetsCards ArrayList in JsonCardParsing
     * @param planetsCards
     */
    public void setPlanetsCards(ArrayList<PlanetsCard> planetsCards) {
        this.planetsCards = planetsCards;
    }

    /**
     * Sets epidemicCards ArrayList in JsonCardParsing
     * @param epidemicCards
     */
    public void setEpidemicCards(ArrayList<EpidemicCard> epidemicCards) {
        this.epidemicCards = epidemicCards;
    }

    /**
     * Sets meteorsCards ArrayList in JsonCardParsing
     * @param meteorsCards
     */
    public void setMeteorsCards(ArrayList<MeteorsCard> meteorsCards) {
        this.meteorsCards = meteorsCards;
    }

    /**
     * Sets piratesCards ArrayList in JsonCardParsing
     * @param piratesCards
     */
    public void setPiratesCards(ArrayList<PiratesCard> piratesCards) {
        this.piratesCards = piratesCards;
    }

    /**
     * Sets shipCards ArrayList in JsonCardParsing
     * @param shipCards
     */
    public void setShipCards(ArrayList<ShipCard> shipCards) {
        this.shipCards = shipCards;
    }

    /**
     * Sets shipCards ArrayList in JsonCardParsing
     * @param smugglersCards
     */
    public void setSmugglersCards(ArrayList<SmugglersCard> smugglersCards) {
        this.smugglersCards = smugglersCards;
    }

    /**
     * Sets openSpaceCards ArrayList in JsonCardParsing
     * @param openSpaceCards
     */
    public void setSpaceCards(ArrayList<OpenSpaceCard> openSpaceCards) {
        this.openSpaceCards = openSpaceCards;
    }

    /**
     * Sets stardustCards ArrayList in JsonCardParsing
     * @param stardustCards
     */
    public void setStardustCards(ArrayList<StardustCard> stardustCards) {
        this.stardustCards = stardustCards;
    }

    /**
     * This function return a list of All Cards in Game
     * @return List<Card>
     */
    public List<Card> getCompleteList(){
        List<Card> completeList = new ArrayList<>();
        completeList.addAll(piratesCards);
        completeList.addAll(planetsCards);
        completeList.addAll(meteorsCards);
        completeList.addAll(epidemicCards);
        completeList.addAll(combatZoneLCards);
        completeList.addAll(combatZoneNotLCards);
        completeList.addAll(openSpaceCards);
        completeList.addAll(stardustCards);
        completeList.addAll(smugglersCards);
        completeList.addAll(shipCards);
        completeList.addAll(stationCards);
        completeList.addAll(stardustCards);
        return completeList;
    }

    /**
     * This function returns a complete List of Level 1 Cards
     * @return List<Card>
     */
    public List<Card> getCompleteListLevel1(){
        List<Card> completeList = new ArrayList<>();
        for(Card card:getCompleteList()){
            if(!card.isLevelTwo()){
                completeList.add(card);
            }
        }
        return completeList;
    }
    /**
     * This function returns a complete List of Level 2 Cards
     * @return List<Card>
     */
    public List<Card> getCompleteListLevel2(){
        List<Card> completeList = new ArrayList<>();
        for(Card card:getCompleteList()){
            if(card.isLevelTwo()){
                completeList.add(card);
            }
        }
        return completeList;
    }

    /**
     * Constructor of JsonCardParsing,it builds all Card present in Game
     */
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
            space.setName(connectorKey);
        }
        setSpaceCards(openSpaceList);

        ArrayList<StationCard> stationCardList = new ArrayList<>();
        Type typeStation = new TypeToken<Map<String,StationCardParse>>(){}.getType();
        Map<String,StationCardParse> stationCards = gson.fromJson(jsonCards.stationCards, typeStation);
        for (String connectorKey : stationCards.keySet()) {
            StationCardParse stationCardParse = stationCards.get(connectorKey);
            StationCard station = new StationCard(stationCardParse.levelTwo,stationCardParse.used,stationCardParse.crewNumberNeeded,stationCardParse.blocks,stationCardParse.daysToLose);
            stationCardList.add(station);
            station.setName(connectorKey);
        }

        setStationCards(stationCardList);

        ArrayList<ShipCard> shipCardList = new ArrayList<>();
        Type shipCardType = new TypeToken<Map<String, ShipCardParse>>(){}.getType();
        Map<String, ShipCardParse> shipCards = gson.fromJson(jsonCards.ShipCards, shipCardType);
        for (String connectorKey : shipCards.keySet()) {
            ShipCardParse ShipCardParse = shipCards.get(connectorKey);
            ShipCard ship = new ShipCard(ShipCardParse.levelTwo,ShipCardParse.used,ShipCardParse.crewNumberLost,ShipCardParse.credits,ShipCardParse.daysToLose);
            shipCardList.add(ship);
            ship.setName(connectorKey);
        }
        setShipCards(shipCardList);

        ArrayList<SlaversCard> slaversCardList = new ArrayList<>();
        Type slaversCardType = new TypeToken<Map<String,SlaversCardParse>>(){}.getType();
        Map<String,SlaversCardParse> slaversCards = gson.fromJson(jsonCards.slaversCards, slaversCardType);
        for (String connectorKey : slaversCards.keySet()) {
            SlaversCardParse slaversCardParse = slaversCards.get(connectorKey);
            SlaversCard slaver = new SlaversCard(slaversCardParse.levelTwo,slaversCardParse.used,slaversCardParse.firepower,slaversCardParse.credits,slaversCardParse.crewLost,slaversCardParse.daysToLose);
            slaversCardList.add(slaver);
            slaver.setName(connectorKey);
        }
        setSlaversCards(slaversCardList);

        ArrayList<SmugglersCard> smugglersCardList = new ArrayList<>();
        Type smugglersCardType = new TypeToken<Map<String, SmugglersCardParse>>(){}.getType();
        Map<String, SmugglersCardParse> smuglersCards = gson.fromJson(jsonCards.smugglersCards, smugglersCardType);
        for (String connectorKey : smuglersCards.keySet()) {
            SmugglersCardParse smugglersCardParse = smuglersCards.get(connectorKey);
            SmugglersCard smuggler = new SmugglersCard(smugglersCardParse.levelTwo,smugglersCardParse.used,smugglersCardParse.firepower,smugglersCardParse.blocks,smugglersCardParse.lostBlocksNumber,smugglersCardParse.daysToLose);
            smugglersCardList.add(smuggler);
            smuggler.setName(connectorKey);
        }
        setSmugglersCards(smugglersCardList);

        ArrayList<StardustCard> stardustCardList = new ArrayList<>();
        Type stardustCard = new TypeToken<Map<String,StardustCardParse>>(){}.getType();
        Map<String,StardustCardParse> stardustCards = gson.fromJson(jsonCards.stardustCards, stardustCard);
        for (String connectorKey : stardustCards.keySet()) {
            StardustCardParse  stardustCardParse = stardustCards.get(connectorKey);
            StardustCard stardust = new StardustCard(stardustCardParse.levelTwo,stardustCardParse.used);
            stardustCardList.add(stardust);
            stardust.setName(connectorKey);
        }
        setStardustCards(stardustCardList);

        ArrayList<EpidemicCard> epidemicCardList = new ArrayList<>();
        Type epidemicCard = new TypeToken<Map<String,EpidemicCardParse>>(){}.getType();
        Map<String,EpidemicCardParse> epidemicCards = gson.fromJson(jsonCards.epidemicCards, epidemicCard);
        for (String connectorKey : epidemicCards.keySet()) {
            EpidemicCardParse epidemicCardParse = epidemicCards.get(connectorKey);
            EpidemicCard epidemic = new EpidemicCard(epidemicCardParse.levelTwo,epidemicCardParse.used);
            epidemicCardList.add(epidemic);
            epidemic.setName(connectorKey);
        }
        setEpidemicCards(epidemicCardList);

        ArrayList<PlanetsCard> planetsCardList = new ArrayList<>();

        Type planetsCardType = new TypeToken<Map<String, PlanetsCardParse>>() {}.getType();
        Map<String, PlanetsCardParse> planetsCards = gson.fromJson(jsonCards.planetsCards, planetsCardType);

        for (String connectorKey : planetsCards.keySet()) {
            PlanetsCardParse parseCard = planetsCards.get(connectorKey);

            List<Planet> planetList = new ArrayList<>();
            for (PlanetParse planetParse : parseCard.planets) {
                planetList.add(new Planet(planetParse.blocks));
            }

            PlanetsCard planet = new PlanetsCard(parseCard.levelTwo, parseCard.used, planetList, parseCard.daysToLose);
            planetsCardList.add(planet);
            planet.setName(connectorKey);
        }
        setPlanetsCards(planetsCardList);

        ArrayList<MeteorsCard> meteorsCardList = new ArrayList<>();

        Type meteorsCardType = new TypeToken<Map<String, MeteorsCardParse>>() {}.getType();
        Map<String, MeteorsCardParse> meteorsCards = gson.fromJson(jsonCards.meteorsCards, meteorsCardType);

        for (String connectorKey : meteorsCards.keySet()) {
            MeteorsCardParse meteorParseCard = meteorsCards.get(connectorKey);

            List<Meteor> meteorList = new ArrayList<>();
            for (MeteorParse meteorParse : meteorParseCard.meteors) {
                meteorList.add(new Meteor(meteorParse.bigMeteor, Direction.valueOf(meteorParse.direction), RowOrColumn.valueOf(meteorParse.rowOrColumn)));
            }

            MeteorsCard meteor = new MeteorsCard(meteorParseCard.levelTwo, meteorParseCard.used, meteorList);
            meteorsCardList.add(meteor);
            meteor.setName(connectorKey);
        }

        setMeteorsCards(meteorsCardList);

        ArrayList<PiratesCard> piratesCardList = new ArrayList<>();

        Type piratesCardType = new TypeToken<Map<String, PiratesCardParse>>() {}.getType();
        Map<String, PiratesCardParse> piratesCards = gson.fromJson(jsonCards.piratesCards, piratesCardType);
        for (String connectorKey : piratesCards.keySet()) {
            PiratesCardParse pirateParseCard = piratesCards.get(connectorKey);

            List<Cannonball> cannonball = new ArrayList<>();
            for(CannonballParse cannonballParse: pirateParseCard.cannonballList){
                cannonball.add(new Cannonball(cannonballParse.bigCannonball,Direction.valueOf(cannonballParse.direction),RowOrColumn.valueOf(cannonballParse.rowOrColumn)));
            }

            PiratesCard pirate = new PiratesCard(pirateParseCard.levelTwo,pirateParseCard.used,pirateParseCard.firepower,pirateParseCard.credits,pirateParseCard.daysToLose,cannonball);
            piratesCardList.add(pirate);
            pirate.setName(connectorKey);
        }

        setPiratesCards(piratesCardList);

        ArrayList<CombatZoneCardL> combatZoneLCardList = new ArrayList<>();
        Type battleZoneCardLType = new TypeToken<Map<String, BattleZoneCardLParse>>(){}.getType();
        Map<String, BattleZoneCardLParse> combatZoneCards = gson.fromJson(jsonCards.combatZoneCardsL, battleZoneCardLType);

        for(String connectorKey : combatZoneCards.keySet()){
            BattleZoneCardLParse battleZoneCardLParse = combatZoneCards.get(connectorKey);
            List<Cannonball> cannonball2 = new ArrayList<>();
            for(CannonballParse cannonballParse: battleZoneCardLParse.cannonballList){
                cannonball2.add(new Cannonball(cannonballParse.bigCannonball,Direction.valueOf(cannonballParse.direction),RowOrColumn.valueOf(cannonballParse.rowOrColumn)));
            }
            CombatZoneCardL combatZoneCard = new CombatZoneCardL(battleZoneCardLParse.levelTwo,battleZoneCardLParse.used, cannonball2);
            combatZoneLCardList.add(combatZoneCard);
            combatZoneCard.setName(connectorKey);
        }

        setCombatZoneLCards(combatZoneLCardList);

        ArrayList<CombatZoneCardNotL> combatZoneNotLCardList = new ArrayList<>();
        Type battleZoneCardNotLType = new TypeToken<Map<String, BattleZoneCardNotLParse>>(){}.getType();
        Map<String, BattleZoneCardNotLParse> combatZoneCardsNotL = gson.fromJson(jsonCards.combatZoneCardsNotL, battleZoneCardNotLType);

        for (String connectorKey : combatZoneCardsNotL.keySet()) {
            BattleZoneCardNotLParse battleZoneCardNotLParse = combatZoneCardsNotL.get(connectorKey);
            List<Cannonball> cannonball2 = new ArrayList<>();
            for (CannonballParse cannonballParse : battleZoneCardNotLParse.cannonballList) {
                cannonball2.add(new Cannonball(cannonballParse.bigCannonball, Direction.valueOf(cannonballParse.direction), RowOrColumn.valueOf(cannonballParse.rowOrColumn)));
            }
            CombatZoneCardNotL combatZoneCard = new CombatZoneCardNotL(battleZoneCardNotLParse.levelTwo, battleZoneCardNotLParse.used, cannonball2);
            combatZoneNotLCardList.add(combatZoneCard);
            combatZoneCard.setName(connectorKey);
        }

        setCombatZoneNotLCards(combatZoneNotLCardList);
    }
}