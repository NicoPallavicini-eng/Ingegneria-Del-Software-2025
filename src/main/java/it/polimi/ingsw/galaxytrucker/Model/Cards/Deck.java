package it.polimi.ingsw.galaxytrucker.Model.Cards;

import java.util.Collections;
import java.util.List;

public class Deck {
    private final int cardsNumberLev1 = 4; // TODO implement CardNumber logic for also tut
    private final int cardsNumberLev2 = 8;
    private final List <Card> allCardsLev1;
    private final List <Card> allCardsLev2;
    private List <Card> gameDeck;

    public Deck(List <Card> allCardsLev1, List <Card> allCardsLev2) {
        this.allCardsLev1 = allCardsLev1;
        this.allCardsLev2 = allCardsLev2;
        this.assembleGameDeck();
    }

    public List <Card> getGameDeck() {
        return gameDeck;
    }

    public Card drawCard() {
        return gameDeck.getFirst();
    }

    public void shuffle(List <Card> cards) {
        // Shuffle the deck using Collections.shuffle() with a randomizer
        Collections.shuffle(cards);
    }

    public void assembleGameDeck() {
        gameDeck = allCardsLev1.subList(0, cardsNumberLev1);

        List <Card> secondPart = allCardsLev2.subList(0, cardsNumberLev2);

        // Concatenate the two parts
        gameDeck.addAll(secondPart);

        // Shuffle created gameDeck
        this.shuffle(gameDeck);

        for (Card card : gameDeck) {
            card.updateUsed(true);
        }
    }
}
