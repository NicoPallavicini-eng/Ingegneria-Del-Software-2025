package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.MeteorsCard;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Card;
import it.polimi.ingsw.galaxytrucker.Model.Cards.*;
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
 * The MeteorsGUI class represents the graphical user interface for handling meteor-related events in the game.
 * It provides buttons and dialogs for user interactions to manage actions during meteor events.
 */
public class MeteorsGUI {
    private final TravellingSceneDefault travellingScene;
    
    private Button activateCannonsButton;
    private Button activateShieldsButton;
    private Button chooseSubshipButton;
    private String nickname;
    /**
     * Constructs a MeteorsGUI instance with the specified travelling scene and player nickname.
     *
     * @param travellingScene The travelling scene associated with this MeteorsGUI.
     * @param nickname        The nickname of the player.
     */
    MeteorsGUI(TravellingSceneDefault travellingScene, String nickname) {
        this.travellingScene = travellingScene;
        this.nickname = nickname;
    }
    /**
     * Initializes the buttons for meteor-related actions and their event handlers.
     *
     * @param box The HBox container to which the buttons will be added.
     */
    public void doButtons(HBox box) {
        activateCannonsButton = new Button("Activate Cannons");
        activateShieldsButton = new Button("Activate Shields");
        chooseSubshipButton = new Button("Choose Subship");

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
