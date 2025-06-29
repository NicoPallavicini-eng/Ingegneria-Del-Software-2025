package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Cards;

import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.TravellingSceneDefault;
import it.polimi.ingsw.galaxytrucker.View.IllegalGUIEventException;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import java.util.List;
import java.util.Optional;
/**
 * The PlanetsGUI class represents the graphical user interface for handling planet-related events in the game.
 * It provides buttons and dialogs for user interactions to manage actions during planet events.
 */
public class PlanetsGUI {
    private final TravellingSceneDefault travellingScene;
    
    private Button addCargoButton;
    private Button choosePlanetButton;
    private String nickname;

    /**
     * Constructs a PlanetsGUI instance with the specified travelling scene and player nickname.
     *
     * @param travellingScene The travelling scene associated with this PlanetsGUI.
     * @param nickname        The nickname of the player.
     */
    PlanetsGUI(TravellingSceneDefault travellingScene, String nickname) {
        this.travellingScene = travellingScene;
        this.nickname = nickname;
    }
    /**
     * Initializes the buttons for planet-related actions and their event handlers.
     *
     * @param box The HBox container to which the buttons will be added.
     */
    public void doButtons(HBox box) {
        addCargoButton = new Button("Add Cargo");
        choosePlanetButton = new Button("Choose Planet");
        
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
        choosePlanetButton.setOnAction(e -> {
            Dialog<List<String>> dialog = new Dialog<>();
            dialog.setTitle("Enter Planet Number");
            dialog.setHeaderText("Fill all fields below:");

            ButtonType confirmButton = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(confirmButton, ButtonType.CANCEL);

            TextField num = new TextField();

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);

            grid.add(new Label("Cargo Row:"), 0, 0);
            grid.add(num, 1, 0);

            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == confirmButton) {
                    return List.of(
                            num.getText()
                    );
                }
                return null;
            });

            Optional<List<String>> result = dialog.showAndWait();

            result.ifPresent(inputs -> {
                String row = inputs.getFirst();

                String msg = "/chooseplanet " + row;
                try {
                    travellingScene.sendMessageToServer(msg, this.nickname);
                } catch(IllegalGUIEventException g){
                    System.out.println(g.getMessage());
                }
            });
        });

        addCargoButton.getStyleClass().add("bottom-button");
        choosePlanetButton.getStyleClass().add("bottom-button");

        addCargoButton.setVisible(true);
        choosePlanetButton.setVisible(true);

        box.getChildren().addAll(addCargoButton, choosePlanetButton);
        box.setAlignment(Pos.CENTER);
    }
}
