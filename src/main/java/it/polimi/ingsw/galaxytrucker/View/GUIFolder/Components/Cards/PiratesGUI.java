package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.PiratesCard;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Card;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.TravellingSceneDefault;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class PiratesGUI {
    private final TravellingSceneDefault travellingScene;
    
    private Button activateCannonsButton;
    private Button activateShieldsButton;
    private Button chooseSubshipButton;
    private String nickname;

    PiratesGUI(TravellingSceneDefault travellingScene, String nickname) {
        this.travellingScene = travellingScene;
        this.nickname = nickname;
    }

    public void doButtons(HBox box) {
        activateCannonsButton = new Button("Activate Cannons");
        activateShieldsButton = new Button("Activate Shields");
        chooseSubshipButton = new Button("Choose Subship");

        activateCannonsButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/activatecannons", this.nickname);
        });
        activateShieldsButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/activateshield", this.nickname);
        });
        chooseSubshipButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/choosesubship", this.nickname);
        });

        activateCannonsButton.getStyleClass().add("bottom-button");
        activateShieldsButton.getStyleClass().add("bottom-button");
        chooseSubshipButton.getStyleClass().add("bottom-button");

        activateCannonsButton.setVisible(true);
        activateShieldsButton.setVisible(true);
        chooseSubshipButton.setVisible(true);

        box.getChildren().addAll(activateCannonsButton, activateShieldsButton, chooseSubshipButton);
        box.setAlignment(Pos.CENTER);
    }
}
