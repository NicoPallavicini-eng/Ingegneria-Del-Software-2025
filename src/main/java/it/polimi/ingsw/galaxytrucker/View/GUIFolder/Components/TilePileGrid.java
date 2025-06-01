package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components;

import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class TilePileGrid extends Pane {
    private static final int ROWS = 10;
    private static final int COLS = 16;
    private static final int TILE_SIZE = 54;
    private static final int TOP_BORDER = 45;
    private static final int LEFT_BORDER = 0;
    private static final int TOT_WIDTH = 937;
    private static final int TOT_HEIGHT = 679;

    private final TilePileTileView[][] cells = new TilePileTileView[ROWS][COLS];

    public TilePileGrid() {
        GridPane pile = new GridPane();
        pile.setHgap(5);
        pile.setVgap(5);
        pile.setLayoutX(LEFT_BORDER);
        pile.setLayoutY(TOP_BORDER);

        Image mainCabinIcon =  new Image(getClass().getResource("/Images/tiles/GT-new_tiles_16_for web33.jpg").toExternalForm());

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                TilePileTileView tile = new TilePileTileView();
                tile.setPrefSize(TILE_SIZE, TILE_SIZE);
                int finalRow = row;
                int finalCol = col;
                tile.getOverlayButton().setOnAction(e -> {
                    System.out.println("Clicked tile at: " + finalRow + ", " + finalCol);
                });
                cells[row][col] = tile;
                // cells[row][col].setTileImage(mainCabinIcon);
                // ^ tmp, uncomment to see the grid filled
                pile.add(tile, col, row);
            }
        }

        // Absolute positioning
        getChildren().add(pile);

        this.setPrefSize(TOT_WIDTH, TOT_HEIGHT);
        this.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
    }
}
