package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CombatZoneCardNotL;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.EventHandler;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.BatteryTile;

import java.io.Serializable;

public class CombatZoneNotLState extends CombatZoneState implements Serializable {

    private CombatZoneCardNotL card;
    private int cargoToLose;

    public CombatZoneNotLState(Game game, CombatZoneCardNotL card) {
        super(game, card);
        currentCard = card;
    }

    public void init(){
        super.init();
        currentChallenge = CombatZoneChallenge.CANNONS;
        currentPenalty = CombatZonePenalty.DAYS;
        cargoToLose = currentCard.getCargoLost();
    }

    protected void cannonsPenalty(){
        super.cannonsPenalty();
        EventHandler.moveBackward(currentLoser.getShip(), currentCard.getDaysLost(), game);
        nextChallenge();
    }

    protected void enginesPenalty(){
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


    protected void peoplePenalty(){
        super.peoplePenalty();
        cannonballStorm();
    }

    protected void nextChallenge(){
        super.init();
        currentLoser = null;
        switch(currentChallenge){
            case CANNONS -> {
                currentChallenge = CombatZoneChallenge.ENGINES;
                currentPenalty = CombatZonePenalty.CARGO;
            }
            case ENGINES -> {
                currentChallenge = CombatZoneChallenge.PEOPLE;
                currentPenalty = CombatZonePenalty.CANNONBALLS;
                peoplePenalty();}
            case PEOPLE -> {
                next();
            }
        }
    }


}
