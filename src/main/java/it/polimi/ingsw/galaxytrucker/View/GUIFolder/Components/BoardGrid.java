package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components;

import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.BuildingSceneBoard;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class BoardGrid extends StackPane {
    private static final int TOT_WIDTH = 937;
    private static final int TOT_HEIGHT = 679;
    private final BuildingSceneBoard buildingSceneBoard;

    public BoardGrid(Color color, BuildingSceneBoard buildingSceneBoard) {
        this.buildingSceneBoard = buildingSceneBoard;
        ImageView boardImage = new ImageView(new Image(getClass().getResource("/Images/cardboard/cardboard-5.png").toExternalForm()));
        boardImage.setFitWidth(750);
        boardImage.setPreserveRatio(true);

        getChildren().add(boardImage);
        StackPane.setAlignment(boardImage, Pos.TOP_CENTER);
        StackPane.setMargin(boardImage, new Insets(10, 0, 0, 0));

        this.setPrefSize(TOT_WIDTH, TOT_HEIGHT);
        this.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
    }
}
