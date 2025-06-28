package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.SmugglersCard;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Card;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.TravellingSceneDefault;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class SmugglersGUI {
    private final TravellingSceneDefault travellingScene;
    
    private Button activateCannonsButton;
    private Button addCargoButton;
    private Button removeCargoButton;
    private String nickname;

    SmugglersGUI(TravellingSceneDefault travellingScene, String nickname) {
        this.travellingScene = travellingScene;
        this.nickname = nickname;
    }

    public void doButtons(HBox box) {
        activateCannonsButton = new Button("Activate Cannons");
        addCargoButton = new Button("Add Cargo");
        removeCargoButton = new Button("Remove Cargo");

        activateCannonsButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/activatecannons", this.nickname);
        });
        addCargoButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/addcargo", this.nickname);
        });
        removeCargoButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/removecargo", this.nickname);
        });

        activateCannonsButton.getStyleClass().add("bottom-button");
        addCargoButton.getStyleClass().add("bottom-button");
        removeCargoButton.getStyleClass().add("bottom-button");

        activateCannonsButton.setVisible(true);
        addCargoButton.setVisible(true);
        removeCargoButton.setVisible(true);

        box.getChildren().addAll(activateCannonsButton, addCargoButton, removeCargoButton);
        box.setAlignment(Pos.CENTER);
    }
}
