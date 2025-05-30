package it.polimi.ingsw.galaxytrucker.View;

import it.polimi.ingsw.galaxytrucker.Controller.Server.ServerController;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;

public class UI {
    public static void main(String[] args){
        Game game = ServerController.getGame();

        UIInterface ui;
        boolean useGui = false; // or read from args/config

        if (useGui) {
            ui = new GUI(game, "playerNickname"); // TODO get properly
        } else {
            ui = new TUI();
        }

        ui.printTitle();
        ui.start();
    }
}
