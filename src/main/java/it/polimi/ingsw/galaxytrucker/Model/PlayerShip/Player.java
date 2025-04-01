package it.polimi.ingsw.galaxytrucker.Model.PlayerShip;

import it.polimi.ingsw.galaxytrucker.Model.Color;

public class Player {
    private String nickname;
    private Ship ship;

    private String playerIp;
    private boolean onlineStatus;


    public Player(String playerIp, String nickname, Color color){
        this.playerIp = playerIp;
        setNickname(nickname);
        //assegno
        setShip(new Ship(color));
        //setCredits(0);
        setOnlineStatus(true);
        //setTravelDays(Integer.valueOf(0));
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
