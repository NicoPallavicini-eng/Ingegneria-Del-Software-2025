package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components;

import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Side;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.Optional;

// used for reserved tiles and hand
public class ReservedTileView extends StackPane {
    private static final int TILE_SIZE = 110;
    private final ImageView backgroundImage = new ImageView();
    private final ImageView tileImage;
    private final Button overlayButton = new Button();
    private boolean isClickable = true;
    private boolean isFull = false;
    private int rotation = 0; // 0, 90, 180, 270 only
    private TileImage tileImageEnum;
    private Tile logicTile;

    public ReservedTileView() {
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

    public Tile getLogicTile() {
        return logicTile;
    }

    public void setLogicTile(Tile tile) {
        this.logicTile = tile;
        this.tileImageEnum = TileImage.valueOf(logicTile.getName());

        tileImage.setImage(tileImageEnum.getImage());
    }

    public ImageView getTileImage() {
        return this.tileImage;
    }

    public void clearTileImage() {
        tileImage.setImage(null);
    }

    public Button getOverlayButton() {
        return overlayButton;
    }

    public boolean isClickable() {
        return isClickable;
    }

    public void setClickable(boolean clickable) {
        isClickable = clickable;
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }

    public void rotate(Side direction) {
        if (direction == Side.LEFT) {
            rotation = (rotation + 270) % 360;
        } else if (direction == Side.RIGHT) {
            rotation = (rotation + 90) % 360;
        }

        tileImage.setRotate(rotation);
    }

    public void resetRotation() {
        rotation = 0;
        tileImage.setRotate(0);
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
        tileImage.setRotate(rotation);
    }
}