package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.OpenSpaceCard;
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

public class OpenSpaceGUI {
    private final TravellingSceneDefault travellingScene;

    private Button activateEnginesButton;
    private String nickname;

    OpenSpaceGUI(TravellingSceneDefault travellingScene, String nickname) {
        this.travellingScene = travellingScene;
        this.nickname = nickname;
    }

    public void doButtons(HBox box) {
        activateEnginesButton = new Button("Activate Engines");

        activateEnginesButton.setOnAction(e -> {
            Dialog<List<String>> dialog = new Dialog<>();
            dialog.setTitle("Enter Engine and Battery Info");
            dialog.setHeaderText("Fill all fields below:");

            ButtonType confirmButton = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(confirmButton, ButtonType.CANCEL);

            TextField engineRow = new TextField();
            TextField engineCol = new TextField();
            TextField batteryRow = new TextField();
            TextField batteryCol = new TextField();
            TextField batteryNum = new TextField();

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);

            grid.add(new Label("Engine Row:"), 0, 0);
            grid.add(engineRow, 1, 0);
            grid.add(new Label("Engine Column:"), 0, 1);
            grid.add(engineCol, 1, 1);
            grid.add(new Label("Battery Row:"), 0, 2);
            grid.add(batteryRow, 1, 2);
            grid.add(new Label("Battery Column:"), 0, 3);
            grid.add(batteryCol, 1, 3);
            grid.add(new Label("Battery Number:"), 0, 4);
            grid.add(batteryNum, 1, 4);

            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == confirmButton) {
                    return List.of(
                            engineRow.getText(),
                            engineCol.getText(),
                            batteryRow.getText(),
                            batteryCol.getText(),
                            batteryNum.getText()
                    );
                }
                return null;
            });

            Optional<List<String>> result = dialog.showAndWait();

            result.ifPresent(inputs -> {
                String row = inputs.get(0);
                String col = inputs.get(1);
                String row2 = inputs.get(2);
                String col2 = inputs.get(3);
                String bat = inputs.get(4);

                String msg = "/activateengines " + row + "," + col + ";" + row2 + "," + col2 + "," + bat;
                try {
                    travellingScene.sendMessageToServer(msg, this.nickname);
                } catch(IllegalGUIEventException g){
                    System.out.println(g.getMessage());
                }
            });
        });

        activateEnginesButton.getStyleClass().add("bottom-button");

        activateEnginesButton.setVisible(true);

        box.getChildren().add(activateEnginesButton);
        box.setAlignment(Pos.CENTER);
    }
}
