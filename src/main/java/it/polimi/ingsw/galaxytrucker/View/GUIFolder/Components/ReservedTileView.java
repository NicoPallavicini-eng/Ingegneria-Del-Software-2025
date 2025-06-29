package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components;

import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Side;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.Optional;
/**
 * GUI component representing a reserved tile or hand slot on the player's ship.
 * Displays a background, an optional tile image, and a transparent button for interaction.
 */
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
    /**
     * Constructs a new reserved tile view for the player's ship.
     * Initializes the background, tile image, and overlay button.
     */
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
     * @param tile the logical tile to associate
     */
    public void setLogicTile(Tile tile) {
        this.logicTile = tile;
        if (logicTile != null) {
            this.tileImageEnum = TileImage.valueOf(logicTile.getName());
            tileImage.setImage(tileImageEnum.getImage());
        } else {
            tileImage.setImage(null);
        }
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
     * Clears the tile image from this reserved slot.
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
     * Resets the tile image rotation to 0 degrees.
     */
    public void resetRotation() {
        rotation = 0;
        tileImage.setRotate(0);
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