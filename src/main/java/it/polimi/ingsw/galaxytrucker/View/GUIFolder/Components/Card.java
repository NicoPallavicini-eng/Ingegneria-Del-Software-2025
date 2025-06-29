package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * A GUI component representing a card in the game.
 * Displays the card image and manages its visual state.
 */
public class Card extends StackPane {
    private static final int CARD_HEIGHT = 361;
    private static final int CARD_WIDTH = 235;
    private final ImageView backgroundImage = new ImageView();
    private final ImageView cardImage;
    private CardImage cardImageEnum;
    private final it.polimi.ingsw.galaxytrucker.Model.Cards.Card logicCard;
    /**
     * Constructs a Card component for the specified logic card.
     * Initializes the card image and background.
     *
     * @param logicCard the logic card model associated with this GUI card
     */
    public Card(it.polimi.ingsw.galaxytrucker.Model.Cards.Card logicCard) {
        this.logicCard = logicCard;
        this.cardImageEnum = CardImage.valueOf(logicCard.getName());

        cardImage = new ImageView(cardImageEnum.getImage());
        cardImage.setFitHeight(CARD_HEIGHT);
        cardImage.setFitWidth(CARD_WIDTH);
        cardImage.setMouseTransparent(true);

        backgroundImage.setFitHeight(CARD_HEIGHT);
        backgroundImage.setFitWidth(CARD_WIDTH);
        backgroundImage.setMouseTransparent(true);

        getChildren().addAll(backgroundImage, cardImage);
    }
    /**
     * Returns the logic card model associated with this GUI card.
     *
     * @return the logic card
     */
    public it.polimi.ingsw.galaxytrucker.Model.Cards.Card getLogicCard() {
        return logicCard;
    }
    /**
     * Returns the card image enum representing the card's image.
     *
     * @return the card image enum
     */
    public CardImage getCardImageEnum() {
        return cardImageEnum;
    }
    /**
     * Sets the card image enum and updates the card's image.
     *
     * @param cardImageEnum the new card image enum
     */
    public void setCardImageEnum(CardImage cardImageEnum) {
        this.cardImageEnum = cardImageEnum;
    }
    /**
     * Clears the card image, making it invisible.
     */
    public void clearCardImage() {
        cardImage.setImage(null);
    }
}
