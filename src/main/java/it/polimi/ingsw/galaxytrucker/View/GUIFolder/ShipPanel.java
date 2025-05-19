package it.polimi.ingsw.galaxytrucker.View.GUIFolder;

import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;

import javax.swing.*;
import java.awt.*;

public class ShipPanel extends JPanel {
    // 868, 620
    private static final int SHIP_WIDTH = 620; // 124 * 5
    private static final int SHIP_HEIGHT = 868; // 124 * 7
    private static final int TILE_SIZE = 124;
    private static final int TOP_BORDER = 29;
    private static final int LEFT_BORDER = 35;
    private static final int RESERVED_TOP_BORDER = 21;
    private static final int RESERVED_LEFT_BORDER = 670;

    public ShipPanel(Color color) {
        setLayout(null); // TODO (for now absolute position)
        setOpaque(false); // So background shows through

        TileCell centerTile = new TileCell("/images/bg/metal-tile.png");
        centerTile.setBounds(LEFT_BORDER + TILE_SIZE * 3, TOP_BORDER + TILE_SIZE * 2, TILE_SIZE, TILE_SIZE); // Top-left at (406, 277)

        ImageIcon mainCabinIcon = new ImageIcon();
        if (color == Color.BLUE) {
            mainCabinIcon = new ImageIcon(getClass().getResource("/images/tiles/GT-new_tiles_16_for web33.jpg"));
        } else if (color == Color.GREEN) {
            mainCabinIcon = new ImageIcon(getClass().getResource("/images/tiles/GT-new_tiles_16_for web34.jpg"));
        } else if (color == Color.YELLOW) {
            mainCabinIcon = new ImageIcon(getClass().getResource("/images/tiles/GT-new_tiles_16_for web61.jpg"));
        } else if (color == Color.RED) {
            mainCabinIcon = new ImageIcon(getClass().getResource("/images/tiles/GT-new_tiles_16_for web52.jpg"));
        }

        centerTile.setTileIcon(mainCabinIcon);
        add(centerTile);
    }

    public void addTileToShip(Tile tile) {

    }
}