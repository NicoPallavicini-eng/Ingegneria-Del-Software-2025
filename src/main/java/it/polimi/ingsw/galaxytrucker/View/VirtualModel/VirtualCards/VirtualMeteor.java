package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualCards;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualDirection;

public record VirtualMeteor(boolean bigMeteor, VirtualDirection direction, VirtualRowOrColumn rowOrColumn) {
}