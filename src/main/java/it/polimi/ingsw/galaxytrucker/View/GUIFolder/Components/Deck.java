package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Deck {
    private static final int CARD_HEIGHT = 361;
    private static final int CARD_WIDTH = 235;

    public Deck() {
        List<CardImage> shuffledCards = Arrays.stream(CardImage.values())
                .collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(shuffledCards);

        int imageIndex = 0;

        for (CardImage cardImage : shuffledCards) {
            Card card = new Card();
            card.setPrefHeight(CARD_HEIGHT);
            card.setPrefWidth(CARD_WIDTH);

            try {
                CardImage cardImage1 = shuffledCards.get(imageIndex);
                card.setCardImage(cardImage1.getImage());
            } catch (Exception e) {
                System.err.println("Failed to load image at: " + (imageIndex));
                e.printStackTrace();
            }

            // TODO set buttons

            imageIndex++;
        }
    }
}
