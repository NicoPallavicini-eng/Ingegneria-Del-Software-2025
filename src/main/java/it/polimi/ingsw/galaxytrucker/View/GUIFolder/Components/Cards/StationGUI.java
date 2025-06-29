package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.StationCard;
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
            Dialog<List<String>> dialog = new Dialog<>();
            dialog.setTitle("Enter Cargo Info");
            dialog.setHeaderText("Fill all fields below:");

            ButtonType confirmButton = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(confirmButton, ButtonType.CANCEL);

            TextField cargoRow = new TextField();
            TextField cargoCol = new TextField();
            TextField batteryNum = new TextField();

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);

            grid.add(new Label("Cargo Row:"), 0, 0);
            grid.add(cargoRow, 1, 0);
            grid.add(new Label("Cargo Column:"), 0, 1);
            grid.add(cargoCol, 1, 1);
            grid.add(new Label("People Number:"), 0, 2);
            grid.add(batteryNum, 1, 2);

            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == confirmButton) {
                    return List.of(
                            cargoRow.getText(),
                            cargoCol.getText(),
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

                String msg = "/addcargo " + row + "," + col + "," + num;
                try {
                    travellingScene.sendMessageToServer(msg, this.nickname);
                } catch(IllegalGUIEventException g){
                    System.out.println(g.getMessage());
                }
            });
        });
        switchCargoButton.setOnAction(e -> {
            Dialog<List<String>> dialog = new Dialog<>();
            dialog.setTitle("Enter Cargo 1 & 2 Info");
            dialog.setHeaderText("Fill all fields below:");

            ButtonType confirmButton = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(confirmButton, ButtonType.CANCEL);

            TextField cargo1Row = new TextField();
            TextField cargo1Col = new TextField();
            TextField cargo2Row = new TextField();
            TextField cargo2Col = new TextField();
            TextField cargoNum = new TextField();

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);

            grid.add(new Label("Cargo 1 Row:"), 0, 0);
            grid.add(cargo1Row, 1, 0);
            grid.add(new Label("Cargo 1 Column:"), 0, 1);
            grid.add(cargo1Col, 1, 1);
            grid.add(new Label("Cargo 2 Row:"), 0, 2);
            grid.add(cargo2Row, 1, 2);
            grid.add(new Label("Cargo 2 Column:"), 0, 3);
            grid.add(cargo2Col, 1, 3);
            grid.add(new Label("Cargo Number:"), 0, 4);
            grid.add(cargoNum, 1, 4);

            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == confirmButton) {
                    return List.of(
                            cargo1Row.getText(),
                            cargo1Col.getText(),
                            cargo2Row.getText(),
                            cargo2Col.getText(),
                            cargoNum.getText()
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
                String num = inputs.get(4);

                String msg = "/switchcargo " + row + "," + col + "," + num + ";" + row2 + "," + col2;
                try {
                    travellingScene.sendMessageToServer(msg, this.nickname);
                } catch(IllegalGUIEventException g){
                    System.out.println(g.getMessage());
                }
            });
        });

        addCargoButton.getStyleClass().add("bottom-button");
        switchCargoButton.getStyleClass().add("bottom-button");
        
        addCargoButton.setVisible(true);
        switchCargoButton.setVisible(true);

        box.getChildren().addAll(addCargoButton, switchCargoButton);
        box.setAlignment(Pos.CENTER);
    }
}
