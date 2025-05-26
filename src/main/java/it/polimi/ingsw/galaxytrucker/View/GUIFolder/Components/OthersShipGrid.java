package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components;

import it.polimi.ingsw.galaxytrucker.Model.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import static it.polimi.ingsw.galaxytrucker.Model.Color.BLUE;

public class OthersShipGrid extends Pane {
    private static final int ROWS = 5;
    private static final int COLS = 7;
    private static final int RES_SLOTS = 2;
    private static final int TILE_SIZE = 124; // TODO
    private static final int RESERVED_TILE_SIZE = 110; // TODO
    private static final int TOP_BORDER = 29; // TODO
    private static final int LEFT_BORDER = 35; // TODO
    private static final int TOT_WIDTH = 937; // TODO
    private static final int TOT_HEIGHT = 679; // TODO
    private static final int RESERVED_TOP_BORDER = 28; // TODO
    private static final int RESERVED_LEFT_BORDER = 677; // TODO
    private static final int HAND_LEFT_BORDER = 28; // TODO

    private final TileView[][] cells1 = new TileView[ROWS][COLS];
    private final TileView[][] cells2 = new TileView[ROWS][COLS];
    private final TileView[][] cells3 = new TileView[ROWS][COLS];

    private final ReservedTileView[] resCells1 = new ReservedTileView[RES_SLOTS];
    private final ReservedTileView[] resCells2 = new ReservedTileView[RES_SLOTS];
    private final ReservedTileView[] resCells3 = new ReservedTileView[RES_SLOTS];

    private final ReservedTileView[] handCell1 = new ReservedTileView[1];
    private final ReservedTileView[] handCell2 = new ReservedTileView[1];
    private final ReservedTileView[] handCell3 = new ReservedTileView[1];

    public OthersShipGrid(Color color) {
        // --- BACKGROUND ---
        Image bgImage = new Image(getClass().getResource("/Images/cardboard/cardboard-1c.png").toExternalForm());
        ImageView bgView = new ImageView(bgImage);
        bgView.setFitWidth(TOT_WIDTH);
        bgView.setFitHeight(TOT_HEIGHT);
        bgView.setPreserveRatio(true);
        bgView.setX(0);
        bgView.setY(0);

        GridPane grid1 = new GridPane();
        grid1.setHgap(0);
        grid1.setVgap(0);
        grid1.setLayoutX(LEFT_BORDER);
        grid1.setLayoutY(TOP_BORDER);
        GridPane grid2 = new GridPane();
        grid2.setHgap(0);
        grid2.setVgap(0);
        grid2.setLayoutX(LEFT_BORDER);
        grid2.setLayoutY(TOP_BORDER);
        GridPane grid3 = new GridPane();
        grid3.setHgap(0);
        grid3.setVgap(0);
        grid3.setLayoutX(LEFT_BORDER);
        grid3.setLayoutY(TOP_BORDER);

        GridPane resGrid1 = new GridPane();
        resGrid1.setHgap(13);
        resGrid1.setVgap(0);
        resGrid1.setLayoutX(RESERVED_LEFT_BORDER);
        resGrid1.setLayoutY(RESERVED_TOP_BORDER);
        GridPane resGrid2 = new GridPane();
        resGrid2.setHgap(13);
        resGrid2.setVgap(0);
        resGrid2.setLayoutX(RESERVED_LEFT_BORDER);
        resGrid2.setLayoutY(RESERVED_TOP_BORDER);
        GridPane resGrid3 = new GridPane();
        resGrid3.setHgap(13);
        resGrid3.setVgap(0);
        resGrid3.setLayoutX(RESERVED_LEFT_BORDER);
        resGrid3.setLayoutY(RESERVED_TOP_BORDER);

        GridPane handGrid1 = new GridPane();
        handGrid1.setLayoutX(HAND_LEFT_BORDER);
        handGrid1.setLayoutY(RESERVED_TOP_BORDER);
        GridPane handGrid2 = new GridPane();
        handGrid2.setLayoutX(HAND_LEFT_BORDER);
        handGrid2.setLayoutY(RESERVED_TOP_BORDER);
        GridPane handGrid3 = new GridPane();
        handGrid3.setLayoutX(HAND_LEFT_BORDER);
        handGrid3.setLayoutY(RESERVED_TOP_BORDER);

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                TileView tile1 = new TileView();
                TileView tile2 = new TileView();
                TileView tile3 = new TileView();
                tile1.setPrefSize(TILE_SIZE, TILE_SIZE);
                tile2.setPrefSize(TILE_SIZE, TILE_SIZE);
                tile3.setPrefSize(TILE_SIZE, TILE_SIZE);
                cells1[row][col] = tile1;
                cells2[row][col] = tile2;
                cells3[row][col] = tile3;
                grid1.add(tile1, col, row);
                grid2.add(tile2, col, row);
                grid3.add(tile3, col, row);
            }
        }

        for (int slot = 0; slot < RES_SLOTS; slot++) {
            ReservedTileView tile1 = new ReservedTileView();
            ReservedTileView tile2 = new ReservedTileView();
            ReservedTileView tile3 = new ReservedTileView();
            tile1.setPrefSize(RESERVED_TILE_SIZE, RESERVED_TILE_SIZE);
            tile2.setPrefSize(RESERVED_TILE_SIZE, RESERVED_TILE_SIZE);
            tile3.setPrefSize(RESERVED_TILE_SIZE, RESERVED_TILE_SIZE);
            resCells1[slot] = tile1;
            resCells2[slot] = tile2;
            resCells3[slot] = tile3;
            resGrid1.add(tile1, slot, 0);
            resGrid2.add(tile2, slot, 0);
            resGrid3.add(tile3, slot, 0);
        }

        ReservedTileView tile1 = new ReservedTileView();
        ReservedTileView tile2 = new ReservedTileView();
        ReservedTileView tile3 = new ReservedTileView();
        tile1.setPrefSize(RESERVED_TILE_SIZE, RESERVED_TILE_SIZE);
        tile2.setPrefSize(RESERVED_TILE_SIZE, RESERVED_TILE_SIZE);
        tile3.setPrefSize(RESERVED_TILE_SIZE, RESERVED_TILE_SIZE);
        handCell1[0] = tile1;
        handCell2[0] = tile2;
        handCell3[0] = tile3;
        handGrid1.add(tile1, 0, 0);
        handGrid2.add(tile2, 0, 0);
        handGrid3.add(tile3, 0, 0);

        // --- MAIN CABINS ---
        Image mainCabinIcon1 = null;
        Image mainCabinIcon2 = null;
        Image mainCabinIcon3 = null;
        if (color == BLUE) {
            mainCabinIcon1 = new Image(getClass().getResource("/Images/tiles/GT-new_tiles_16_for web34.jpg").toExternalForm()); // GREEN
            mainCabinIcon2 = new Image(getClass().getResource("/Images/tiles/GT-new_tiles_16_for web61.jpg").toExternalForm()); // YELLOW
            mainCabinIcon3 = new Image(getClass().getResource("/Images/tiles/GT-new_tiles_16_for web52.jpg").toExternalForm()); // RED
        } else if (color == Color.GREEN) {
            mainCabinIcon1 = new Image(getClass().getResource("/Images/tiles/GT-new_tiles_16_for web33.jpg").toExternalForm()); // BLUE
            mainCabinIcon2 = new Image(getClass().getResource("/Images/tiles/GT-new_tiles_16_for web61.jpg").toExternalForm()); // YELLOW
            mainCabinIcon3 = new Image(getClass().getResource("/Images/tiles/GT-new_tiles_16_for web52.jpg").toExternalForm()); // RED
        } else if (color == Color.YELLOW) {
            mainCabinIcon1 = new Image(getClass().getResource("/Images/tiles/GT-new_tiles_16_for web33.jpg").toExternalForm()); // BLUE
            mainCabinIcon2 = new Image(getClass().getResource("/Images/tiles/GT-new_tiles_16_for web34.jpg").toExternalForm()); // GREEN
            mainCabinIcon3 = new Image(getClass().getResource("/Images/tiles/GT-new_tiles_16_for web52.jpg").toExternalForm()); // RED
        } else if (color == Color.RED) {
            mainCabinIcon1 = new Image(getClass().getResource("/Images/tiles/GT-new_tiles_16_for web33.jpg").toExternalForm()); // BLUE
            mainCabinIcon2 = new Image(getClass().getResource("/Images/tiles/GT-new_tiles_16_for web34.jpg").toExternalForm()); // GREEN
            mainCabinIcon3 = new Image(getClass().getResource("/Images/tiles/GT-new_tiles_16_for web61.jpg").toExternalForm()); // YELLOW
        }

        cells1[2][3].setTileImage(mainCabinIcon1);
        cells2[2][3].setTileImage(mainCabinIcon2);
        cells3[2][3].setTileImage(mainCabinIcon3);

        // Absolute positioning - TODO set in proper position
        getChildren().addAll(bgView, grid1, grid2, grid3, resGrid1, resGrid2, resGrid3, handGrid1, handGrid2, handGrid3);

        this.setPrefSize(TOT_WIDTH, TOT_HEIGHT);
        this.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
    }
}
