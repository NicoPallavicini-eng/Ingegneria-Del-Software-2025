package it.polimi.ingsw.galaxytrucker.View.GUIFolder;

import javax.swing.*;
import java.awt.*;

public class TileCell extends JPanel {
    private final JLabel tileLabel = new JLabel();
    private final Image backgroundImage;
    private static final int TILE_SIZE = 125;

    public TileCell(String bgImagePath) {
        setLayout(new BorderLayout());
        setOpaque(false);
        setPreferredSize(new Dimension(TILE_SIZE, TILE_SIZE));

        // Load and resize the background image to fit the cell
        backgroundImage = new ImageIcon(getClass().getResource("/Images/tiles/GT-new_tiles_16_for web157.jpg")).getImage();

        tileLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tileLabel.setVerticalAlignment(SwingConstants.CENTER);
        add(tileLabel, BorderLayout.CENTER);
    }

    public void setTileIcon(ImageIcon icon) {
        // Resize the icon to TILE_SIZE × TILE_SIZE before applying
        Image scaledImage = icon.getImage().getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_SMOOTH);
        tileLabel.setIcon(new ImageIcon(scaledImage));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, TILE_SIZE, TILE_SIZE, this);
        }
    }
}