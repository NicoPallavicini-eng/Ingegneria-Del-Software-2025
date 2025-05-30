package it.polimi.ingsw.galaxytrucker.View;

import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;

public interface UIInterface {
    void start();
    void printTitle();
    void setNickname(String nickname);
    void viewLeaderboard(Game game);
    void printMessage(String message);
    void viewTilePile(Game game);
    void printShips(Game game);
    void printVoid();
    void printMyShip(Game game, String nickname);
    void printGuide();
    void printActualShip(Ship ship);
    void printTile(Tile tile);
    void viewCard(Game game);

    static void printHelpMessage() {
        // Optional: Implement help popup or dialog in concrete class
    }
}
