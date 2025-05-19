package it.polimi.ingsw.galaxytrucker.View.GUIFolder;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private final Image backgroundImage;
    private final int imageWidth;
    private final int imageHeight;

    public BackgroundPanel(String imagePath) {
        ImageIcon icon = new ImageIcon(getClass().getResource("/Images/cardboard/cardboard-1b.jpg"));
        this.backgroundImage = icon.getImage();
        this.imageWidth = icon.getIconWidth();
        this.imageHeight = icon.getIconHeight();
        setLayout(null); // Absolute layout or change as needed
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, null);
    }
}