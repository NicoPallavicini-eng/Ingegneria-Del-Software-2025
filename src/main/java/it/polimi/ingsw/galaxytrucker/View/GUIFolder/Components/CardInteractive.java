package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components;

import javafx.scene.layout.StackPane;

public class CardInteractive extends StackPane {
    private static final int HEIGHT = 500; // TODO check
    private static final int WIDTH = 350; // TODO check
    private final Card card;

    public CardInteractive(Card card) {
        this.card = card;
    }
}
