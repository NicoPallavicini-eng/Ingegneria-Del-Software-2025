package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.SlaversCard;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Card;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.TravellingSceneDefault;
import it.polimi.ingsw.galaxytrucker.View.IllegalGUIEventException;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Optional;

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
            Dialog<List<String>> dialog = new Dialog<>();
            dialog.setTitle("Enter Cannon and Battery Info");
            dialog.setHeaderText("Fill all fields below:");

            ButtonType confirmButton = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(confirmButton, ButtonType.CANCEL);

            TextField cannonRow = new TextField();
            TextField cannonCol = new TextField();
            TextField batteryRow = new TextField();
            TextField batteryCol = new TextField();
            TextField batteryNum = new TextField();

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);

            grid.add(new Label("Cannon Row:"), 0, 0);
            grid.add(cannonRow, 1, 0);
            grid.add(new Label("Cannon Column:"), 0, 1);
            grid.add(cannonCol, 1, 1);
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
                            cannonRow.getText(),
                            cannonCol.getText(),
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

                String msg = "/activatecannons " + row + "," + col + ";" + row2 + "," + col2 + "," + bat;
                try {
                    travellingScene.sendMessageToServer(msg, this.nickname);
                } catch(IllegalGUIEventException g){
                    System.out.println(g.getMessage());
                }
            });
        });
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

        activateCannonsButton.getStyleClass().add("bottom-button");
        ejectPeopleButton.getStyleClass().add("bottom-button");

        activateCannonsButton.setVisible(true);
        ejectPeopleButton.setVisible(true);

        box.getChildren().addAll(activateCannonsButton, ejectPeopleButton);
        box.setAlignment(Pos.CENTER);
    }
}
