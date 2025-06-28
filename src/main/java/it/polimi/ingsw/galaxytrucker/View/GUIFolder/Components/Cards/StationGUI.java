package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.StationCard;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Card;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.TravellingSceneDefault;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class StationGUI {
    private final TravellingSceneDefault travellingScene;
    
    private Button addCargoButton;
    private Button switchCargoButton;
    private String nickname;

    StationGUI(TravellingSceneDefault travellingScene, String nickname) {
        this.travellingScene = travellingScene;
        this.nickname = nickname;
    }

    public void doButtons(HBox box) {
        addCargoButton = new Button("Add Cargo");
        switchCargoButton = new Button("Switch Cargo");
        
        addCargoButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/addcargo", this.nickname);
        });
        switchCargoButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/switchcargo", this.nickname);
        });

        addCargoButton.getStyleClass().add("bottom-button");
        switchCargoButton.getStyleClass().add("bottom-button");
        
        addCargoButton.setVisible(true);
        switchCargoButton.setVisible(true);

        box.getChildren().addAll(addCargoButton, switchCargoButton);
        box.setAlignment(Pos.CENTER);
    }
}
