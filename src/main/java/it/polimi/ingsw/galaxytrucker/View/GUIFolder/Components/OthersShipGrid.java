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
    private static final int TILE_SIZE = 41; // 124/3
    private static final int RESERVED_TILE_SIZE = 37; // 110/3
    private static final int TOP_BORDER = 10; // 29/3
    private static final int LEFT_BORDER = 12; // 35/3
    private static final int TOT_WIDTH = 312; // 937/3
    private static final int TOT_HEIGHT = 226; // 679/3
    private static final int RESERVED_TOP_BORDER = 9; // 28/3
    private static final int RESERVED_LEFT_BORDER = 226; // 677/3
    private static final int HAND_LEFT_BORDER = 9; // 28/3

    private final OthersShipTileView[][] cells1 = new OthersShipTileView[ROWS][COLS];
    private final OthersShipTileView[][] cells2 = new OthersShipTileView[ROWS][COLS];
    private final OthersShipTileView[][] cells3 = new OthersShipTileView[ROWS][COLS];

    private final OthersShipReservedTileView[] resCells1 = new OthersShipReservedTileView[RES_SLOTS];
    private final OthersShipReservedTileView[] resCells2 = new OthersShipReservedTileView[RES_SLOTS];
    private final OthersShipReservedTileView[] resCells3 = new OthersShipReservedTileView[RES_SLOTS];

    private final OthersShipReservedTileView[] handCell1 = new OthersShipReservedTileView[1];
    private final OthersShipReservedTileView[] handCell2 = new OthersShipReservedTileView[1];
    private final OthersShipReservedTileView[] handCell3 = new OthersShipReservedTileView[1];

    public OthersShipGrid(Color color) {
        // --- BACKGROUND ---
        Image bgImage = new Image(getClass().getResource("/Images/cardboard/cardboard-1c.png").toExternalForm());
        ImageView bgView1 = new ImageView(bgImage);
        ImageView bgView2 = new ImageView(bgImage);
        ImageView bgView3 = new ImageView(bgImage);
        bgView1.setFitWidth(TOT_WIDTH);
        bgView2.setFitWidth(TOT_WIDTH);
        bgView3.setFitWidth(TOT_WIDTH);
        bgView1.setFitHeight(TOT_HEIGHT);
        bgView2.setFitHeight(TOT_HEIGHT);
        bgView3.setFitHeight(TOT_HEIGHT);
        bgView1.setPreserveRatio(true);
        bgView2.setPreserveRatio(true);
        bgView3.setPreserveRatio(true);
        bgView1.setX(-300);
        bgView2.setX(-300);
        bgView3.setX(-300);
        bgView1.setY(-235);
        bgView2.setY(0);
        bgView3.setY(235);

        GridPane grid1 = new GridPane();
        grid1.setHgap(0);
        grid1.setVgap(0);
        grid1.setLayoutX(LEFT_BORDER + bgView1.getX());
        grid1.setLayoutY(TOP_BORDER + bgView1.getY());
        GridPane grid2 = new GridPane();
        grid2.setHgap(0);
        grid2.setVgap(0);
        grid2.setLayoutX(LEFT_BORDER + bgView2.getX());
        grid2.setLayoutY(TOP_BORDER + bgView2.getY());
        GridPane grid3 = new GridPane();
        grid3.setHgap(0);
        grid3.setVgap(0);
        grid3.setLayoutX(LEFT_BORDER + bgView3.getX());
        grid3.setLayoutY(TOP_BORDER + bgView3.getY());

        GridPane resGrid1 = new GridPane();
        resGrid1.setHgap(13);
        resGrid1.setVgap(0);
        resGrid1.setLayoutX(RESERVED_LEFT_BORDER + bgView1.getX());
        resGrid1.setLayoutY(RESERVED_TOP_BORDER + bgView1.getY());
        GridPane resGrid2 = new GridPane();
        resGrid2.setHgap(13);
        resGrid2.setVgap(0);
        resGrid2.setLayoutX(RESERVED_LEFT_BORDER + bgView2.getX());
        resGrid2.setLayoutY(RESERVED_TOP_BORDER + bgView2.getY());
        GridPane resGrid3 = new GridPane();
        resGrid3.setHgap(13);
        resGrid3.setVgap(0);
        resGrid3.setLayoutX(RESERVED_LEFT_BORDER + bgView3.getX());
        resGrid3.setLayoutY(RESERVED_TOP_BORDER + bgView3.getY());

        GridPane handGrid1 = new GridPane();
        handGrid1.setLayoutX(HAND_LEFT_BORDER + bgView1.getX());
        handGrid1.setLayoutY(RESERVED_TOP_BORDER + bgView1.getY());
        GridPane handGrid2 = new GridPane();
        handGrid2.setLayoutX(HAND_LEFT_BORDER + bgView2.getX());
        handGrid2.setLayoutY(RESERVED_TOP_BORDER + bgView2.getY());
        GridPane handGrid3 = new GridPane();
        handGrid3.setLayoutX(HAND_LEFT_BORDER + bgView3.getX());
        handGrid3.setLayoutY(RESERVED_TOP_BORDER + bgView3.getY());

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                OthersShipTileView tile1 = new OthersShipTileView();
                OthersShipTileView tile2 = new OthersShipTileView();
                OthersShipTileView tile3 = new OthersShipTileView();
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
            OthersShipReservedTileView tile1 = new OthersShipReservedTileView();
            OthersShipReservedTileView tile2 = new OthersShipReservedTileView();
            OthersShipReservedTileView tile3 = new OthersShipReservedTileView();
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

        OthersShipReservedTileView tile1 = new OthersShipReservedTileView();
        OthersShipReservedTileView tile2 = new OthersShipReservedTileView();
        OthersShipReservedTileView tile3 = new OthersShipReservedTileView();
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
        getChildren().addAll(bgView1, bgView2, bgView3, grid1, grid2, grid3, resGrid1, resGrid2, resGrid3, handGrid1, handGrid2, handGrid3);

        this.setPrefSize(TOT_WIDTH, TOT_HEIGHT);
        this.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
    }
}
