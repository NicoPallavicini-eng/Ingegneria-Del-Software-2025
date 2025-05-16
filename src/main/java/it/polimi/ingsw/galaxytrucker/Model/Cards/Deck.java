package it.polimi.ingsw.galaxytrucker.Model.Cards;

import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck implements Serializable {
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
        Card firstCard = gameDeck.getFirst();
        gameDeck.removeFirst();
        return firstCard;
    }

    public void shuffle(List <Card> cards) {
        // Shuffle the deck using Collections.shuffle() with a randomizer
        Collections.shuffle(cards);
    }

    public void assembleGameDeck() {
        // TODO implement CardNumber logic for also tut
        int cardsNumberLev1 = 4;
        gameDeck = new ArrayList<>(allCardsLev1.subList(0, cardsNumberLev1));

        int cardsNumberLev2 = 8;
        List<Card> secondPart = new ArrayList<>(allCardsLev2.subList(0, cardsNumberLev2));

        // Concatenate the two parts
        gameDeck.addAll(secondPart);

        // Shuffle created gameDeck
        this.shuffle(gameDeck);

        for (Card card : gameDeck) {
            card.updateUsed(true);
        }
    }
}
