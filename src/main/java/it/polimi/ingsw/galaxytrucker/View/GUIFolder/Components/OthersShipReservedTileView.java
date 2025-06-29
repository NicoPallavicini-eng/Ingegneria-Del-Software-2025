package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components;

import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.Optional;
/**
 * GUI component representing a reserved tile or hand slot on another player's ship.
 * Displays a background, an optional tile image, and a transparent button for interaction.
 */
public class OthersShipReservedTileView extends StackPane {
    private static final int TILE_SIZE = 55; // 110/2
    private final ImageView backgroundImage = new ImageView();
    private final ImageView tileImage;
    private final Button overlayButton = new Button();
    private Optional<Tile> logicTile;
    /**
     * Constructs a new reserved tile view for another player's ship.
     * Initializes the background, tile image, and overlay button.
     */
    public OthersShipReservedTileView() {
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
     * Sets the image to display for the tile in this reserved slot.
     *
     * @param img the image to display
     */
    public void setTileImage(Image img) {
        tileImage.setImage(img);
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
}
