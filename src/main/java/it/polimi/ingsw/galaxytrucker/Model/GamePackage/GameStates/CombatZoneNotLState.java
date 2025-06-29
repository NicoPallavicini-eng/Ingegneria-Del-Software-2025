package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CombatZoneCardNotL;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.EventHandler;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.BatteryTile;

import java.io.Serializable;

/**
 * This class represent one of the two types of CombatZone Card
 */
public class CombatZoneNotLState extends CombatZoneState implements Serializable {

    private CombatZoneCardNotL card;
    private int cargoToLose;

    /**
     * @param game
     * @param card
     */
    public CombatZoneNotLState(Game game, CombatZoneCardNotL card) {
        super(game, card);
        currentCard = card;
    }

    /**
     * This function initialize CombatZoneNotLState
     */
    public void init(){
        super.init();
        game.notifyObservers(game, "combatZoneNotL");
        game.notifyObservers(game, "combatCannonChallenge");
        currentChallenge = CombatZoneChallenge.CANNONS;
        currentPenalty = CombatZonePenalty.DAYS;
        cargoToLose = currentCard.getCargoLost();
    }
    /**
     * This function manages cannons penalty
     */
    protected void cannonsPenalty(){
        game.notifyObservers(game, "cannonsPenalty");
        super.cannonsPenalty();
        EventHandler.moveBackward(currentLoser.getShip(), currentCard.getDaysLost(), game);
        nextChallenge();
    }
    /**
     * This function manages engines penalty
     */
    protected void enginesPenalty(){
        game.notifyObservers(game, "enginesPenalty");
        super.enginesPenalty();
        Player p = currentLoser;
        long available = p.getShip().getListOfCargo().stream()
                .flatMap(c -> c.getTileContent().stream())
                .count();
        if(available <= cargoToLose){
            p.getShip().removeAllCargo();
            available = p.getShip().getListOfBattery().stream()
                    .mapToInt(BatteryTile::getSlotsFilled)
                    .sum();
            if(available <= cargoToLose) {
                p.getShip().removeAllBatteries();
                nextChallenge();
            }
            else if(cargoToLose == 0){
                nextChallenge();
            }
        }
    }

    /**
     * This function manages people penalty
     */
    protected void peoplePenalty(){
        game.notifyObservers(game, "peoplePenalty");
        super.peoplePenalty();
        cannonballStorm();
    }
    /**
     * this function change a challenge of this State
     */
    protected void nextChallenge(){
        super.init();
        currentLoser = null;
        switch(currentChallenge){
            case CANNONS -> {
                game.notifyObservers(game, "combatEnginesChallenge");
                currentChallenge = CombatZoneChallenge.ENGINES;
                currentPenalty = CombatZonePenalty.CARGO;
            }
            case ENGINES -> {
                game.notifyObservers(game, "combatPeopleChallenge");
                currentChallenge = CombatZoneChallenge.PEOPLE;
                currentPenalty = CombatZonePenalty.CANNONBALLS;
                peoplePenalty();}
            case PEOPLE -> {
                next();
            }
        }
    }


}
