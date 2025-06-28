package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.*;
import it.polimi.ingsw.galaxytrucker.Model.Cards.CombatZoneCard;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Card;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.TravellingSceneDefault;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CombatZoneGUI {
    private final TravellingSceneDefault travellingScene;
    
    private Button activateCannonsButton;
    private Button activateShieldsButton;
    private Button chooseSubshipButton;
    private Button ejectPeopleButton;
    private Button activateEnginesButton;
    private Button removeCargoButton;
    private String nickname;

    public CombatZoneGUI(TravellingSceneDefault travellingScene, String nickname) {
        this.travellingScene = travellingScene;
        this.nickname = nickname;
    }

    public void doButtons(HBox box) {
        activateCannonsButton = new Button("Activate Cannons");
        activateShieldsButton = new Button("Activate Shields");
        chooseSubshipButton = new Button("Choose Subship");
        ejectPeopleButton = new Button("Eject People");
        activateEnginesButton = new Button("Activate Engines");
        removeCargoButton = new Button("Remove Cargo");

        activateCannonsButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/activatecannons", this.nickname);
        });
        activateShieldsButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/activateshield", this.nickname);
        });
        chooseSubshipButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/choosesubship", this.nickname);
        });
        ejectPeopleButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/ejectpeople", this.nickname);
        });
        activateEnginesButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/activateengines", this.nickname);
        });
        removeCargoButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/removecargo", this.nickname);
        });

        activateCannonsButton.getStyleClass().add("bottom-button");
        activateShieldsButton.getStyleClass().add("bottom-button");
        chooseSubshipButton.getStyleClass().add("bottom-button");
        ejectPeopleButton.getStyleClass().add("bottom-button");
        activateEnginesButton.getStyleClass().add("bottom-button");
        removeCargoButton.getStyleClass().add("bottom-button");

        activateCannonsButton.setVisible(true);
        activateShieldsButton.setVisible(true);
        chooseSubshipButton.setVisible(true);
        ejectPeopleButton.setVisible(true);
        activateEnginesButton.setVisible(true);
        removeCargoButton.setVisible(true);

        HBox h1 = new HBox(activateCannonsButton, activateShieldsButton, chooseSubshipButton);
        HBox h2 = new HBox(ejectPeopleButton, activateEnginesButton, removeCargoButton);
        h1.setSpacing(10);
        h2.setSpacing(10);
        VBox v = new VBox(h1, h2);
        h1.setAlignment(Pos.CENTER);
        h2.setAlignment(Pos.CENTER);

        box.getChildren().add(v);
    }
}
