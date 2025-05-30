package it.polimi.ingsw.galaxytrucker;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager extends Application {
    private static Stage primaryStage;
    private final Game game;
    private WaitingScene waitingScene;
    private String nickname;
    private MyScene currentScene;
    private boolean isFirtsPlayer;

    public SceneManager(Game game, Stage stage, String nickname, boolean isFirstPlayer) {
        this.game = game;
        this.nickname = nickname;
        this.isFirtsPlayer = isFirstPlayer;
        primaryStage = stage;
        start(primaryStage); // stage comes from instantiation of SceneManager
    }

    @Override
    public void start(Stage stage) {
        waitingScene = new WaitingScene(game, nickname, isFirtsPlayer);
        primaryStage = stage;
        primaryStage.setScene(waitingScene.getScene());
        primaryStage.setResizable(false);
        primaryStage.setTitle("Waiting State");
        primaryStage.getIcons().add(new javafx.scene.image.Image(getClass().getResource("/Images/misc/window_simple_icon.png").toExternalForm()));
        primaryStage.show();
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

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public void next(WaitingScene waitingScene) {
        BuildingSceneUserShip buildingSceneUserShip = new BuildingSceneUserShip(game, nickname);
        primaryStage.setTitle("Building State - User Ship");
        primaryStage.setScene(buildingSceneUserShip.getScene());
        primaryStage.show();
    }

    public void next(BuildingSceneUserShip buildingSceneUserShip) {
        TravellingScene travellingScene = new TravellingScene(game, nickname);
        primaryStage.setTitle("Travelling State");
        primaryStage.setScene(travellingScene.getScene());
        primaryStage.show();
    }

    public void next(BuildingSceneTilePile buildingSceneTilePile) {
        TravellingScene travellingScene = new TravellingScene(game, nickname);
        primaryStage.setTitle("Travelling State");
        primaryStage.setScene(travellingScene.getScene());
        primaryStage.show();
    }

    public void next(BuildingSceneOthersShip buildingSceneOthersShip) {
        TravellingScene travellingScene = new TravellingScene(game, nickname);
        primaryStage.setTitle("Travelling State");
        primaryStage.setScene(travellingScene.getScene());
        primaryStage.show();
    }

    public void next(TravellingScene travellingScene) {
        FinalScene finalScene = new FinalScene(game, nickname);
        primaryStage.setTitle("Final State");
        primaryStage.setScene(finalScene.getScene());
        primaryStage.show();
    }

    public void switchBuilding(BuildingSceneUserShip buildingSceneUserShip) {
        BuildingSceneTilePile buildingSceneTilePile = new BuildingSceneTilePile(game, nickname);
        primaryStage.setTitle("Building State - Tile Pile");
        primaryStage.setScene(buildingSceneTilePile.getScene());
        primaryStage.show();
    }

    public void switchBuilding(BuildingSceneTilePile buildingSceneTilePile) {
        BuildingSceneOthersShip buildingSceneOthersShip = new BuildingSceneOthersShip(game, nickname);
        primaryStage.setTitle("Building State - Others' Ship");
        primaryStage.setScene(buildingSceneOthersShip.getScene());
        primaryStage.show();
    }

    public void switchBuilding(BuildingSceneOthersShip buildingSceneOthersShip) {
        BuildingSceneUserShip buildingSceneUserShip = new BuildingSceneUserShip(game, nickname);
        primaryStage.setTitle("Building State - User Ship");
        primaryStage.setScene(buildingSceneUserShip.getScene());
        primaryStage.show();
    }
}