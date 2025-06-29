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
    private GridPane resGrid;
    private GridPane handGrid;
    private GridPane grid;

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

        grid = new GridPane();
        grid.setHgap(0);
        grid.setVgap(0);
        grid.setLayoutX(LEFT_BORDER);
        grid.setLayoutY(TOP_BORDER);

        resGrid = new GridPane();
        resGrid.setHgap(13);
        resGrid.setVgap(0);
        resGrid.setLayoutX(RESERVED_LEFT_BORDER);
        resGrid.setLayoutY(RESERVED_TOP_BORDER);

        handGrid = new GridPane();
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
                                buildingSceneUserShip.sendMessageToServer("/placetile " + finalRow + "," + finalCol, this.user.getNickname());
                            } catch (IllegalGUIEventException ex) {
                                System.out.println(ex.getMessage());
                            }
                        } else if (tile.isClickable()) {
                            try {
                                    buildingSceneUserShip.sendMessageToServer("/pickupfromship", this.user.getNickname());
                                    update(user.getShip(), (user.getShip().getTileInHand()) != null ? user.getShip().getTileInHand().getRotation() : 0);

                            } catch (IllegalGUIEventException ex) {
                                System.out.println(ex.getMessage());
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
                    try {
                        buildingSceneUserShip.sendMessageToServer("/reservetile", this.user.getNickname());
                        update(user.getShip(), (user.getShip().getTileInHand()) != null ? user.getShip().getTileInHand().getRotation() : 0);
                    } catch (IllegalGUIEventException ex) {
                        System.out.println(ex.getMessage());
                    }
                } else if (tile.isClickable()) {
                    try {
                        buildingSceneUserShip.sendMessageToServer("/pickupreservedtile " + (finalSlot + 1), this.user.getNickname());
                        update(user.getShip(), (user.getShip().getTileInHand()) != null ? user.getShip().getTileInHand().getRotation() : 0);
                    } catch (IllegalGUIEventException ex) {;
                        System.out.println(ex.getMessage());
                    }
                }
            });
            resCells[slot] = tile;
            resGrid.add(tile, slot, 0);
        }

        ReservedTileView tile = new ReservedTileView();
        tile.setPrefSize(RESERVED_TILE_SIZE, RESERVED_TILE_SIZE);
        tile.getOverlayButton().setOnAction(e -> {
            if (tile.isClickable() && tile.isFull()) {
                buildingSceneUserShip.getBuildingSceneTilePile().putDownTile(tile);
            }
        });
        handCell[0] = tile;
        handGrid.add(tile, 0, 0);

        // --- MAIN CABIN ---
        Optional<Tile> mainCabin = user.getShip().getTileOnFloorPlan(2, 3);

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
            try {
                buildingSceneUserShip.sendMessageToServer("/rotate left", this.user.getNickname());
            } catch (IllegalGUIEventException ex) {
                System.out.println(ex.getMessage());
            }
        });
        rotateRight.setOnAction(e -> {
            try {
                buildingSceneUserShip.sendMessageToServer("/rotate right", this.user.getNickname());
            } catch (IllegalGUIEventException ex) {
                System.out.println(ex.getMessage());
            }
        });

        // Absolute positioning
        getChildren().addAll(bgView, grid, resGrid, handGrid, rotateLeft, rotateRight);

        this.setPrefSize(TOT_WIDTH, TOT_HEIGHT);
        this.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
        update(user.getShip(), (user.getShip().getTileInHand()) != null ? user.getShip().getTileInHand().getRotation() : 0);
    }

    public List<Button> getRotates() {
        List<Button> rotates = new ArrayList<>();
        rotates.add(rotateLeft);
        rotates.add(rotateRight);
        return rotates;
    }

    public List<GridPane> getResNHand() {
        List<GridPane> panes = new ArrayList<>();
        panes.add(handGrid);
        panes.add(resGrid);
        return panes;
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
                    cells[row][col].setRotation(tile.getRotation());
                    //cells[row][col].setClickable(false);
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
