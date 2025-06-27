package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Card;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.TravellingSceneDefault;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class CardInteractive extends StackPane {
    private static final int HEIGHT = 500; // TODO check
    private static final int WIDTH = 350; // TODO check
    private final TravellingSceneDefault travellingScene;
    private final it.polimi.ingsw.galaxytrucker.Model.Cards.Card card;

    private Button doneButton;
    private Button noChoiceButton;
    private Button giveUpButton;
    private Button inventoryButton;

    public CardInteractive(Card card, TravellingSceneDefault travellingScene) {
        this.card = card;
        this.travellingScene = travellingScene;

        doMainButtons(doneButton, noChoiceButton, giveUpButton, inventoryButton);
    }

    public void doMainButtons(Button doneButton, Button noChoiceButton, Button giveUpButton, Button inventoryButton) {
        doneButton = new Button("Done");
        noChoiceButton = new Button("No Choice");
        giveUpButton = new Button("Give Up");
        inventoryButton = new Button("Inventory");

        doneButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/done");
        });
        noChoiceButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/nochoice");
        });
        giveUpButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/giveup");
        });
        inventoryButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/viewinvetory");
        });

        doneButton.getStyleClass().add("bottom-button");
        noChoiceButton.getStyleClass().add("bottom-button");
        giveUpButton.getStyleClass().add("bottom-button");
        inventoryButton.getStyleClass().add("bottom-button");

        doneButton.setVisible(true);
        noChoiceButton.setVisible(true);
        giveUpButton.setVisible(true);
        inventoryButton.setVisible(true);

        doneButton.setLayoutX(20);
        doneButton.setLayoutY(400);
        noChoiceButton.setLayoutX(20);
        noChoiceButton.setLayoutY(450);
        giveUpButton.setLayoutX(120);
        giveUpButton.setLayoutY(400);
        inventoryButton.setLayoutX(120);
        inventoryButton.setLayoutY(450);
    }
}
