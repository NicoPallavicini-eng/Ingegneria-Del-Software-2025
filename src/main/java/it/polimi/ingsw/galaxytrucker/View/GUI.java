package it.polimi.ingsw.galaxytrucker.View;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Cannonball;
import it.polimi.ingsw.galaxytrucker.Model.Cards.Card;
import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.TravellingState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.ConnectorType;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;
import it.polimi.ingsw.galaxytrucker.Network.Client.RMIClient;
import it.polimi.ingsw.galaxytrucker.Network.Client.SocketClient;
import it.polimi.ingsw.galaxytrucker.Network.Client.VirtualClient;
import it.polimi.ingsw.galaxytrucker.SceneManager;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.BuildingSceneUserShip;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.TravellingSceneDefault;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.WaitingScene;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

/**
 * The GUI class represents the graphical user interface for the Galaxy Trucker game.
 * It extends the JavaFX Application class and implements the UI and Serializable interfaces.
 * This class manages the game's main window, scenes, and interactions with the server.
 */
public class GUI extends Application implements UI, Serializable {

    // Static fields used for initialization before launch()
    private static Game staticGame;
    private static GUI instance;
    private static VirtualClient staticRmiClient;
    private static SocketClient staticSocketClient;

    // Instance fields
    private Game game;
    private String nickname;
    private Stage stage;
    private SceneManager sceneManager;
    private VirtualClient rmiClient;
    private SocketClient socketClient;
    private static Boolean started;

    // Called from outside to launch the GUI
    /**
     * Launches the GUI application with the specified game and clients.
     *
     * @param game        The game instance to be used in the GUI.
     * @param rmiClient   The RMI client for server communication.
     * @param socketClient The Socket client for server communication.
     */
    public static void launchGUI(Game game, VirtualClient rmiClient, SocketClient socketClient) {
        staticGame = game;
        staticRmiClient = rmiClient;
        staticSocketClient = socketClient;
        started = false;
        if (socketClient != null) {
            new Thread(() -> {
                try {
                    socketClient.runVirtualServer();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        Thread t = new Thread (() -> {
            Application.launch(GUI.class);
        });
        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            t.interrupt();
        }
    }

    /**
     * Default constructor required by JavaFX.
     */
    public GUI() {
        // JavaFX requires a no-args constructor
    }

    /**
     * Starts the JavaFX application and initializes the primary stage.
     *
     * @param primaryStage The primary stage for the JavaFX application.
     */
    @Override
    public void start(Stage primaryStage) {
        instance = this;
        this.game = staticGame;
        this.stage = primaryStage;
        this.rmiClient = staticRmiClient;
        this.socketClient = staticSocketClient;
        this.sceneManager = new SceneManager(this.game, this.stage, this.rmiClient, this.socketClient);

        stage.setTitle("Galaxy Trucker");
        // TODO: set the sceneManager's initial scene
        started = true;
        stage.show();
        stage.setOnCloseRequest(e -> {
            if (rmiClient != null){
                try{
                    rmiClient.getServer().showMessage(rmiClient + "/disconnect");
                    rmiClient.getServer().handleUserInput(rmiClient, "/disconnect");
                    String disconnectionMessage = "/disconnect";
                    System.setIn(new ByteArrayInputStream(disconnectionMessage.getBytes()));
                    rmiClient.close();
                } catch (RemoteException ex) {
                    try {
                        rmiClient.close();
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }
                    throw new RuntimeException(ex);
                }
            } else if (socketClient != null){
                try{
                    socketClient.getServerSocket().sendMessageToServer("/disconnect", null);
                    String disconnectionMessage = "/disconnect";
                    System.setIn(new ByteArrayInputStream(disconnectionMessage.getBytes()));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else{
                throw new RuntimeException("No client connection available.");
            }
        });

        if (staticRmiClient != null) {
            if (staticRmiClient instanceof RMIClient rmiRealClient) {
                rmiRealClient.setGUI(this);
            }
        } else if (staticSocketClient != null) {
            staticSocketClient.setGUI(this);
        }
    }
    /**
     * Retrieves the singleton instance of the GUI.
     *
     * @return The current instance of the GUI.
     */
    public static GUI getInstance() {
        return instance;
    }

    /**
     * Sets the nickname of the player.
     *
     * @param nickname The player's nickname.
     */
    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public void printTitle() {}

    @Override
    public void viewLeaderboard(Game game) {}


    /**
     * Displays a message in the GUI.
     *
     * @param message The message to be displayed.
     */
    @Override
    public void printMessage(String message) {
        javafx.application.Platform.runLater(() -> {
            javafx.scene.control.Alert messageFromServer = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
            messageFromServer.setTitle("Update");
            messageFromServer.setHeaderText(null);
            messageFromServer.setContentText(message);
            messageFromServer.showAndWait();
        });
        // TODO: Show message (popup, status bar, etc.)
    }

    /**
     * Handles invalid commands and displays an error message.
     *
     * @param error The error message to be displayed.
     */
    @Override
    public void invalidCommand(String error){
        javafx.application.Platform.runLater(() -> {
            javafx.scene.control.Alert errorAlert = new javafx.scene.control.Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Invalid Command");
            errorAlert.setContentText(error);
            errorAlert.showAndWait();
        });
        throw new IllegalGUIEventException(error);
    }

    /**
     * Displays the tile pile in the GUI.
     *
     * @param game The game instance containing tile pile data.
     */
    @Override
    public void viewTilePile(Game game) {
        sceneManager.updateGame(game, null);
    }
    /**
     * Displays all ships graphically in the GUI.
     *
     * @param game The game instance containing ship data.
     */
    @Override
    public void printShips(Game game) {
        sceneManager.updateGame(game, null);
        // TODO: Display all ships graphically
    }

    @Override
    public void printVoid() {
        // Optional: Possibly empty or GUI spacing logic
    }
    /**
     * Displays the current player's ship in the GUI.
     *
     * @param game     The game instance containing ship data.
     * @param nickname The nickname of the current player.
     */
    @Override
    public void printMyShip(Game game, String nickname) {
        sceneManager.updateGame(game, null);
    }

    @Override
    public void printGuide() {}

    @Override
    public void printActualShip(Ship ship) {}

    @Override
    public void printTile(Tile tile) {}

    /**
     * Displays the cards in the GUI.
     *
     * @param game The game instance containing card data.
     */
    @Override
    public void viewCard(Game game) {
        BuildingSceneUserShip buildingSceneUserShip = new BuildingSceneUserShip(game, game.getListOfActivePlayers().getFirst().getNickname(), sceneManager);
        sceneManager.updateGame(game, null);
        sceneManager.next(buildingSceneUserShip);
    }

    @Override
    public void viewDeck(Game game, int index) {}

    // PRIVATE HELPER METHODS (not in interface)

    private void printShipHeaders(Color color) {
        // TODO: Implement if needed
    }

    private void printShipFooters(Color color) {
        // TODO: Implement if needed
    }

    private void printPileHeaders() {
        // TODO: Implement if needed
    }

    private void printPileFooters() {
        // TODO: Implement if needed
    }

    private List<String> checkConnectors(List<ConnectorType> connectors) {
        // TODO: Convert connectors for GUI visuals
        return null;
    }

    private void printBlocks(List<Integer> blocks) {
        // TODO: Visualize blocks in GUI
    }

    private void printBalls(List<Cannonball> balls) {
        // TODO: Visualize cannonballs/ammo in GUI
    }

    private void printVoidTile(int i, int j) {
        // TODO: Optional for GUI
    }

    private void printShipDetails(Ship ship) {
        // TODO: Show detailed ship info
    }

    private List<List<String>> appendReserved(Ship ship) {
        // TODO: Convert reserved tiles info for GUI
        return null;
    }

    private List<List<String>> appendHand(Ship ship) {
        // TODO: Convert hand tiles info for GUI
        return null;
    }

    private void checkCard(Card card) {
        // TODO: Interpret card and update GUI
    }

    public void printHelpMessage() {
        // TODO: Show help dialog/popup in GUI
    }
    /**
     * Updates the game state in the GUI.
     *
     * @param gameSend The updated game instance.
     */

    @Override
    public void updateGame(Game gameSend) {
        this.game = gameSend;
        this.sceneManager.updateGame(game, null);
    }
    /**
     * Advances to the next scene in the GUI based on the game state and message.
     *
     * @param game    The game instance.
     * @param message The message indicating the next scene.
     */

    @Override
    public void nextScene(Game game, String message){
        updateGame(game);
    }
}
