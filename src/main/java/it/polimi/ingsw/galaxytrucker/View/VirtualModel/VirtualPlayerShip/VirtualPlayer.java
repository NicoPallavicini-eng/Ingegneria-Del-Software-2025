package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualPlayerShip;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualColor;

public class VirtualPlayer {
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
}
