package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components;

import javafx.scene.layout.Pane;

public class OthersShipGrid extends Pane {
    private static final int ROWS = 5;
    private static final int COLS = 7;
    private static final int RES_SLOTS = 2;
    private static final int TILE_SIZE = 124;
    private static final int TOP_BORDER = 29;
    private static final int BOTTOM_BORDER = 25;
    private static final int LEFT_BORDER = 35;
    private static final int RIGHT_BORDER = 27;
    private static final int RESERVED_TOP_BORDER = 21;
    private static final int RESERVED_LEFT_BORDER = 670;
    private static final int GRID_WIDTH = COLS * TILE_SIZE; // 868
    private static final int GRID_HEIGHT = ROWS * TILE_SIZE; // 620
    private static final int TOT_WIDTH = 937;
    private static final int TOT_HEIGHT = 679;

    private final TileView[][] cells = new TileView[ROWS][COLS];
    private final TileView[] resCells = new TileView[RES_SLOTS];
}
