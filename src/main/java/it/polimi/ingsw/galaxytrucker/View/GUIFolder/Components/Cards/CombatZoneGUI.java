package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Cards;

import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.TravellingSceneDefault;
import it.polimi.ingsw.galaxytrucker.View.IllegalGUIEventException;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

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
        activateShieldsButton.setOnAction(e -> {
            Dialog<List<String>> dialog = new Dialog<>();
            dialog.setTitle("Enter Shield and Battery Info");
            dialog.setHeaderText("Fill all fields below:");

            ButtonType confirmButton = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(confirmButton, ButtonType.CANCEL);

            TextField shieldRow = new TextField();
            TextField shieldCol = new TextField();
            TextField batteryRow = new TextField();
            TextField batteryCol = new TextField();

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);

            grid.add(new Label("Engine Row:"), 0, 0);
            grid.add(shieldRow, 1, 0);
            grid.add(new Label("Engine Column:"), 0, 1);
            grid.add(shieldCol, 1, 1);
            grid.add(new Label("Battery Row:"), 0, 2);
            grid.add(batteryRow, 1, 2);
            grid.add(new Label("Battery Column:"), 0, 3);
            grid.add(batteryCol, 1, 3);

            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == confirmButton) {
                    return List.of(
                            shieldRow.getText(),
                            shieldCol.getText(),
                            batteryRow.getText(),
                            batteryCol.getText()
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

                String msg = "/activateshield " + row + "," + col + "," + row2 + "," + col2;
                try {
                    travellingScene.sendMessageToServer(msg, this.nickname);
                } catch(IllegalGUIEventException g){
                    System.out.println(g.getMessage());
                }
            });
        });
        chooseSubshipButton.setOnAction(e -> {
            Dialog<List<String>> dialog = new Dialog<>();
            dialog.setTitle("Enter Chosen SubShip Info");
            dialog.setHeaderText("Fill all fields below:");

            ButtonType confirmButton = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(confirmButton, ButtonType.CANCEL);

            TextField subRow = new TextField();
            TextField subCol = new TextField();

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);

            grid.add(new Label("Engine Row:"), 0, 0);
            grid.add(subRow, 1, 0);
            grid.add(new Label("Engine Column:"), 0, 1);
            grid.add(subCol, 1, 1);

            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == confirmButton) {
                    return List.of(
                            subRow.getText(),
                            subCol.getText()
                    );
                }
                return null;
            });

            Optional<List<String>> result = dialog.showAndWait();

            result.ifPresent(inputs -> {
                String row = inputs.get(0);
                String col = inputs.get(1);

                String msg = "/choosesubship " + row + "," + col;
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
        removeCargoButton.setOnAction(e -> {
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

                String msg = "/removecargo " + row + "," + col + "," + num;
                try {
                    travellingScene.sendMessageToServer(msg, this.nickname);
                } catch(IllegalGUIEventException g){
                    System.out.println(g.getMessage());
                }
            });
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
        v.setSpacing(10);
        h1.setAlignment(Pos.CENTER);
        h2.setAlignment(Pos.CENTER);

        box.getChildren().add(v);
    }
}
