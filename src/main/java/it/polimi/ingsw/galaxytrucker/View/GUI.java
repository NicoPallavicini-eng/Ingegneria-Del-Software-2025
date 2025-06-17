package it.polimi.ingsw.galaxytrucker.View;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Cannonball;
import it.polimi.ingsw.galaxytrucker.Model.Cards.Card;
import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.ConnectorType;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;
import it.polimi.ingsw.galaxytrucker.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;


public class GUI extends Application implements UI {
    private Game game;
    private String nickname;
    private Stage stage;
    private SceneManager sceneManager;
    private boolean isFirstPlayer;

    public GUI(Game game, String nickname) {
        this.game = game;
        this.isFirstPlayer = game.getListOfActivePlayers().size() == 1;
        this.nickname = nickname; // TODO change without nickname
        this.stage = new Stage();
        this.sceneManager = new SceneManager(this.game, this.stage, this.nickname, this.isFirstPlayer);
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
        // TODO: Update nickname field in GUI if applicable
    }

    @Override
    public void start(Stage primaryStage) {
        // TODO: Implement start logic (launch GUI scene, show window)
    }

    @Override
    public void printTitle() {
        // TODO: Display the game title in GUI
    }

    @Override
    public void viewLeaderboard(Game game) {
        // TODO: Show leaderboard in GUI
    }

    @Override
    public void printMessage(String message) {
        // TODO: Show message (popup, status bar, etc.)
    }

    @Override
    public void viewTilePile(Game game) {
        // TODO: Visualize the tile pile
    }

    @Override
    public void printShips(Game game) {
        // TODO: Display all ships graphically
    }

    @Override
    public void printVoid() {
        // Optional: Possibly empty or GUI spacing logic
    }

    @Override
    public void printMyShip(Game game, String nickname) {
        // TODO: Show current player's ship
    }

    @Override
    public void printGuide() {
        // TODO: Show guide/help dialog or panel
    }

    @Override
    public void printActualShip(Ship ship) {
        // TODO: Show ship details if needed
    }

    @Override
    public void printTile(Tile tile) {
        // TODO: Render a single tile visually
    }

    @Override
    public void viewCard(Game game) {
        // TODO: Show cards info
    }

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

    // Static helper (optional)

    public void printHelpMessage() {
        // TODO: Show help dialog/popup in GUI
    }

    @Override
    public void updateGame(Game game) {
        this.game = game;
    }
}
