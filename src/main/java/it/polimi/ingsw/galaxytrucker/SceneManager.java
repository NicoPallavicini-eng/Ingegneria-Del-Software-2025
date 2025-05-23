package it.polimi.ingsw.galaxytrucker;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.*;

public class SceneManager extends Application {
    private static Stage primaryStage;
    private final Game game;
    private WaitingScene waitingScene;
    private String nickname;

    public SceneManager(Game game, Stage stage, String nickname) {
        this.game = game;
        this.nickname = nickname;
        primaryStage = stage;
        start(primaryStage); // stage comes from instantiation of SceneManager
    }

    @Override
    public void start(Stage stage) {
        waitingScene = new WaitingScene(game, nickname);
        primaryStage.setTitle("Waiting State");
        primaryStage.setScene(waitingScene.getScene());
        primaryStage.show();
    }

    public static void setScene(Scene scene) {
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public void next(WaitingScene waitingScene) {
        BuildingScene buildingScene = new BuildingScene(game, nickname);
        primaryStage.setTitle("Building State");
        primaryStage.setScene(buildingScene.getScene());
        primaryStage.show();
    }

    public void next(BuildingScene buildingScene) {
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
}