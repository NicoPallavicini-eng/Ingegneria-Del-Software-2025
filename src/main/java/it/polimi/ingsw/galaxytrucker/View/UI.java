package it.polimi.ingsw.galaxytrucker.View;

import it.polimi.ingsw.galaxytrucker.Controller.Server.ServerController;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.ParsingJSON;

public class UI {
    public static void main(String[] args){
        Game game = ServerController.getGame();
        new ParsingJSON();
        TUI tui = new TUI();
        tui.viewTilePile(game);
    }
}
