package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGameEvents;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualPlayerShip.Player;

//not sure if it is necessary

//CHANGED: INSERTED ROW AND COLUMN OF ONE TILE THAT IS PART OF THE SUBSHIP YOU WANT TO KEEP
public record ChooseSubShipEvent(Player player, /*int SubshipIndex*/ int row, int col) implements GameEvent {
}
