package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components;

import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Side;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.BuildingSceneUserShip;
import it.polimi.ingsw.galaxytrucker.View.IllegalGUIEventException;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserShipGrid extends Pane {
    private static final int ROWS = 5;
    private static final int COLS = 7;
    private static final int RES_SLOTS = 2;
    private static final int TILE_SIZE = 124;
    private static final int RESERVED_TILE_SIZE = 110;
    private static final int TOP_BORDER = 29;
    private static final int LEFT_BORDER = 35;
    private static final int TOT_WIDTH = 937;
    private static final int TOT_HEIGHT = 679;
    private static final int RESERVED_TOP_BORDER = 28;
    private static final int RESERVED_LEFT_BORDER = 677;
    private static final int HAND_LEFT_BORDER = 28;
    private final BuildingSceneUserShip buildingSceneUserShip;
    private Game game;
    private Player user;

    private final TileView[][] cells = new TileView[ROWS][COLS];
    private final ReservedTileView[] resCells = new ReservedTileView[RES_SLOTS];
    private final ReservedTileView[] handCell = new ReservedTileView[1];
    private Button rotateLeft;
    private Button rotateRight;

    public UserShipGrid(Color color, BuildingSceneUserShip buildingSceneUserShip) {
        this.buildingSceneUserShip = buildingSceneUserShip;
        this.game = buildingSceneUserShip.getGame();
        this.user = buildingSceneUserShip.getUser();
        // --- BACKGROUND ---
        Image bgImage = new Image(getClass().getResource("/Images/cardboard/cardboard-1c.png").toExternalForm());
        ImageView bgView = new ImageView(bgImage);
        bgView.setFitWidth(TOT_WIDTH);
        bgView.setFitHeight(TOT_HEIGHT);
        bgView.setPreserveRatio(true);
        bgView.setX(0);
        bgView.setY(0);

        GridPane grid = new GridPane();
        grid.setHgap(0);
        grid.setVgap(0);
        grid.setLayoutX(LEFT_BORDER);
        grid.setLayoutY(TOP_BORDER);

        GridPane resGrid = new GridPane();
        resGrid.setHgap(13);
        resGrid.setVgap(0);
        resGrid.setLayoutX(RESERVED_LEFT_BORDER);
        resGrid.setLayoutY(RESERVED_TOP_BORDER);

        GridPane handGrid = new GridPane();
        handGrid.setLayoutX(HAND_LEFT_BORDER);
        handGrid.setLayoutY(RESERVED_TOP_BORDER);

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (!((row == 0 && (col == 0 || col == 1 || col == 3 || col == 5 || col == 6)) ||
                        (row == 1 && (col == 0 || col == 6)) ||
                        (row == 4 && col == 3))) {
                    TileView tile = new TileView();
                    tile.setPrefSize(TILE_SIZE, TILE_SIZE);
                    int finalRow = row+5;
                    int finalCol = col+4;
                    tile.getOverlayButton().setOnAction(e -> {
                        if (tile.isClickable() && !tile.isFull()) {
                            try {
                                buildingSceneUserShip.sendMessageToServer("/placetile " + finalRow + "," + finalCol);
                            } catch (IllegalGUIEventException ex) {
                                errorPopUp(ex);
                            }
                        } else if (tile.isClickable() && tile.isFull()) {
                            try {
                                Tile realTile = tile.getLogicTile();
                                if (realTile == user.getShip().getLastPlacedTile()) {
                                    buildingSceneUserShip.sendMessageToServer("/pickupfromship"); //TODO non capisco neanche qui... Devo premere sul tile in hand o sulla tile della ship?
                                    update(user.getShip(), 0);
                                }
                            } catch (IllegalGUIEventException ex) {
                                errorPopUp(ex);
                            }
                        }
                    });
                    cells[row][col] = tile;
                    grid.add(tile, col, row);
                }
            }
        }

        for (int slot = 0; slot < RES_SLOTS; slot++) {
            ReservedTileView tile = new ReservedTileView();
            tile.setPrefSize(RESERVED_TILE_SIZE, RESERVED_TILE_SIZE);
            int finalSlot = slot;
            tile.getOverlayButton().setOnAction(e -> {
                if (tile.isClickable() && !tile.isFull()) {
                    buildingSceneUserShip.sendMessageToServer("/reservetile");
                    update(user.getShip(), 0);
//                    setResTile(finalSlot, handCell[0].getLogicTile(), handCell[0].getRotation());
//                    handCell[0].clearTileImage();
//                    tile.setFull(true);
                } else if (tile.isClickable() && tile.isFull()) {
                    buildingSceneUserShip.sendMessageToServer("/pickupreservedtile " + (finalSlot+1));
                    update(user.getShip(), 0);
                }
            });
            resCells[slot] = tile;
            resGrid.add(tile, slot, 0);
        }

        ReservedTileView tile = new ReservedTileView();
        tile.setPrefSize(RESERVED_TILE_SIZE, RESERVED_TILE_SIZE);
        tile.getOverlayButton().setOnAction(e -> {
            if (tile.isClickable() && tile.isFull()) {
                buildingSceneUserShip.getBuildingSceneTilePile().putDownTile(tile); //TODO: NON HO CAPITO :) COSA FA?
            }
        });
        handCell[0] = tile;
        handGrid.add(tile, 0, 0);

        // --- MAIN CABIN ---
        Optional<Tile> mainCabin = user.getShip().getTileOnFloorPlan(2, 3); // TODO check correctness

        cells[2][3].setLogicTile(mainCabin.orElse(null));
        cells[2][3].setFull(true);
        cells[2][3].setClickable(false);

        rotateLeft = new Button("↺");
        rotateRight = new Button("↻");
        rotateLeft.getStyleClass().add("rotate-button");
        rotateRight.getStyleClass().add("rotate-button");

        rotateLeft.setLayoutX(HAND_LEFT_BORDER + TILE_SIZE + 10);
        rotateLeft.setLayoutY(RESERVED_TOP_BORDER + 10);
        rotateRight.setLayoutX(HAND_LEFT_BORDER + TILE_SIZE + 10);
        rotateRight.setLayoutY(RESERVED_TOP_BORDER + 70);
        rotateLeft.setVisible(false);
        rotateRight.setVisible(false);

        rotateLeft.setOnAction(e -> {
            buildingSceneUserShip.sendMessageToServer("/rotate left");
        });
        rotateRight.setOnAction(e -> {
            buildingSceneUserShip.sendMessageToServer("/rotate right");
        });

        // Absolute positioning
        getChildren().addAll(bgView, grid, resGrid, handGrid, rotateLeft, rotateRight);

        this.setPrefSize(TOT_WIDTH, TOT_HEIGHT);
        this.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
        update(user.getShip(), 0);
    }

    private void update(Ship ship, int rotation){
        buildGridFromShip(ship);
        buildReservedTilesFromShip(ship);
        setHandTile(ship.getTileInHand(), rotation);
    }

    public void buildGridFromShip(Ship ship) {
        ArrayList<ArrayList<Tile>> floorplan = ship.getFloorplanArrayList();
        for (int row = 0; row < floorplan.size(); row++) {
            for (int col = 0; col < floorplan.get(row).size(); col++) {
                Tile tile = floorplan.get(row).get(col);
                if (tile != null) {
                    cells[row][col].setLogicTile(tile);
                    cells[row][col].setFull(true);
                    cells[row][col].setClickable(false);
                }
            }
        }
    }

    public void buildReservedTilesFromShip(Ship ship) {
        List<Tile> reservedTiles = ship.getReservedTiles();
        for (int i = 0; i < reservedTiles.size(); i++) {
            resCells[i].setLogicTile(reservedTiles.get(i));
            resCells[i].setFull(true);
            resCells[i].setClickable(true);
        }
    }
    /**
     * Sets a tile image at the specified logical row and column.
     */
    public void setTile (int row, int col, Tile logicTile, int rotation) {
        if (row >= 0 && row < ROWS && col >= 0 && col < COLS &&
            !((row == 0 && (col == 0 || col == 1 || col == 3 || col == 5 || col == 6)) ||
            (row == 1 && (col == 0 || col == 6)) ||
            (row == 4 && col == 3) || (row == 2 && col == 3)) &&
            cells[row][col].getLogicTile() == null) {
            cells[row][col].setLogicTile(logicTile);
            cells[row][col].setFull(true);
            cells[row][col].setRotation(rotation);
            updateRotateVisible(false);
            cells[row][col].setClickable(true); // TODO setup logic of last picked up cell
        } else {
            // TODO print error: "hand already filled" or other errors
        }
        // TODO add conformity checks
    }

    public void setResTile (int slot, Tile tile, int rotation) {
        if (slot >= 0 && slot < RES_SLOTS && resCells[slot].getLogicTile() == null) {
            resCells[slot].setLogicTile(tile);
            resCells[slot].setFull(true);
            resCells[slot].setRotation(rotation);
            updateRotateVisible(false);
            resCells[slot].setClickable(true); // TODO setup the putdown n all
        } else {
            // TODO print error: "slot already filled" or other errors
        }
        // TODO add conformity checks
    }

    public void setHandTile (Tile tile, int rotation) {
        handCell[0].setLogicTile(tile);
        handCell[0].setClickable(true);
        handCell[0].setRotation(rotation);
        handCell[0].setFull(true);
        updateRotateVisible(true);
    }

    public void updateRotateVisible(boolean rotateVisible) {
        if (rotateVisible) {
            rotateLeft.setVisible(true);
            rotateRight.setVisible(true);
        } else {
            rotateLeft.setVisible(false);
            rotateRight.setVisible(false);
        }
    }

    public ReservedTileView getHandTile () {
        return handCell[0];
    }

//    public Tile pickUpFromShip(TileView tile) { TODO understand if necessary (prob no)
//        Tile ret;
//        if (tile.getLogicTile() != null) {
//            ret = tile.getLogicTile();
//            tile.setLogicTile(null);
//        } else {
//            ret = null;
//            // TODO print error: "no tile present"
//        }
//        return ret;
//        // TODO add conformity checks
//    }

    public Tile pickUpReserved(ReservedTileView tile) {
        Tile ret;
        if (tile.getLogicTile() != null) {
            ret = tile.getLogicTile();
            tile.setLogicTile(null);
        } else {
            ret = null;
            // TODO print error: "no tile present"
        }
        return ret;
        // TODO add conformity checks
    }

    public void reserveTile(int slot, ReservedTileView tile) {
        resCells[slot] = tile;
        handCell[0] = null;
        // TODO add conformity checks
    }

    public void placeTileOnShip(int row, int column, TileView tile) {
        cells[row][column] = tile;
        handCell[0] = null;
        // TODO add conformity checks
    }

    private void errorPopUp(IllegalGUIEventException e){
        javafx.application.Platform.runLater(() -> {
            javafx.scene.control.Alert errorAlert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setContentText(e.getMessage());

            Stage errorStage = (Stage) errorAlert.getDialogPane().getScene().getWindow();
            errorStage.getIcons().clear();
            errorStage.getIcons().add(new Image(getClass().getResourceAsStream("/Images/misc/window_simple_icon.png")));
            errorAlert.showAndWait();
        });
    }

    public List<TileView> getTiles() {
        List<TileView> tiles = new ArrayList<>();
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                if (cells[row][col] != null) {
                    tiles.add(cells[row][col]);
                }
            }
        }
        return tiles;
    }


}
