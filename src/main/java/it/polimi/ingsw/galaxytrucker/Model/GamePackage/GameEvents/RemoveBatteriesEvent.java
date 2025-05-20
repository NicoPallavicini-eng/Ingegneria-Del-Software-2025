package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.util.List;
//from a single tile
public record RemoveBatteriesEvent(Player player, List<Integer> batteries) implements GameEvent {
}
