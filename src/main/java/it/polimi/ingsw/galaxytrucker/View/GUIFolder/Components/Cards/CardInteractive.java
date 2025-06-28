package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.*;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.TravellingSceneDefault;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class CardInteractive extends StackPane {
    private static final int HEIGHT = 500; // TODO check
    private static final int WIDTH = 350; // TODO check
    private final TravellingSceneDefault travellingScene;
    private final it.polimi.ingsw.galaxytrucker.Model.Cards.Card card;

    private Button doneButton;
    private Button noChoiceButton;
    private Button giveUpButton;
    private Button inventoryButton;
    private String nickname;

    public CardInteractive(Card card, TravellingSceneDefault travellingScene, String nickname) {
        this.card = card;
        this.nickname = nickname;
        this.travellingScene = travellingScene;
        doMainButtons(doneButton, noChoiceButton, giveUpButton, inventoryButton);
/*
        if (card instanceof CombatZoneCard){
            CombatZoneGUI cardGUI = new CombatZoneGUI((CombatZoneCard) card, travellingScene);
        }
        else if (card instanceof EpidemicCard){
            EpidemicGUI cardGUI = new EpidemicGUI((EpidemicCard) card, travellingScene);
        }
        else if (card instanceof MeteorsCard){
            MeteorsGUI cardGUI = new MeteorsGUI((MeteorsCard) card, travellingScene);
        }
        else if (card instanceof OpenSpaceCard){
            OpenSpaceGUI cardGUI = new OpenSpaceGUI((OpenSpaceCard) card, travellingScene);
        }
        else if (card instanceof PiratesCard){
            PiratesGUI cardGUI = new PiratesGUI((PiratesCard) card, travellingScene);
        }
        else if (card instanceof PlanetsCard){
            PlanetsGUI cardGUI = new PlanetsGUI((PlanetsCard) card, travellingScene);
        }
        else if (card instanceof ShipCard){
            ShipGUI cardGUI = new ShipGUI((ShipCard) card, travellingScene);
        }
        else if (card instanceof  SlaversCard){
            SlaversGUI cardGUI = new SlaversGUI((SlaversCard) card, travellingScene);
        }
        else if (card instanceof SmugglersCard){
            SmugglersGUI cardGUI = new SmugglersGUI((SmugglersCard) card, travellingScene);
        }
        else if (card instanceof StardustCard){
            StardustGUI cardGUI = new StardustGUI((StardustCard) card, travellingScene);
        }
        else if (card instanceof StationCard) {
            StationGUI cardGUI = new StationGUI((StationCard) card, travellingScene);
        }
        else {
            throw new IllegalArgumentException("Unknown Card Type");

        }
 */
    }


    public void doMainButtons(Button doneButton, Button noChoiceButton, Button giveUpButton, Button inventoryButton) {
        doneButton = new Button("Done");
        noChoiceButton = new Button("No Choice");
        giveUpButton = new Button("Give Up");
        doneButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/done", this.nickname);
        });
        noChoiceButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/nochoice", this.nickname);
        });
        giveUpButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/giveup", this.nickname);
        });

        doneButton.getStyleClass().add("bottom-button");
        noChoiceButton.getStyleClass().add("bottom-button");
        giveUpButton.getStyleClass().add("bottom-button");
        inventoryButton.getStyleClass().add("bottom-button");

        doneButton.setVisible(true);
        noChoiceButton.setVisible(true);
        giveUpButton.setVisible(true);

        doneButton.setLayoutX(20);
        doneButton.setLayoutY(400);
        noChoiceButton.setLayoutX(20);
        noChoiceButton.setLayoutY(450);
        giveUpButton.setLayoutX(120);
        giveUpButton.setLayoutY(400);

        VBox buttons = new VBox(10, doneButton, noChoiceButton, giveUpButton, inventoryButton);
        buttons.setAlignment(Pos.BOTTOM_CENTER);
        getChildren().add(buttons);
    }
}
