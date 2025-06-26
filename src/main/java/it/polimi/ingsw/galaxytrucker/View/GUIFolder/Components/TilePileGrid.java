package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components;

import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.BuildingSceneTilePile;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.TilePile;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TilePileGrid extends Pane {
    private static final int ROWS = 10;
    private static final int COLS = 16;
    private static final int TILE_SIZE = 54;
    private static final int TOP_BORDER = 45;
    private static final int LEFT_BORDER = 0;
    private static final int TOT_WIDTH = 937;
    private static final int TOT_HEIGHT = 679;
    private final BuildingSceneTilePile buildingSceneTilePile;

    private final TilePileTileView[][] cells = new TilePileTileView[ROWS][COLS];

    public TilePileGrid(BuildingSceneTilePile buildingSceneTilePile, List<Tile> tilePile) {
        this.buildingSceneTilePile = buildingSceneTilePile;
        GridPane pile = new GridPane();
        pile.setHgap(5);
        pile.setVgap(5);
        pile.setLayoutX(LEFT_BORDER);
        pile.setLayoutY(TOP_BORDER);

        List <TilePileTileView> tiles = new ArrayList<>();
        for (Tile tile : tilePile) {
            if (tile == null) {
                TilePileTileView guiTile = new TilePileTileView(null);
                guiTile.setPrefSize(TILE_SIZE, TILE_SIZE);
                tiles.add(guiTile);
            } else {
                TilePileTileView guiTile = new TilePileTileView(tile);
                guiTile.setPrefSize(TILE_SIZE, TILE_SIZE);
                tiles.add(guiTile);
            }
        }

        int i = 0;

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                TilePileTileView tile = tiles.get(i);

                tile.getOverlayButton().setOnAction(e -> {
                    if (tile.getLogicTile() != null && !tile.getLogicTile().getFacingUp()) {
                        tile.getChildren().remove(tile.getBack());
                    }
                    if (tile.isClickable()) {
                        buildingSceneTilePile.pickUpTile(tile);
                    } else {
                        // TODO print error: not clickable
                    }
                });

                cells[row][col] = tile;
                pile.add(tile, col, row);
                i++;

                if (row == ROWS - 1 && col == 7 || i == tiles.size()) {
                    break;
                }
            }
        }

        getChildren().add(pile);
        this.setPrefSize(TOT_WIDTH, TOT_HEIGHT);
        this.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
    }

    public ImageView getTileImageView (TilePileTileView tile) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (cells[i][j].getLogicTile().equals(tile.getLogicTile())) {                    return cells[i][j].getTileImage();
                }
                if (i == ROWS - 1 && j == 1) {
                    break;
                }
            }
        }
        return null;
    }

    public int getFirstEmpty() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (cells[i][j] == null) {
                    return i;
                }
            }
        }
        return -1;
    }

    public ImageView getTileImageView (ReservedTileView tile) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (cells[i][j].getTileImage().getImage() == tile.getTileImage().getImage()) {
                    return cells[i][j].getTileImage();
                }
                if (i == ROWS - 1 && j == 1) {
                    break;
                }
            }
        }
        return null;
    }

    public ImageView getTileImageView (TileView tile) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (cells[i][j].getLogicTile().equals(tile.getLogicTile())) {
                    return cells[i][j].getTileImage();
                }
                if (i == ROWS - 1 && j == 1) {
                    break;
                }
            }
        }
        return null;
    }

    public void setDefault(int index, Tile t, int rotation) {
        int row = index / COLS;
        int col = index % COLS;
        cells[row][col].setLogicTile(t);
        cells[row][col].setOpacity(1);
        cells[row][col].setClickable(true);
        cells[row][col].setRotation(rotation);
    }

//    public void setDefault(ImageView img, int rotation) {
//        for (int i = 0; i < ROWS; i++) {
//            for (int j = 0; j < COLS; j++) {
//                if (img != null) {
//                    if (cells[i][j].getTileImage().getImage() == img.getImage()) {
//                        cells[i][j].setOpacity(1);
//                        cells[i][j].setClickable(true);
//                        cells[i][j].setRotation(rotation);
//                    }
//                    if (i == ROWS - 1 && j == 1) {
//                        break;
//                    }
//                } else {
//                    // TODO proper exception
//                }
//            }
//        }
//    }
}
