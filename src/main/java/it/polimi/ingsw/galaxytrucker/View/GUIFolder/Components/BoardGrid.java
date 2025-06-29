package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components;

import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.BuildingSceneBoard;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * A StackPane representing the board grid in the GUI.
 * Displays the board image and manages its layout and size.
 */
public class BoardGrid extends StackPane {
    private static final int TOT_WIDTH = 937;
    private static final int TOT_HEIGHT = 679;
    private final BuildingSceneBoard buildingSceneBoard;

    /**
     * Constructs a BoardGrid with the specified color and building scene board.
     * Initializes the board image and sets up the layout.
     *
     * @param color the color associated with the board
     * @param buildingSceneBoard the building scene board controller
     */
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
