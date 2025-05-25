package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

// used for reserved tiles and hand
public class ReservedTileView extends StackPane {
    private static final int TILE_SIZE = 110;
    private ImageView backgroundImage = new ImageView();
    private final ImageView tileImage;

    public ReservedTileView() {
        // Set up the top tile image (initially empty)
        tileImage = new ImageView();
        tileImage.setFitWidth(TILE_SIZE);
        tileImage.setFitHeight(TILE_SIZE);

        // Stack background and tile
        getChildren().addAll(backgroundImage, tileImage);
    }

    public void setTileImage(Image img) {
        tileImage.setImage(img);
    }

    public void clearTileImage() {
        tileImage.setImage(null);
    }
}