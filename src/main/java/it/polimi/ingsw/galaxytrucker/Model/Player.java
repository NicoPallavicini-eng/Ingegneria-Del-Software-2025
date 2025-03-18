package it.polimi.ingsw.galaxytrucker.Model;

public class Player {
    private String nickname;
    private Ship ship;
    //private int credits;
    private String playerIp;
    private boolean onlineStatus;
    //private Integer travelDays;
    //private boolean outOfSpace;

    public Player(String playerIp,String nickname){
        this.playerIp = playerIp;
        setNickname(nickname);
        setShip(new Ship());
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


    /*
    public boolean isOutOfSpace() {
        return outOfSpace;
    }
    public void setOutOfSpace(boolean outOfSpace) {
        this.outOfSpace = outOfSpace;
    }
     */
}
