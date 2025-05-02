package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

//not sure if it is necessary
public record ChooseSubShipEvent(Player player, int SubshipIndex) implements GameEvent {
}
