package it.polimi.ingsw.galaxytrucker.View;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;
import it.polimi.ingsw.galaxytrucker.Network.Client.RMIClient;
import it.polimi.ingsw.galaxytrucker.Network.Client.SocketClient;
import it.polimi.ingsw.galaxytrucker.Network.Client.VirtualClient;
import javafx.stage.Stage;
/**
 * The UI interface defines the contract for user interfaces in the Galaxy Trucker game.
 * It provides methods for displaying game information, handling user interactions,
 * and updating the game state.
 */
public interface UI {

    /**
     * Starts the user interface.
     *
     * @param primaryStage The primary stage for the JavaFX application.
     */
    void start(Stage primaryStage);

    /**
     * Displays the game title.
     */
    void printTitle();
    /**
     * Sets the nickname of the player.
     *
     * @param nickname The player's nickname.
     */
    void setNickname(String nickname);
    /**
     * Displays the leaderboard of the game.
     *
     * @param game The game instance containing leaderboard data.
     */
    void viewLeaderboard(Game game);

    /**
     * Prints a message to the user interface.
     *
     * @param message The message to be displayed.
     */
    void printMessage(String message);
    /**
     * Displays the tile pile in the game.
     *
     * @param game The game instance containing tile pile data.
     */
    void viewTilePile(Game game);
    /**
     * Displays all players' ships in the game.
     *
     * @param game The game instance containing ship data.
     */
    void printShips(Game game);

    /**
     * Prints an empty line or space in the user interface.
     */
    void printVoid();
    /**
     * Displays the current player's ship.
     *
     * @param game     The game instance containing ship data.
     * @param nickname The nickname of the current player.
     */
    void printMyShip(Game game, String nickname);

    /**
     * Prints a guide for the game, including tile and connector descriptions.
     */
    void printGuide();
    /**
     * Displays the details of a specific ship.
     *
     * @param ship The ship to be displayed.
     */
    void printActualShip(Ship ship);
    /**
     * Displays the specified deck of cards in the game.
     *
     * @param game  The game instance containing the deck.
     * @param index The index of the deck to be displayed.
     */
    void viewDeck(Game game, int index);
    /**
     * Prints a single tile in the user interface.
     *
     * @param tile The tile to be printed.
     */
    void printTile(Tile tile);
    /**
     * Displays the current card in the game.
     *
     * @param game The game instance containing the current card.
     */
    void viewCard(Game game);
    /**
     * Prints a help message with available commands and their descriptions.
     */
    void printHelpMessage();
    /**
     * Updates the game instance in the user interface.
     *
     * @param game The updated game instance.
     */
    void updateGame(Game game);
    /**
     * Handles invalid commands and displays an error message.
     *
     * @param error The error message to be displayed.
     */
    void invalidCommand(String error);
    /**
     * Advances to the next scene in the game.
     *
     * @param game    The game instance.
     * @param message The message indicating the next scene.
     */
    void nextScene(Game game, String message);
    /**
     * Launches the GUI application with the specified game and clients.
     *
     * @param game        The game instance to be used in the GUI.
     * @param rmiClient   The RMI client for server communication.
     * @param socketClient The Socket client for server communication.
     */
    static void launchGUI(Game game, VirtualClient rmiClient, SocketClient socketClient) { /* default empty */ }
}
