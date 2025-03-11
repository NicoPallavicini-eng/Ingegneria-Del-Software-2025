package it.polimi.ingsw.galaxytrucker.Model.Cards;

import java.util.List;

public class Deck {
    private final CardNumber cardsNumberLev1;
    private final CardNumber cardsNumberLev2;
    private final List <Card> allCardsLev1;
    private final List <Card> allCardsLev2;
    private List <Card> gameDeck;

    public Deck(CardNumber cardsNumberLev1, CardNumber cardsNumberLev2, List <Card> allCardsLev1, List <Card> allCardsLev2, List <Card> gameDeck) {
        this.cardsNumberLev1 = cardsNumberLev1;
        this.cardsNumberLev2 = cardsNumberLev2;
        this.allCardsLev1 = allCardsLev1;
        this.allCardsLev2 = allCardsLev2;
        this.gameDeck = gameDeck;
    }

    public List <Card> getAllCards() {
        return allCardsLev1; // TODO concatenate allCardsLev2
    }

    public List <Card> getGameDeck() {
        return gameDeck;
    }

    public Card drawCard() {
        return gameDeck.getFirst();
    }

    public void shuffleDeck() {
        // TODO add randomizer
    }

    public void shuffleCardsInUse() {
        // TODO add randomizer
    }
}
