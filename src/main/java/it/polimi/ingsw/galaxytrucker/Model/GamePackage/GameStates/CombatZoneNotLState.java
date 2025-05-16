package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CombatZoneCardNotL;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;

import java.io.Serializable;

public class CombatZoneNotLState extends CombatZoneState implements Serializable {
    public CombatZoneNotLState(Game game, CombatZoneCardNotL card) {
        super(game, card);
    }

    public void init(){
        super.init();
    }
}
