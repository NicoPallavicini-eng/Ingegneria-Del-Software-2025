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

    private Button activateCannonsButton;
    private Button activateShieldsButton;
    private Button addCargoButton;
    private Button chooseSubshipButton;
    private Button ejectPeopleButton;
    private Button activateEnginesButton;
    private Button removeCargoButton;
    private Button switchCargoButton;
    private Button choosePlanetButton;

    private Button doneButton;
    private Button noChoiceButton;
    private Button giveUpButton;
    private Button inventoryButton;

    public CardInteractive(Card card, TravellingSceneDefault travellingScene) {
        this.card = card;
        this.travellingScene = travellingScene;

        doButtons();
    }

    public void doButtons() {
        activateCannonsButton = new Button("Activate Cannons");
        activateShieldsButton = new Button("Activate Shields");
        addCargoButton = new Button("Add Cargo");
        chooseSubshipButton = new Button("Choose Subship");
        ejectPeopleButton = new Button("Eject People");
        activateEnginesButton = new Button("Activate Engines");
        removeCargoButton = new Button("Remove Cargo");
        switchCargoButton = new Button("Switch Cargo");
        choosePlanetButton = new Button("Choose Planet");

        doneButton = new Button("Done");
        noChoiceButton = new Button("No Choice");
        giveUpButton = new Button("Give Up");
        inventoryButton = new Button("Inventory");

        activateCannonsButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/activatecannons");
        });
        activateShieldsButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/activateshield");
        });
        addCargoButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/addcargo");
        });
        chooseSubshipButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/choosesubship");
        });
        ejectPeopleButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/ejectpeople");
        });
        activateEnginesButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/activateengines");
        });
        removeCargoButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/removecargo");
        });
        switchCargoButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/switchcargo");
        });
        choosePlanetButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/chooseplanet");
        });

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

        activateCannonsButton.getStyleClass().add("bottom-button");
        activateShieldsButton.getStyleClass().add("bottom-button");
        addCargoButton.getStyleClass().add("bottom-button");
        chooseSubshipButton.getStyleClass().add("bottom-button");
        ejectPeopleButton.getStyleClass().add("bottom-button");
        activateEnginesButton.getStyleClass().add("bottom-button");
        removeCargoButton.getStyleClass().add("bottom-button");
        switchCargoButton.getStyleClass().add("bottom-button");
        choosePlanetButton.getStyleClass().add("bottom-button");

        doneButton.getStyleClass().add("bottom-button");
        noChoiceButton.getStyleClass().add("bottom-button");
        giveUpButton.getStyleClass().add("bottom-button");
        inventoryButton.getStyleClass().add("bottom-button");

        activateCannonsButton.setVisible(false);
        activateShieldsButton.setVisible(false);
        addCargoButton.setVisible(false);
        chooseSubshipButton.setVisible(false);
        ejectPeopleButton.setVisible(false);
        activateEnginesButton.setVisible(false);
        removeCargoButton.setVisible(false);
        switchCargoButton.setVisible(false);
        choosePlanetButton.setVisible(false);

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
