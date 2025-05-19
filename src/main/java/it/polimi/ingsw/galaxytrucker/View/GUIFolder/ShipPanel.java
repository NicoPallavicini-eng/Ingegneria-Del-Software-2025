package it.polimi.ingsw.galaxytrucker.View.GUIFolder;

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

    public ShipPanel() {
        setLayout(null); // Use absolute positioning
        setOpaque(false); // So background shows through

        // Create tile cell
        TileCell centerTile = new TileCell("/images/bg/metal-tile.png");
        centerTile.setBounds(LEFT_BORDER + TILE_SIZE * 3, TOP_BORDER + TILE_SIZE * 2, TILE_SIZE, TILE_SIZE); // Top-left at (406, 277)
        TileCell trialTile1 = new TileCell("/images/bg/metal-tile.png");
        trialTile1.setBounds(LEFT_BORDER + TILE_SIZE * 2, TOP_BORDER + TILE_SIZE, TILE_SIZE, TILE_SIZE);
        TileCell trialTile2 = new TileCell("/images/bg/metal-tile.png");
        trialTile2.setBounds(LEFT_BORDER + TILE_SIZE * 2, TOP_BORDER + TILE_SIZE * 2, TILE_SIZE, TILE_SIZE);
        TileCell reserved1 = new TileCell("/images/bg/metal-reserva.png");
        reserved1.setBounds(RESERVED_LEFT_BORDER, RESERVED_TOP_BORDER, TILE_SIZE, TILE_SIZE);
        TileCell reserved2 = new TileCell("/images/bg/metal-reserva.png");
        reserved2.setBounds(RESERVED_LEFT_BORDER + TILE_SIZE, RESERVED_TOP_BORDER, TILE_SIZE, TILE_SIZE);

        ImageIcon mainCabinIcon = new ImageIcon(getClass().getResource("/images/tiles/GT-new_tiles_16_for web33.jpg"));
        centerTile.setTileIcon(mainCabinIcon);
        ImageIcon trialTile1Icon = new ImageIcon(getClass().getResource("/images/tiles/GT-new_tiles_16_for web7.jpg"));
        trialTile1.setTileIcon(trialTile1Icon);
        ImageIcon trialTile2Icon = new ImageIcon(getClass().getResource("/images/tiles/GT-new_tiles_16_for web19.jpg"));
        trialTile2.setTileIcon(trialTile2Icon);
        ImageIcon reservedTileIcon = new ImageIcon(getClass().getResource("/images/tiles/GT-new_tiles_16_for web19.jpg"));
        reserved1.setTileIcon(reservedTileIcon);
        ImageIcon reservedTileIcon2 = new ImageIcon(getClass().getResource("/images/tiles/GT-new_tiles_16_for web7.jpg"));
        reserved2.setTileIcon(reservedTileIcon2);

        add(centerTile);
        add(trialTile1);
        add(trialTile2);
        add(reserved1);
        add(reserved2);
    }
}