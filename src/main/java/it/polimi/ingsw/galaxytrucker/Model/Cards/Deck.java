package it.polimi.ingsw.galaxytrucker.Model.Cards;

import java.util.List;

import java.util.Collections;

public class Deck {
    private final int cardsNumberLev1 = 4;
    private final int cardsNumberLev2 = 8;
    private final List <Card> allCardsLev1;
    private final List <Card> allCardsLev2;
    private List <Card> gameDeck;

    public Deck(List <Card> allCardsLev1, List <Card> allCardsLev2, List <Card> gameDeck) {
        this.allCardsLev1 = allCardsLev1;
        this.allCardsLev2 = allCardsLev2;
        this.gameDeck = gameDeck;
    }

    public List <Card> getAllCardsLev1() {
        return allCardsLev1;
    }

    public List <Card> getAllCardsLev2() {
        return allCardsLev2;
    }

    public List <Card> getGameDeck() {
        return gameDeck;
    }

    public Card drawCard() {
        return gameDeck.getFirst();
    }

    public void shuffleAllCards() {
        // Shuffle the deck using Collections.shuffle() with a randomizer
        Collections.shuffle(allCardsLev1);
        Collections.shuffle(allCardsLev2);
    }

    public void shuffleCardsInUse() {
        // Shuffle the deck using Collections.shuffle() with a randomizer
        Collections.shuffle(gameDeck);
    }

    public List <Card> assembleGameDeck() {
        this.shuffleAllCards();

        gameDeck = allCardsLev1.subList(1, cardsNumberLev1);

        List <Card> secondPart = allCardsLev2.subList(0, cardsNumberLev2);

        // Concatenate the two parts
        gameDeck.addAll(secondPart);

        return gameDeck;
    }
}
