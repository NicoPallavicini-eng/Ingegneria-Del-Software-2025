package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components;

import it.polimi.ingsw.galaxytrucker.Model.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class UserShipGrid extends Pane {
    private static final int ROWS = 5;
    private static final int COLS = 7;
    private static final int RES_SLOTS = 2;
    private static final int TILE_SIZE = 124;
    private static final int RESERVED_TILE_SIZE = 110;
    private static final int TOP_BORDER = 29;
    private static final int BOTTOM_BORDER = 25;
    private static final int LEFT_BORDER = 35;
    private static final int RIGHT_BORDER = 27;
    private static final int TOT_WIDTH = 937;
    private static final int TOT_HEIGHT = 679;
    private static final int RESERVED_TOP_BORDER = 28;
    private static final int RESERVED_LEFT_BORDER = 677;
    private static final int RESERVED_RIGHT_BORDER = 28;
    private static final int GRID_WIDTH = COLS * TILE_SIZE; // 868
    private static final int GRID_HEIGHT = ROWS * TILE_SIZE; // 620

    private final TileView[][] cells = new TileView[ROWS][COLS];
    private final ReservedTileView[] resCells = new ReservedTileView[RES_SLOTS];
    private final ReservedTileView[] handCell = new ReservedTileView[1];

    public UserShipGrid(Color color) {
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
        handGrid.setLayoutX(RESERVED_RIGHT_BORDER);
        handGrid.setLayoutY(RESERVED_TOP_BORDER);

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                TileView tile = new TileView();
                tile.setPrefSize(TILE_SIZE, TILE_SIZE);
                cells[row][col] = tile;
                grid.add(tile, col, row);
            }
        }

        for (int slot = 0; slot < RES_SLOTS; slot++) {
            ReservedTileView tile = new ReservedTileView();
            tile.setPrefSize(RESERVED_TILE_SIZE, RESERVED_TILE_SIZE);
            resCells[slot] = tile;
            resGrid.add(tile, slot, 0);
        }

        ReservedTileView tile = new ReservedTileView();
        tile.setPrefSize(RESERVED_TILE_SIZE, RESERVED_TILE_SIZE);
        handCell[0] = tile;
        handGrid.add(tile, 0, 0);

        // --- MAIN CABIN ---
        Image mainCabinIcon = switch (color) {
            case BLUE -> new Image(getClass().getResource("/Images/tiles/GT-new_tiles_16_for web33.jpg").toExternalForm());
            case GREEN -> new Image(getClass().getResource("/Images/tiles/GT-new_tiles_16_for web34.jpg").toExternalForm());
            case YELLOW -> new Image(getClass().getResource("/Images/tiles/GT-new_tiles_16_for web61.jpg").toExternalForm());
            case RED -> new Image(getClass().getResource("/Images/tiles/GT-new_tiles_16_for web52.jpg").toExternalForm());
            default -> null;
        };

        handCell[0].setTileImage(mainCabinIcon); // tmp test
        resCells[0].setTileImage(mainCabinIcon); // tmp test
        resCells[1].setTileImage(mainCabinIcon);

        cells[2][3].setTileImage(mainCabinIcon);

        // Absolute positioning
        getChildren().addAll(bgView, grid, resGrid, handGrid);

        this.setPrefSize(GRID_WIDTH + LEFT_BORDER + RIGHT_BORDER, GRID_HEIGHT + TOP_BORDER + BOTTOM_BORDER);
        this.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
    }

    /**
     * Sets a tile image at the specified logical row and column.
     */
    public void setTile(int row, int col, Image image) {
        if (row >= 0 && row < ROWS && col >= 0 && col < COLS) {
            cells[row][col].setTileImage(image);
        }
    }

    public TileView getTileView(int row, int col) {
        return (row >= 0 && row < ROWS && col >= 0 && col < COLS) ? cells[row][col] : null;
    }
}
