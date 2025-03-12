package it.polimi.ingsw.galaxytrucker.Model;

public class Player {
    private String nickname;
    private Ship ship;
    private int credits;
    private String playerIp;
    private boolean onlineStatus;
    private Integer travelDays;

    public Player(String playerIp){
        this.playerIp = playerIp;

    }

    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void addCredits(int credits) {
        this.credits = credits;
    }
    public int getCredits() {
        return credits;
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

    public Integer getTravelDays() {
        return travelDays;
    }
    public void setTravelDays(Integer travelDays) {
        this.travelDays = travelDays;
    }
}
