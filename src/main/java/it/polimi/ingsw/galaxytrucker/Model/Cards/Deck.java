package it.polimi.ingsw.galaxytrucker.Model.Cards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck implements Serializable {
    private final List <Card> allCardsLev1;
    private final List <Card> allCardsLev2;
    private final List <Card> subDeck1 = new ArrayList <>();
    private final List <Card> subDeck2 = new ArrayList <>();
    private final List <Card> subDeck3 = new ArrayList <>();
    private final List <Card> subDeck4 = new ArrayList <>();
    private List <Card> gameDeck = new ArrayList <>();

    public Deck(List <Card> allCardsLev1, List <Card> allCardsLev2) {
        this.allCardsLev1 = allCardsLev1;
        this.allCardsLev2 = allCardsLev2;
        this.assembleGameDeck();
    }

    public List <Card> getGameDeck() {
        return gameDeck;
    }

    public Card drawCard() {
        Card firstCard;
        try{
            firstCard = gameDeck.getFirst();
            gameDeck.removeFirst();
        }catch (Exception e){
            firstCard = null;
        }
        return firstCard;
    }

    public void shuffle(List <Card> cards) {
        // Shuffle the deck using Collections.shuffle() with a randomizer
        Collections.shuffle(cards);
    }

    public void assembleGameDeck() { // TODO implement CardNumber logic for also tut
        Collections.shuffle(allCardsLev1);
        Collections.shuffle(allCardsLev2);

        subDeck1.add(allCardsLev1.get(0));
        subDeck1.add(allCardsLev2.get(0));
        subDeck1.add(allCardsLev2.get(1));

        subDeck2.add(allCardsLev1.get(1));
        subDeck2.add(allCardsLev2.get(2));
        subDeck2.add(allCardsLev2.get(3));

        subDeck3.add(allCardsLev1.get(2));
        subDeck3.add(allCardsLev2.get(4));
        subDeck3.add(allCardsLev2.get(5));

        subDeck4.add(allCardsLev1.get(3));
        subDeck4.add(allCardsLev2.get(6));
        subDeck4.add(allCardsLev2.get(7));

        // Concatenate the two parts
        gameDeck.addAll(subDeck1);
        gameDeck.addAll(subDeck2);
        gameDeck.addAll(subDeck3);
        gameDeck.addAll(subDeck4);

        // Shuffle created gameDeck
        shuffle(gameDeck);

        for (Card card : gameDeck) {
            card.updateUsed(true);
        }
    }

    public List<Card> getSubDeck1() {
        return subDeck1;
    }

    public List<Card> getSubDeck2() {
        return subDeck2;
    }

    public List<Card> getSubDeck3() {
        return subDeck3;
    }

    public List<Card> getSubDeck4() {
        return subDeck4;
    }
}
