package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components;

import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Deck extends StackPane {
    private static final int CARD_HEIGHT = 361;
    private static final int CARD_WIDTH = 235;
    private static final List<Card> deck1 = new ArrayList<>();
    private static final List<Card> deck3 = new ArrayList<>();
    private static final List<Card> deck2 = new ArrayList<>();
    private static final List<Card> deck4 = new ArrayList<>();
    private static final List<Card> gameDeck = new ArrayList<>();

    public Deck(it.polimi.ingsw.galaxytrucker.Model.Cards.Deck deckCards) {
        List<it.polimi.ingsw.galaxytrucker.Model.Cards.Card> deck = deckCards.getGameDeck();
        List<it.polimi.ingsw.galaxytrucker.Model.Cards.Card> subDeck1 = deckCards.getSubDeck1();
        List<it.polimi.ingsw.galaxytrucker.Model.Cards.Card> subDeck2 = deckCards.getSubDeck2();
        List<it.polimi.ingsw.galaxytrucker.Model.Cards.Card> subDeck3 = deckCards.getSubDeck3();
        List<it.polimi.ingsw.galaxytrucker.Model.Cards.Card> subDeck4 = deckCards.getSubDeck4();

        // pairing of gameDeck's cards with images of gui cards, instantiating only the used ones
        for (it.polimi.ingsw.galaxytrucker.Model.Cards.Card card : deck) {
            Card guiCard = new Card(card);
            guiCard.setPrefHeight(CARD_HEIGHT);
            guiCard.setPrefWidth(CARD_WIDTH);

            gameDeck.add(guiCard);

            // TODO set buttons
        }

        // set subDecks for Building stage Board
        for (Card card : gameDeck) {
            for (it.polimi.ingsw.galaxytrucker.Model.Cards.Card logicCard : subDeck1) {
                if (card.getLogicCard().equals(logicCard)) {
                    deck1.add(card);
                }
            }
            for (it.polimi.ingsw.galaxytrucker.Model.Cards.Card logicCard : subDeck2) {
                if (card.getLogicCard().equals(logicCard)) {
                    deck2.add(card);
                }
            }
            for (it.polimi.ingsw.galaxytrucker.Model.Cards.Card logicCard : subDeck3) {
                if (card.getLogicCard().equals(logicCard)) {
                    deck3.add(card);
                }
            }
            for (it.polimi.ingsw.galaxytrucker.Model.Cards.Card logicCard : subDeck4) {
                if (card.getLogicCard().equals(logicCard)) {
                    deck4.add(card);
                }
            }
        }
    }

    public List<Card> getGameDeck() {
        return gameDeck;
    }

    public List<Card> getDeck1() {
        return deck1;
    }

    public List<Card> getDeck2() {
        return deck2;
    }

    public List<Card> getDeck3() {
        return deck3;
    }

    public List<Card> getDeck4() {
        return deck4;
    }
}
