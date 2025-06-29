package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.ShipCard;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Card;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.TravellingSceneDefault;
import it.polimi.ingsw.galaxytrucker.View.IllegalGUIEventException;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;
/**
 * The ShipGUI class represents the graphical user interface for handling ship-related events in the game.
 * It provides buttons and dialogs for user interactions to manage actions during ship events.
 */
public class ShipGUI {
    private final TravellingSceneDefault travellingScene;

    private Button ejectPeopleButton;
    private String nickname;
    /**
     * Constructs a ShipGUI instance with the specified travelling scene and player nickname.
     *
     * @param travellingScene The travelling scene associated with this ShipGUI.
     * @param nickname        The nickname of the player.
     */
    ShipGUI(TravellingSceneDefault travellingScene, String nickname) {
        this.travellingScene = travellingScene;
        this.nickname = nickname;
    }
    /**
     * Initializes the buttons for ship-related actions and their event handlers.
     *
     * @param box The HBox container to which the buttons will be added.
     */
    public void doButtons(HBox box) {
        ejectPeopleButton = new Button("Eject People");

        ejectPeopleButton.setOnAction(e -> {
            Dialog<List<String>> dialog = new Dialog<>();
            dialog.setTitle("Enter Cabin Info");
            dialog.setHeaderText("Fill all fields below:");

            ButtonType confirmButton = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(confirmButton, ButtonType.CANCEL);

            TextField cabinRow = new TextField();
            TextField cabinCol = new TextField();
            TextField batteryNum = new TextField();

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);

            grid.add(new Label("Cabin Row:"), 0, 0);
            grid.add(cabinRow, 1, 0);
            grid.add(new Label("Cabin Column:"), 0, 1);
            grid.add(cabinCol, 1, 1);
            grid.add(new Label("People Number:"), 0, 2);
            grid.add(batteryNum, 1, 2);

            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == confirmButton) {
                    return List.of(
                            cabinRow.getText(),
                            cabinCol.getText(),
                            batteryNum.getText()
                    );
                }
                return null;
            });

            Optional<List<String>> result = dialog.showAndWait();

            result.ifPresent(inputs -> {
                String row = inputs.get(0);
                String col = inputs.get(1);
                String num = inputs.get(2);

                String msg = "/ejectpeople " + row + "," + col + "," + num;
                try {
                    travellingScene.sendMessageToServer(msg, this.nickname);
                } catch(IllegalGUIEventException g){
                    System.out.println(g.getMessage());
                }
            });
        });

        ejectPeopleButton.getStyleClass().add("bottom-button");

        ejectPeopleButton.setVisible(true);

        box.getChildren().add(ejectPeopleButton);
        box.setAlignment(Pos.CENTER);
    }
}
