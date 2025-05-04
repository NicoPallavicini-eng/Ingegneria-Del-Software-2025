package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualCards;

import java.util.List;

public class VirtualDeck {
    private final List <VirtualCard> allCardsLev1;
    private final List <VirtualCard> allCardsLev2;
    private List <VirtualCard> gameDeck;

    public VirtualDeck(List <VirtualCard> allCardsLev1, List <VirtualCard> allCardsLev2) {
        this.allCardsLev1 = allCardsLev1;
        this.allCardsLev2 = allCardsLev2;
    }

    public List <VirtualCard> getGameDeck() {
        return gameDeck;
    }

    public List<VirtualCard> getAllCardsLev1() {
        return allCardsLev1;
    }

    public List<VirtualCard> getAllCardsLev2() {
        return allCardsLev2;
    }
}
