package it.polimi.ingsw.galaxytrucker.View.GUIFolder;

import javax.swing.*;
import java.awt.*;

public class GalaxyTruckerGUI extends JFrame {
    public GalaxyTruckerGUI() {
        setTitle("Galaxy Trucker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BackgroundPanel background = new BackgroundPanel("/images/gui/background.jpg");
        background.setPreferredSize(new Dimension(background.getImageWidth(), background.getImageHeight()));
        background.setLayout(null); // for absolute positioning if needed

        ShipPanel shipPanel = new ShipPanel();
        shipPanel.setOpaque(false);
        shipPanel.setBounds(0, 0, background.getImageWidth(), background.getImageHeight()); // adjust as needed

        background.add(shipPanel);
        add(background);
        pack(); // auto-resizes frame to match background's preferred size
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GalaxyTruckerGUI::new);
    }
}