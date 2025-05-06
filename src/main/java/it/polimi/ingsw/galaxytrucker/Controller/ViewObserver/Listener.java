package it.polimi.ingsw.galaxytrucker.Controller.ViewObserver;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Hourglass;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.TilePile;

public interface Listener {
    void update(Player player);
    void update(Ship ship);
    void update(Hourglass hourglass);
    void update(Game game);
    void update(TilePile tilePile);
}
