package it.polimi.ingsw.galaxytrucker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.*;

public class MainApp extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        MainMenuScene menu = new MainMenuScene();
        stage.setTitle("Galaxy Trucker");
        stage.setScene(menu.getScene());
        stage.show();
    }

    public static void setScene(Scene scene) {
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}