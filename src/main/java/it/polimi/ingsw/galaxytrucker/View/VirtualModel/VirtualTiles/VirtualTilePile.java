package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualTiles;

import it.polimi.ingsw.galaxytrucker.Controller.ViewObserver.Listener;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Hourglass;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.TilePile;

import java.util.List;

public class VirtualTilePile implements Listener {
    private List<VirtualTile> tilePile;

    public VirtualTilePile(List<VirtualTile> tilePile) {
        this.tilePile = tilePile;
    }

    public VirtualTilePile getTilePile() {
        return this;
    }

    @Override
    public void update(TilePile tilePile) {
        // set all variables
    }

    public void update(Hourglass hourglass) {}
    public void update(Ship ship) {}
    public void update(Player player) {}
    public void update(Game game) {}
}
