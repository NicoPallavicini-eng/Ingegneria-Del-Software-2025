package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components;

import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.Optional;

// used for others' ship tiles
public class OthersShipTileView extends StackPane {
    private static final int TILE_SIZE = 62; // 124/2
    private final ImageView backgroundImage = new ImageView();
    private final ImageView tileImage;
    private final Button overlayButton = new Button();
    private Optional<Tile> logicTile;

    public OthersShipTileView() {
        // Set up the top tile image (initially empty)
        tileImage = new ImageView();
        tileImage.setFitWidth(TILE_SIZE);
        tileImage.setFitHeight(TILE_SIZE);
        tileImage.setMouseTransparent(true);

        backgroundImage.setFitWidth(TILE_SIZE);
        backgroundImage.setFitHeight(TILE_SIZE);
        backgroundImage.setMouseTransparent(true);

        // Transparent button to detect clicks
        overlayButton.setPrefSize(TILE_SIZE, TILE_SIZE);
        overlayButton.setOpacity(0); // invisible but active
        overlayButton.getStyleClass().add("tile-button");

        // Stack background and tile
        getChildren().addAll(backgroundImage, tileImage, overlayButton);
    }

    public void setTileImage(Image img) {
        tileImage.setImage(img);
    }

    public void clearTileImage() {
        tileImage.setImage(null);
    }

    public Button getOverlayButton() {
        return overlayButton;
    }
}