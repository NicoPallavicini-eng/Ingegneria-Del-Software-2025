package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CombatZoneCard;
import it.polimi.ingsw.galaxytrucker.Model.Cards.CombatZoneCardL;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.EventHandler;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.util.Optional;
import java.util.OptionalInt;

public class CombatZoneLState extends CombatZoneState {
    protected CombatZoneCardL currentCard;

    public CombatZoneLState(Game game, CombatZoneCard card) {
        super(game, card);
    }

    public void init(){
        super.init();
        currentChallenge = CombatZoneChallenge.PEOPLE;
        currentPenalty = CombatZonePenalty.DAYS;
        lessPeople();
    }

    private void lessPeople(){
        OptionalInt min = game.getListOfActivePlayers().stream()
                .mapToInt(p -> p.getShip().getNumberOfInhabitants())
                .min();
        game.sortListOfActivePlayers();
        for(Player p : game.getListOfActivePlayers()){
            if(p.getShip().getNumberOfInhabitants() == min.getAsInt()){
                currentLoser = p;
                break;
            }
        }
        EventHandler.moveBackward(currentLoser.getShip(), currentCard.getDaysLost(), game);
        currentLoser = null;
        currentChallenge = CombatZoneChallenge.ENGINES;
        currentPenalty = CombatZonePenalty.PEOPLE;
        super.init();
    }

}
