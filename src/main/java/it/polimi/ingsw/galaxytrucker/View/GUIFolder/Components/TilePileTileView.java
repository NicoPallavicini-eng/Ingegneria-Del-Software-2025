package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components;

import it.polimi.ingsw.galaxytrucker.Model.Tiles.Side;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import java.io.FileInputStream;

import java.util.Objects;
import java.util.Optional;

/**
 * GUI component representing a single tile in the tile pile grid.
 * Displays the tile image, background, back image (if face down), and a transparent button for interaction.
 */
public class TilePileTileView extends StackPane {
    private static final int TILE_SIZE = 54;
    private final ImageView backgroundImage = new ImageView();
    private final ImageView tileImage;
    private final ImageView back;
    private final Button overlayButton = new Button();
    private boolean isClickable = true;
    private boolean isFull = true;
    private int rotation = 0; // 0, 90, 180, 270 only
    private TileImage tileImageEnum;
    private Tile logicTile;

    /**
     * Constructs a new tile pile tile view for the given logical tile.
     * Initializes the background, tile image, back image, and overlay button.
     *
     * @param logicTile the logical tile to associate, or null for an empty slot
     */
    public TilePileTileView(Tile logicTile) {
        this.logicTile = logicTile;
        if (logicTile != null) {
            this.tileImageEnum = TileImage.valueOf(logicTile.getName());
            tileImage = new ImageView(tileImageEnum.getImage());
            tileImage.setFitWidth(TILE_SIZE);
            tileImage.setFitHeight(TILE_SIZE);
            tileImage.setMouseTransparent(true);
        } else {
            tileImage = null;
        }

        backgroundImage.setFitWidth(TILE_SIZE);
        backgroundImage.setFitHeight(TILE_SIZE);
        backgroundImage.setMouseTransparent(true);

        // Transparent button to detect clicks
        overlayButton.setPrefSize(TILE_SIZE, TILE_SIZE);
        overlayButton.setOpacity(0); // invisible but active
        overlayButton.getStyleClass().add("tile-button");

        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/tiles/GT-new_tiles_16_for web157.jpg")));
        back = new ImageView(image);
        back.setFitWidth(TILE_SIZE);
        back.setFitHeight(TILE_SIZE);
        back.setMouseTransparent(true);

        if (logicTile != null && !logicTile.getFacingUp()) {
            if (tileImage != null) {
                getChildren().addAll(backgroundImage, tileImage, back, overlayButton);
            } else {
                getChildren().addAll(backgroundImage, back, overlayButton);
            }
        } else {
            if (tileImage != null) {
                getChildren().addAll(backgroundImage, tileImage, overlayButton);
            } else {
                getChildren().addAll(backgroundImage, overlayButton);
            }
        }
    }
    /**
     * Returns the image view for the back of the tile.
     *
     * @return the back image view
     */
    public ImageView getBack() {
        return back;
    }
    /**
     * Returns the logical tile associated with this view.
     *
     * @return the logical tile, or null if none
     */
    public Tile getLogicTile() {
        return logicTile;
    }
    /**
     * Sets the logical tile for this view and updates the displayed image.
     *
     * @param logicTile the logical tile to associate
     */
    public void setLogicTile(Tile logicTile) {
        this.logicTile = logicTile;
        this.tileImageEnum = TileImage.valueOf(logicTile.getName());
        tileImage.setImage(tileImageEnum.getImage());
    }
    /**
     * Returns the image view displaying the tile image.
     *
     * @return the tile image view
     */
    public ImageView getTileImage() {
        return this.tileImage;
    }
    /**
     * Clears the tile image from this slot.
     */
    public void clearTileImage() {
        tileImage.setImage(null);
    }
    /**
     * Returns the transparent overlay button for this tile view.
     * Can be used to add event handlers for user interaction.
     *
     * @return the overlay button
     */
    public Button getOverlayButton() {
        return overlayButton;
    }
    /**
     * Returns whether this tile view is currently clickable.
     *
     * @return true if clickable, false otherwise
     */
    public boolean isClickable() {
        return isClickable;
    }
    /**
     * Sets whether this tile view is clickable.
     *
     * @param clickable true to make clickable, false otherwise
     */
    public void setClickable(boolean clickable) {
        isClickable = clickable;
    }
    /**
     * Returns whether this tile view currently contains a tile.
     *
     * @return true if full, false otherwise
     */
    public boolean isFull() {
        return isFull;
    }
    /**
     * Sets whether this tile view contains a tile.
     *
     * @param full true if full, false otherwise
     */
    public void setFull(boolean full) {
        isFull = full;
    }
    /**
     * Rotates the tile image in the specified direction.
     *
     * @param direction the direction to rotate (LEFT or RIGHT)
     */
    public void rotate(Side direction) {
        if (direction == Side.LEFT) {
            rotation = (rotation + 270) % 360;
        } else if (direction == Side.RIGHT) {
            rotation = (rotation + 90) % 360;
        }

        tileImage.setRotate(rotation);
    }
    /**
     * Returns the current rotation of the tile image.
     *
     * @return the rotation in degrees (0, 90, 180, 270)
     */
    public int getRotation() {
        return rotation;
    }
    /**
     * Sets the rotation of the tile image.
     *
     * @param rotation the rotation in degrees (0, 90, 180, 270)
     */
    public void setRotation(int rotation) {
        this.rotation = rotation;
        tileImage.setRotate(rotation);
    }
}