package it.polimi.ingsw.galaxytrucker.View;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.SceneManager;
import javafx.stage.Stage;

public class GUI extends UI{
    private Game game;
    private String nickname;
    private Stage stage;
    private SceneManager sceneManager;

    public GUI(Game game, String nickname) {
        this.game = game;
        this.nickname = nickname;
        this.stage = new Stage();
        this.sceneManager = new SceneManager(this.game, this.stage);
    }
}
