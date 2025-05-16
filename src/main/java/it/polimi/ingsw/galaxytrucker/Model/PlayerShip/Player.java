package it.polimi.ingsw.galaxytrucker.Model.PlayerShip;

import it.polimi.ingsw.galaxytrucker.Model.Color;

import java.io.Serializable;

public class Player implements Serializable {
    private String nickname;
    private Ship ship;
    private int input;
    private boolean engages;

    private String playerIp;
    private boolean onlineStatus;

    public Player(String playerIp, String nickname, Color color){
        this.playerIp = playerIp;
        this.nickname = nickname;
        //assegno
        setShip(new Ship(color));
        //setCredits(0);
        setOnlineStatus(true);
        //setTravelDays(Integer.valueOf(0));
        engages = false;
        input = 0;
    }

    //per cosa serve Engages?
    public boolean getEngages() {
        return engages;
    }

    public void setEngages(boolean engages) {
        this.engages = engages;
    }

    public int getInput() {
        return input;
    }

    public void setInput(int input) {
        this.input = input;
    }

    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Ship getShip() {
        return ship;
    }
    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public String getPlayerIp() {
        return playerIp;
    }

    public boolean getOnlineStatus() {
        return onlineStatus;
    }
    public void setOnlineStatus(boolean onlineStatus) {
        this.onlineStatus = onlineStatus;
    }
}
