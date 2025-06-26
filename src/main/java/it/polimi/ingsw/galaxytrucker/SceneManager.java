package it.polimi.ingsw.galaxytrucker;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Network.Client.SocketClient;
import it.polimi.ingsw.galaxytrucker.Network.Client.VirtualClient;
import it.polimi.ingsw.galaxytrucker.Network.Server.VirtualServer;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.util.Objects;

public class SceneManager extends Application {
    private static Stage primaryStage;
    private Game game;
    private WaitingScene waitingScene;
    private MyScene currentScene;
    private BuildingSceneUserShip userShipScene = null;
    private BuildingSceneTilePile tilePileScene = null;
    private BuildingSceneOthersShip othersShipScene = null;
    private BuildingSceneBoard boardScene = null;
    private TravellingSceneDefault travellingSceneDefault = null;
    private TravellingSceneOthersShip travellingSceneOthersShip = null;
    private FinalScene finalScene = null;
    private final VirtualClient rmiClient;
    private final SocketClient socketClient;
    private String nickname;
    private final VirtualServer server;

    public SceneManager(Game game, Stage stage, VirtualClient rmiClient, SocketClient socketClient) {
        this.game = game;
        this.rmiClient = rmiClient;
        this.socketClient = socketClient;
        try {
            this.server = rmiClient.getServer();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        primaryStage = stage;
        start(primaryStage); // stage comes from instantiation of SceneManager
    }

    @Override
    public void start(Stage stage) {
        waitingScene = new WaitingScene(game, this);
        primaryStage = stage;
        primaryStage.setScene(waitingScene.getScene());
        primaryStage.setResizable(true); // TODO choose if keep
        primaryStage.setTitle("Waiting State");
        primaryStage.getIcons().add(new javafx.scene.image.Image(getClass().getResource("/Images/misc/window_simple_icon.png").toExternalForm()));
        primaryStage.show();
    }

    public VirtualClient getRmiClient() {
        return rmiClient;
    }

    public SocketClient getSocketClient() {
        return socketClient;
    }

    public VirtualServer getServer() {return server;}

    public BuildingSceneOthersShip getOthersShipScene() {
        return othersShipScene;
    }

    public BuildingSceneTilePile getTilePileScene() {
        return tilePileScene;
    }

    public BuildingSceneUserShip getUserShipScene() {
        return userShipScene;
    }

    public BuildingSceneBoard getBoardScene() {
        return boardScene;
    }

    public TravellingSceneDefault getTravellingSceneDefault() {
        return travellingSceneDefault;
    }

    public TravellingSceneOthersShip getTravellingSceneOthersShip() {
        return travellingSceneOthersShip;
    }

    public FinalScene getFinalScene() {
        return finalScene;
    }

    public void setUserShipScene(BuildingSceneUserShip userShipScene) {
        this.userShipScene = userShipScene;
    }

    public void setTilePileScene(BuildingSceneTilePile tilePileScene) {
        this.tilePileScene = tilePileScene;
    }

    public void setOthersShipScene(BuildingSceneOthersShip othersShipScene) {
        this.othersShipScene = othersShipScene;
    }

    public void setBoardScene(BuildingSceneBoard boardScene) {
        this.boardScene = boardScene;
    }

    public void setTravellingSceneDefault(TravellingSceneDefault travellingSceneDefault) {
        this.travellingSceneDefault = travellingSceneDefault;
    }

    public void setTravellingSceneOthersShip(TravellingSceneOthersShip travellingSceneOthersShip) {
        this.travellingSceneOthersShip = travellingSceneOthersShip;
    }

    public void setFinalScene(FinalScene finalScene) {
        this.finalScene = finalScene;
    }

    public void setScene(Scene scene, MyScene currentScene) {
        primaryStage.setScene(scene);
        this.currentScene = currentScene;
    }

    public MyScene getScene() {
        return currentScene;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public void next(WaitingScene waitingScene) {
        BuildingSceneUserShip buildingSceneUserShip = new BuildingSceneUserShip(game, nickname, this);
        primaryStage.setTitle("Building State - User Ship");
        primaryStage.setScene(buildingSceneUserShip.getScene());
        primaryStage.show();
    }

    public void next(BuildingSceneUserShip buildingSceneUserShip) {
        TravellingSceneDefault travellingSceneDefault = new TravellingSceneDefault(game, nickname, this);
        primaryStage.setTitle("Travelling State - Default");
        primaryStage.setScene(travellingSceneDefault.getScene());
        primaryStage.show();
    }

    public void next(BuildingSceneTilePile buildingSceneTilePile) {
        TravellingSceneDefault travellingSceneDefault = new TravellingSceneDefault(game, nickname, this);
        primaryStage.setTitle("Travelling State - Default");
        primaryStage.setScene(travellingSceneDefault.getScene());
        primaryStage.show();
    }

    public void next(BuildingSceneBoard buildingSceneBoard) {
        TravellingSceneDefault travellingSceneDefault = new TravellingSceneDefault(game, nickname, this);
        primaryStage.setTitle("Travelling State - Default");
        primaryStage.setScene(travellingSceneDefault.getScene());
        primaryStage.show();
    }

    public void next(BuildingSceneOthersShip buildingSceneOthersShip) {
        TravellingSceneDefault travellingSceneDefault = new TravellingSceneDefault(game, nickname, this);
        primaryStage.setTitle("Travelling State - Default");
        primaryStage.setScene(travellingSceneDefault.getScene());
        primaryStage.show();
    }

    public void next(TravellingSceneDefault travellingSceneDefault) {
        FinalScene finalScene = new FinalScene(game, nickname, this);
        primaryStage.setTitle("Final State");
        primaryStage.setScene(finalScene.getScene());
        primaryStage.show();
    }

    public void next(TravellingSceneOthersShip travellingSceneOthersShip) {
        FinalScene finalScene = new FinalScene(game, nickname, this);
        primaryStage.setTitle("Final State");
        primaryStage.setScene(finalScene.getScene());
        primaryStage.show();
    }

    public void switchTravelling(TravellingSceneDefault travellingSceneDefault) {
        TravellingSceneOthersShip travellingSceneOthersShip;
        travellingSceneOthersShip = Objects.requireNonNullElseGet(this.travellingSceneOthersShip, () -> new TravellingSceneOthersShip(game, nickname, this));
        travellingSceneOthersShip.setTravellingSceneDefault(travellingSceneDefault);
        primaryStage.setTitle("Travelling State - Others' Ship");
        primaryStage.setScene(travellingSceneOthersShip.getScene());
        primaryStage.show();
    }

    public void switchTravelling(TravellingSceneOthersShip travellingSceneOthersShip) {
        TravellingSceneDefault travellingSceneDefault;
        travellingSceneDefault = Objects.requireNonNullElseGet(this.travellingSceneDefault, () -> new TravellingSceneDefault(game, nickname, this));
        travellingSceneDefault.setTravellingSceneOthersShip(travellingSceneOthersShip);
        primaryStage.setTitle("Travelling State - Default");
        primaryStage.setScene(travellingSceneDefault.getScene());
        primaryStage.show();
    }

    public void switchBuilding(BuildingSceneUserShip buildingSceneUserShip, String choice) {
        if (Objects.equals(choice, "TilePile")) {
            checkExistingTilePile();
        } else if (Objects.equals(choice, "OthersShip")) {
            checkExistingOthersShip();
        } else if (Objects.equals(choice, "Board")) {
            checkExistingBoard();
        }
    }

    public void switchBuilding(BuildingSceneBoard buildingSceneBoard, String choice) {
        if (Objects.equals(choice, "TilePile")) {
            checkExistingTilePile();
        } else if (Objects.equals(choice, "OthersShip")) {
            checkExistingOthersShip();
        } else if (Objects.equals(choice, "UserShip")) {
            checkExistingUserShip();
        }
    }

    public void switchBuilding(BuildingSceneTilePile buildingSceneTilePile, String choice) {
        if (Objects.equals(choice, "OthersShip")) {
            checkExistingOthersShip();
        } else if (Objects.equals(choice, "UserShip")) {
            checkExistingUserShip();
        } else if (Objects.equals(choice, "Board")) {
            checkExistingBoard();
        }
    }

    public void switchBuilding(BuildingSceneOthersShip buildingSceneOthersShip, String choice) {
        if (Objects.equals(choice, "UserShip")) {
            checkExistingUserShip();
        } else if (Objects.equals(choice, "TilePile")) {
            checkExistingTilePile();
        } else if (Objects.equals(choice, "Board")) {
            checkExistingBoard();
        }
    }

    private void checkExistingOthersShip() {
        BuildingSceneOthersShip buildingSceneOthersShip;
        buildingSceneOthersShip = Objects.requireNonNullElseGet(othersShipScene, () -> new BuildingSceneOthersShip(game, nickname, this));
        buildingSceneOthersShip.setBuildingSceneUserShip(Objects.requireNonNullElseGet(buildingSceneOthersShip.getBuildingSceneUserShip(), () -> userShipScene));
        buildingSceneOthersShip.setBuildingSceneTilePile(Objects.requireNonNullElseGet(buildingSceneOthersShip.getBuildingSceneTilePile(),
                () -> Objects.requireNonNullElseGet(tilePileScene, () -> new BuildingSceneTilePile(game, nickname, this))));
        buildingSceneOthersShip.setBuildingSceneBoard(Objects.requireNonNullElseGet(buildingSceneOthersShip.getBuildingSceneBoard(),
                () -> Objects.requireNonNullElseGet(boardScene, () -> new BuildingSceneBoard(game, nickname, this))));
        primaryStage.setTitle("Building State - Others' Ship");
        primaryStage.setScene(buildingSceneOthersShip.getScene());
        primaryStage.show();
    }

    private void checkExistingBoard() {
        BuildingSceneBoard buildingSceneBoard;
        buildingSceneBoard = Objects.requireNonNullElseGet(boardScene, () -> new BuildingSceneBoard(game, nickname, this));
        buildingSceneBoard.setBuildingSceneUserShip(Objects.requireNonNullElseGet(buildingSceneBoard.getBuildingSceneUserShip(), () -> userShipScene));
        buildingSceneBoard.setBuildingSceneTilePile(Objects.requireNonNullElseGet(buildingSceneBoard.getBuildingSceneTilePile(),
                () -> Objects.requireNonNullElseGet(tilePileScene, () -> new BuildingSceneTilePile(game, nickname, this))));
        buildingSceneBoard.setBuildingSceneOthersShip(Objects.requireNonNullElseGet(buildingSceneBoard.getBuildingSceneOthersShip(),
                () -> Objects.requireNonNullElseGet(othersShipScene, () -> new BuildingSceneOthersShip(game, nickname, this))));
        primaryStage.setTitle("Building State - Board");
        primaryStage.setScene(buildingSceneBoard.getScene());
        primaryStage.show();
    }

    private void checkExistingUserShip() {
        BuildingSceneUserShip buildingSceneUserShip;
        buildingSceneUserShip = Objects.requireNonNullElseGet(userShipScene, () -> new BuildingSceneUserShip(game, nickname, this));
        buildingSceneUserShip.setBuildingSceneOthersShip(Objects.requireNonNullElseGet(buildingSceneUserShip.getBuildingSceneOthersShip(),
                () -> Objects.requireNonNullElseGet(othersShipScene, () -> new BuildingSceneOthersShip(game, nickname, this))));
        buildingSceneUserShip.setBuildingSceneTilePile(Objects.requireNonNullElseGet(buildingSceneUserShip.getBuildingSceneTilePile(),
                () -> Objects.requireNonNullElseGet(tilePileScene, () -> new BuildingSceneTilePile(game, nickname, this))));
        buildingSceneUserShip.setBuildingSceneBoard(Objects.requireNonNullElseGet(buildingSceneUserShip.getBuildingSceneBoard(),
                () -> Objects.requireNonNullElseGet(boardScene, () -> new BuildingSceneBoard(game, nickname, this))));
        primaryStage.setTitle("Building State - User Ship");
        primaryStage.setScene(buildingSceneUserShip.getScene());
        primaryStage.show();
    }

    private void checkExistingTilePile() {
        BuildingSceneTilePile buildingSceneTilePile;
        buildingSceneTilePile = Objects.requireNonNullElseGet(tilePileScene, () -> new BuildingSceneTilePile(game, nickname, this));
        buildingSceneTilePile.setBuildingSceneUserShip(Objects.requireNonNullElseGet(buildingSceneTilePile.getBuildingSceneUserShip(), () -> userShipScene));
        buildingSceneTilePile.setBuildingSceneOthersShip(Objects.requireNonNullElseGet(buildingSceneTilePile.getBuildingSceneOthersShip(),
                () -> Objects.requireNonNullElseGet(othersShipScene, () -> new BuildingSceneOthersShip(game, nickname, this))));
        buildingSceneTilePile.setBuildingSceneBoard(Objects.requireNonNullElseGet(buildingSceneTilePile.getBuildingSceneBoard(),
                () -> Objects.requireNonNullElseGet(boardScene, () -> new BuildingSceneBoard(game, nickname, this))));
        primaryStage.setTitle("Building State - Tile Pile");
        primaryStage.setScene(buildingSceneTilePile.getScene());
        primaryStage.show();
    }

    public void updateGame(Game game, MyScene scene) {
        this.game = game;
        if (userShipScene != null && scene != userShipScene) {
            userShipScene.updateGame(game);
        }
        if (tilePileScene != null && scene != tilePileScene) {
            tilePileScene.updateGame(game);
        }
        if (boardScene != null && scene != boardScene) {
            boardScene.updateGame(game);
        }
        if (othersShipScene != null && scene != othersShipScene) {
            othersShipScene.updateGame(game);
        }
        if (waitingScene != null && scene != waitingScene) {
            waitingScene.updateGame(game);
        }
        if (travellingSceneDefault != null  && scene != travellingSceneDefault) {
            travellingSceneDefault.updateGame(game);
        }
        if (travellingSceneOthersShip != null && scene != travellingSceneOthersShip) {
            travellingSceneOthersShip.updateGame(game);
        }
        if (finalScene != null  && scene != finalScene) {
            finalScene.updateGame(game);
        }
    }
}