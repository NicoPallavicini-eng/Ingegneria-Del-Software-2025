package it.polimi.ingsw.galaxytrucker.View.GUIFolder;

import javax.swing.*;

public class GUITrials {
    public GUITrials() {
    }

    public static void main(String[] args) {
        ImageIcon icon = new ImageIcon(GUITrials.class.getResource("/images/cardboard/cardboard-1b.jpg"));
        System.out.println(icon.getImage());
    }
}
