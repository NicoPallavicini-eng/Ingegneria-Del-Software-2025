package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.*;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Card;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.TravellingSceneDefault;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class CardInteractive extends Pane {
    private static final int HEIGHT = 500; // TODO check
    private static final int WIDTH = 350; // TODO check
    private final TravellingSceneDefault travellingScene;
    private final Card card;

    private CombatZoneGUI combatZoneGUI;
    private EpidemicGUI epidemicGUI;
    private MeteorsGUI meteorsGUI;
    private OpenSpaceGUI openSpaceGUI;
    private PiratesGUI piratesGUI;
    private PlanetsGUI planetsGUI;
    private ShipGUI shipGUI;
    private SlaversGUI slaversGUI;
    private SmugglersGUI smugglersGUI;
    private StardustGUI stardustGUI;
    private StationGUI stationGUI;

    private Button doneButton;
    private Button noChoiceButton;
    private Button giveUpButton;
    private Button inventoryButton;
    private HBox lowButtonBox;
    private HBox highButtonBox;
    private String nickname;

    public CardInteractive(Card card, TravellingSceneDefault travellingScene, String nickname) {
        this.card = card;
        this.nickname = nickname;
        this.travellingScene = travellingScene;

        this.setPrefSize(WIDTH, HEIGHT);

        combatZoneGUI = new CombatZoneGUI(travellingScene, nickname);
        epidemicGUI = new EpidemicGUI(travellingScene, nickname);
        meteorsGUI = new MeteorsGUI(travellingScene, nickname);
        openSpaceGUI = new OpenSpaceGUI(travellingScene, nickname);
        piratesGUI = new PiratesGUI(travellingScene, nickname);
        planetsGUI = new PlanetsGUI(travellingScene, nickname);
        shipGUI = new ShipGUI(travellingScene, nickname);
        slaversGUI = new SlaversGUI(travellingScene, nickname);
        smugglersGUI = new SmugglersGUI(travellingScene, nickname);
        stardustGUI = new StardustGUI(travellingScene, nickname);
        stationGUI = new StationGUI(travellingScene, nickname);

        lowButtonBox = new HBox();
        highButtonBox = new HBox();
        doMainButtons();

        switch (card.getLogicCard()) {
            case CombatZoneCard combatZoneCard:
                combatZoneGUI.doButtons(highButtonBox);
                break;
            case EpidemicCard epidemicCard:
                break;
            case MeteorsCard meteorsCard:
                meteorsGUI.doButtons(highButtonBox);
                break;
            case OpenSpaceCard openSpaceCard:
                openSpaceGUI.doButtons(highButtonBox);
                break;
            case PiratesCard piratesCard:
                piratesGUI.doButtons(highButtonBox);
                break;
            case PlanetsCard planetsCard:
                planetsGUI.doButtons(highButtonBox);
                break;
            case ShipCard shipCard:
                shipGUI.doButtons(highButtonBox);
                break;
            case SlaversCard slaversCard:
                slaversGUI.doButtons(highButtonBox);
                break;
            case SmugglersCard smugglersCard:
                smugglersGUI.doButtons(highButtonBox);
                break;
            case StardustCard stardustCard:
                break;
            case StationCard stationCard:
                stationGUI.doButtons(highButtonBox);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + card.getLogicCard());
        }

        lowButtonBox.setSpacing(10);
        highButtonBox.setSpacing(10);

        lowButtonBox.setLayoutY(600);
        lowButtonBox.setAlignment(Pos.CENTER);

        card.setLayoutX(60);
        card.setLayoutY(160);

        getChildren().addAll(lowButtonBox, highButtonBox, card);
    }

    public void doMainButtons() {
        doneButton = new Button("Done");
        noChoiceButton = new Button("No Choice");
        giveUpButton = new Button("Give Up");

        doneButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/done", this.nickname);
        });
        noChoiceButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/nochoice", this.nickname);
        });
        giveUpButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/giveup", this.nickname);
            travellingScene.getSceneManager().next(travellingScene);
        });

        doneButton.getStyleClass().add("bottom-button");
        noChoiceButton.getStyleClass().add("bottom-button");
        giveUpButton.getStyleClass().add("bottom-button");

        doneButton.setVisible(true);
        noChoiceButton.setVisible(true);
        giveUpButton.setVisible(true);

        doneButton.setLayoutX(20);
        doneButton.setLayoutY(400);
        noChoiceButton.setLayoutX(20);
        noChoiceButton.setLayoutY(450);
        giveUpButton.setLayoutX(120);
        giveUpButton.setLayoutY(400);

        lowButtonBox.getChildren().addAll(doneButton, noChoiceButton, giveUpButton);
        lowButtonBox.setAlignment(Pos.CENTER);
    }
}
