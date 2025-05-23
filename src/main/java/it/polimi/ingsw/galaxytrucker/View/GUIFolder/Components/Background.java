package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Background extends Pane {

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
