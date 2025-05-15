package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CombatZoneCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.EventHandler;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;

//todo understand
public abstract class CombatZoneState extends TravellingState implements Serializable {

    public CombatZoneState(Game game, CombatZoneCard card) {
        super(game, card);
    }
        protected Player currentLoser;
        protected CombatZoneChallenge currentChallenge;
        protected CombatZonePenalty currentPenalty;
        protected CombatZoneCard currentCard;

}
