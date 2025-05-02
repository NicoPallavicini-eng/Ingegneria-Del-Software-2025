package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

public record ViewDeckEvent(Player player, int deckIndex) implements GameEvent {
}
