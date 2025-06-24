package it.polimi.ingsw.galaxytrucker.View;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;
import it.polimi.ingsw.galaxytrucker.Network.Client.RMIClient;
import it.polimi.ingsw.galaxytrucker.Network.Client.SocketClient;
import it.polimi.ingsw.galaxytrucker.Network.Client.VirtualClient;
import javafx.stage.Stage;

public interface UI {
    void start(Stage primaryStage);
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
    void viewDeck(Game game, int index);
    void printTile(Tile tile);
    void viewCard(Game game);
    void printHelpMessage();
    void updateGame(Game game);

    static void launchGUI(Game game, VirtualClient rmiClient, SocketClient socketClient) { /* default empty */ }
}
