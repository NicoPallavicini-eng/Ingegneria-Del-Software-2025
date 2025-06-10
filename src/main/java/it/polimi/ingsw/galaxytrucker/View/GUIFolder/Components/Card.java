package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Card extends StackPane {
    private static final int CARD_HEIGHT = 361;
    private static final int CARD_WIDTH = 235;
    private final ImageView backgroundImage = new ImageView();
    private final ImageView cardImage;
    private final Button overlayButton = new Button();
    private int rotation = 0; // deg, needed for Building orientation of sub-decks

    public Card() {
        cardImage = new ImageView();
        cardImage.setFitHeight(CARD_HEIGHT);
        cardImage.setFitWidth(CARD_WIDTH);
        cardImage.setMouseTransparent(true);

        backgroundImage.setFitHeight(CARD_HEIGHT);
        backgroundImage.setFitWidth(CARD_WIDTH);
        backgroundImage.setMouseTransparent(true);

        overlayButton.setPrefHeight(CARD_HEIGHT);
        overlayButton.setPrefWidth(CARD_WIDTH);
        overlayButton.setOpacity(0); // invisible but active
        overlayButton.getStyleClass().add("card-button");

        getChildren().addAll(backgroundImage, cardImage, overlayButton);
    }

    public void setCardImage(Image image) {
        cardImage.setImage(image);
    }

    public ImageView getCardImage() {
        return cardImage;
    }

    public void clearCardImage() {
        cardImage.setImage(null);
    }

    public Button getOverlayButton() {
        return overlayButton;
    }

    public void resetRotation() {
        rotation = 0;
        cardImage.setRotate(0);
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
        cardImage.setRotate(rotation);
    }
}
