package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualPlayerShip;

import it.polimi.ingsw.galaxytrucker.Controller.ViewObserver.Listener;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Hourglass;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.TilePile;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualColor;

public class VirtualPlayer implements Listener {
    private String nickname;
    private VirtualShip ship;
    private int input;
    private boolean engages;

    private String playerIp;
    private boolean onlineStatus;

    public VirtualPlayer(String playerIp, String nickname, VirtualColor color){
        this.playerIp = playerIp;
        // setNickname(nickname);
        // setShip(new VirtualShip(color));
        // setOnlineStatus(true);
        engages = false;
        input = 0;
    }

    public boolean getEngages() {
        return engages;
    }

    public int getInput() {
        return input;
    }

    public String getNickname() {
        return nickname;
    }

    public VirtualShip getShip() {
        return ship;
    }

    public String getPlayerIp() {
        return playerIp;
    }

    public boolean getOnlineStatus() {
        return onlineStatus;
    }

    @Override
    public void update(Player player) {
        // set all variables
    }

    public void update(Ship ship) {}
    public void update(Hourglass hourglass) {}
    public void update(TilePile tilePile) {}
    public void update(Game game) {}
}
