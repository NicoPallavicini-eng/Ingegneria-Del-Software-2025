package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components;

import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the GUI component for a deck of cards in the game.
 * Manages the visual representation and grouping of cards for different subdecks.
 */
public class Deck extends StackPane {
    private static final int CARD_HEIGHT = 361;
    private static final int CARD_WIDTH = 235;
    private static final List<Card> deck1 = new ArrayList<>();
    private static final List<Card> deck3 = new ArrayList<>();
    private static final List<Card> deck2 = new ArrayList<>();
    private static final List<Card> deck4 = new ArrayList<>();
    private static final List<Card> gameDeck = new ArrayList<>();

    /**
     * Constructs a new Deck GUI component based on the provided logical deck.
     *
     * @param deckCards the logical deck from the model containing all cards and subdecks
     */
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
    /**
     * Returns the list of all cards in the game deck.
     *
     * @return the list of cards in the game deck
     */
    public List<Card> getGameDeck() {
        return gameDeck;
    }
    /**
     * Returns the list of cards in subdeck 1.
     *
     * @return the list of cards in subdeck 1
     */
    public List<Card> getDeck1() {
        return deck1;
    }
    /**
     * Returns the list of cards in subdeck 2.
     *
     * @return the list of cards in subdeck 2
     */
    public List<Card> getDeck2() {
        return deck2;
    }

    /**
     * Returns the list of cards in subdeck 3.
     *
     * @return the list of cards in subdeck 3
     */
    public List<Card> getDeck3() {
        return deck3;
    }
    /**
     * Returns the list of cards in subdeck 4.
     *
     * @return the list of cards in subdeck 4
     */
    public List<Card> getDeck4() {
        return deck4;
    }
}
