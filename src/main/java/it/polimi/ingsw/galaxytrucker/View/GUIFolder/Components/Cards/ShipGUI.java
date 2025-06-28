package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.ShipCard;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Card;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.TravellingSceneDefault;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ShipGUI {
    private final TravellingSceneDefault travellingScene;

    private Button ejectPeopleButton;
    private String nickname;

    ShipGUI(TravellingSceneDefault travellingScene, String nickname) {
        this.travellingScene = travellingScene;
        this.nickname = nickname;
    }

    public void doButtons(HBox box) {
        ejectPeopleButton = new Button("Eject People");

        ejectPeopleButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/ejectpeople", this.nickname);
        });

        ejectPeopleButton.getStyleClass().add("bottom-button");

        ejectPeopleButton.setVisible(true);

        box.getChildren().add(ejectPeopleButton);
        box.setAlignment(Pos.CENTER);
    }
}
