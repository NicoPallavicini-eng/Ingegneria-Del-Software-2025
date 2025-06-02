package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components;

import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.BuildingSceneTilePile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public TilePileGrid(BuildingSceneTilePile buildingSceneTilePile) {
        GridPane pile = new GridPane();
        this.buildingSceneTilePile = buildingSceneTilePile;
        pile.setHgap(5);
        pile.setVgap(5);
        pile.setLayoutX(LEFT_BORDER);
        pile.setLayoutY(TOP_BORDER);

        // Step 1: Create list of image paths
        List<String> imagePaths = new ArrayList<>();
        for (int i = 0; i < 152; i++) {
            String path = String.format("/Images/tiles/TilePile/GT-new_tiles_16_for web%d.jpg", i);
            imagePaths.add(path);
        }

        // Step 2: Shuffle the image paths
        Collections.shuffle(imagePaths);

        Image mainCabinIcon =  new Image(getClass().getResource("/Images/tiles/GT-new_tiles_16_for web33.jpg").toExternalForm());

        // Step 3: Assign images to grid cells from shuffled list
        int imageIndex = 0;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                TilePileTileView tile = new TilePileTileView();
                tile.setPrefSize(TILE_SIZE, TILE_SIZE);

                try {
                    String imagePath = imagePaths.get(imageIndex);
                    Image img = new Image(getClass().getResource(imagePath).toExternalForm());
                    tile.setTileImage(img); // Assuming TilePileTileView has a setTileImage method
                } catch (Exception e) {
                    System.err.println("Failed to load image at: " + (imageIndex));
                    e.printStackTrace();
                }

                int finalRow = row;
                int finalCol = col;
                tile.getOverlayButton().setOnAction(e -> {
                    System.out.println("Clicked tile at: " + finalRow + ", " + finalCol);
                    if (tile.isClickable()) {
                        buildingSceneTilePile.pickUpTile(tile);
                    } else {
                        // TODO print error: not clickable
                    }
                });

                cells[row][col] = tile;
                pile.add(tile, col, row);
                imageIndex++;

                if (row == ROWS - 1 && col == 1) {
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

    public void setDefault(ImageView img) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (cells[i][j].getTileImage().getImage() == img.getImage()) {
                    cells[i][j].setOpacity(1);
                    cells[i][j].setClickable(true);
                }
                if (i == ROWS - 1 && j == 1) {
                    break;
                }
            }
        }
    }
}
