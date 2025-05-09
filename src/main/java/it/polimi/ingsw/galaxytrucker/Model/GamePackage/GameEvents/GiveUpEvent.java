package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;

//end of card
public record GiveUpEvent(Player player) implements GameEvent, Serializable {
}
