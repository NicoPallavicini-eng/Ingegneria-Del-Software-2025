package it.polimi.ingsw.galaxytrucker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.*;

public class SceneManager extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        WaitingScene waitingScene = new WaitingScene();
        stage.setTitle("Waiting State");
        stage.setScene(waitingScene.getScene());
        stage.show();
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
        BuildingScene buildingScene = new BuildingScene();
        primaryStage.setTitle("Building State");
        primaryStage.setScene(buildingScene.getScene());
        primaryStage.show();
    }

    public void next(BuildingScene buildingScene) {
        TravellingScene travellingScene = new TravellingScene();
        primaryStage.setTitle("Travelling State");
        primaryStage.setScene(travellingScene.getScene());
        primaryStage.show();
    }

    public void next(TravellingScene travellingScene) {
        FinalScene finalScene = new FinalScene();
        primaryStage.setTitle("Final State");
        primaryStage.setScene(finalScene.getScene());
        primaryStage.show();
    }
}