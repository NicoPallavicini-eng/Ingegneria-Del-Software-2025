package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * A Pane that displays a background image stretched to fill its area.
 * Used as a reusable background component in the GUI.
 */
public class Background extends Pane {
    /**
     * Constructs a Background pane with a stretched background image.
     * The image is loaded from the resources at /Images/misc/background.png.
     */
    public Background() {
        Image bgImage = new Image(getClass().getResource("/Images/misc/background.png").toExternalForm());
        ImageView bgView = new ImageView(bgImage);

        // Stretch to fill the entire pane
        bgView.fitWidthProperty().bind(widthProperty());
        bgView.fitHeightProperty().bind(heightProperty());
        bgView.setPreserveRatio(false);

        // Add to the Pane
        getChildren().add(bgView);
    }
}
