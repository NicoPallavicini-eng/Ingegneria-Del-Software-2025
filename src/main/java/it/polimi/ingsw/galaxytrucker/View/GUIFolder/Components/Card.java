package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Card extends StackPane {
    private static final int CARD_HEIGHT = 361;
    private static final int CARD_WIDTH = 235;
    private final ImageView backgroundImage = new ImageView();
    private final ImageView cardImage;
    private CardImage cardImageEnum;
    private final it.polimi.ingsw.galaxytrucker.Model.Cards.Card logicCard;

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

    public it.polimi.ingsw.galaxytrucker.Model.Cards.Card getLogicCard() {
        return logicCard;
    }

    public CardImage getCardImageEnum() {
        return cardImageEnum;
    }

    public void setCardImageEnum(CardImage cardImageEnum) {
        this.cardImageEnum = cardImageEnum;
    }

    public void clearCardImage() {
        cardImage.setImage(null);
    }
}
