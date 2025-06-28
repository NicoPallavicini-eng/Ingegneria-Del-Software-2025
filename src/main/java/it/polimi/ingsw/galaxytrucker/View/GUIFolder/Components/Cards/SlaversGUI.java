package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.SlaversCard;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Card;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.TravellingSceneDefault;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.rmi.server.UnicastRemoteObject;

public class SlaversGUI {
    private final TravellingSceneDefault travellingScene;
    
    private Button activateCannonsButton;
    private Button ejectPeopleButton;
    private String nickname;

    SlaversGUI(TravellingSceneDefault travellingScene, String nickname) {
        this.travellingScene = travellingScene;
        this.nickname = nickname;
    }

    public void doButtons(HBox box) {
        activateCannonsButton = new Button("Activate Cannons");
        ejectPeopleButton = new Button("Eject People");

        activateCannonsButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/activatecannons", this.nickname);
        });
        ejectPeopleButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/ejectpeople", this.nickname);
        });

        activateCannonsButton.getStyleClass().add("bottom-button");
        ejectPeopleButton.getStyleClass().add("bottom-button");

        activateCannonsButton.setVisible(true);
        ejectPeopleButton.setVisible(true);

        box.getChildren().addAll(activateCannonsButton, ejectPeopleButton);
        box.setAlignment(Pos.CENTER);
    }
}
