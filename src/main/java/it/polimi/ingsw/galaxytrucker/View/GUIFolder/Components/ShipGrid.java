package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components;

import it.polimi.ingsw.galaxytrucker.Model.Color;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class ShipGrid extends StackPane {
    private static final int ROWS = 5;
    private static final int COLS = 7;
    private static final int TILE_SIZE = 124;
    private static final int TOP_BORDER = 29;
    private static final int BOTTOM_BORDER = 25;
    private static final int LEFT_BORDER = 35;
    private static final int RIGHT_BORDER = 27;
    private static final int RESERVED_TOP_BORDER = 21;
    private static final int RESERVED_LEFT_BORDER = 670;
    private static final int GRID_WIDTH = COLS * TILE_SIZE; // 875
    private static final int GRID_HEIGHT = ROWS * TILE_SIZE; // 625

    private final TileView[][] cells = new TileView[ROWS][COLS];

    public ShipGrid(Color color) {
        // --- BACKGROUND ---
        Image bgImage = new Image(getClass().getResource("/Images/cardboard/cardboard-1b.jpg").toExternalForm());
        ImageView bgView = new ImageView(bgImage);
        bgView.setFitWidth(GRID_WIDTH + LEFT_BORDER + RIGHT_BORDER);
        bgView.setFitHeight(GRID_HEIGHT + TOP_BORDER + BOTTOM_BORDER);
        setPrefSize(bgView.getFitWidth(), bgView.getFitHeight());
        setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
        bgView.setPreserveRatio(false);  // TODO
        bgView.setLayoutX(0);
        bgView.setLayoutY(0);

        GridPane grid = new GridPane();
        grid.setHgap(0);
        grid.setVgap(0);
        grid.setLayoutX(LEFT_BORDER);  // Exact pixel position
        grid.setLayoutY(TOP_BORDER);   // Exact pixel position

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                TileView tile = new TileView(null);
                tile.setPrefSize(TILE_SIZE, TILE_SIZE);
                cells[row][col] = tile;
                grid.add(tile, col, row);
            }
        }

        // --- MAIN CABIN ---
        Image mainCabinIcon = switch (color) {
            case BLUE -> new Image(getClass().getResource("/Images/tiles/GT-new_tiles_16_for web33.jpg").toExternalForm());
            case GREEN -> new Image(getClass().getResource("/Images/tiles/GT-new_tiles_16_for web34.jpg").toExternalForm());
            case YELLOW -> new Image(getClass().getResource("/Images/tiles/GT-new_tiles_16_for web61.jpg").toExternalForm());
            case RED -> new Image(getClass().getResource("/Images/tiles/GT-new_tiles_16_for web52.jpg").toExternalForm());
            default -> null;
        };

        cells[2][3].setTileImage(mainCabinIcon);

        // Absolute positioning
        getChildren().addAll(bgView, grid);
        setPrefSize(bgView.getFitWidth(), bgView.getFitHeight());
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
