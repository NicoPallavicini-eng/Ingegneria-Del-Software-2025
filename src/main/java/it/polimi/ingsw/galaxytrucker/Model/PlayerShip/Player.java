package it.polimi.ingsw.galaxytrucker.Model.PlayerShip;

import it.polimi.ingsw.galaxytrucker.Model.Color;

import java.io.Serializable;



/**
 * This class represent actual Player of Game
 */
public class Player implements Serializable {
    private String nickname;
    private Ship ship;
    private int input;
    private boolean engages;
    private String playerIp;
    private boolean onlineStatus;
    private boolean checked = false;

    /**
     * This function tells whether Player was checked during a specific State or not
     * @return boolean
     */
    public boolean isChecked() {
        return checked;
    }

    /**
     * This function sets whether Player was checked during a specific State or not
     * @param checked boolean
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    /**
     * This Player Construct build Player with default settings
     * @param playerIp
     * @param nickname
     * @param color
     */
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

    /**
     * This function tells you whether Player engages(Specific Card action) or not
     * @return boolean
     */
    //per cosa serve Engages?
    public boolean getEngages() {
        return engages;
    }

    /**
     * This function sets Player engages (Specific Card action)
     * @param engages boolean
     */
    public void setEngages(boolean engages) {
        this.engages = engages;
    }

    public int getInput() {
        return input;
    }

    public void setInput(int input) {
        this.input = input;
    }

    /**
     * This function returns a nickname of the Player
     * @return String
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * This function sets a nickname of Player
     * @param nickname String
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * This function returns a Ship of the Player
     * @return Ship
     */
    public Ship getShip() {
        return ship;
    }

    /**This function sets a Ship of Player
     * @param ship Ship
     */
    public void setShip(Ship ship) {
        this.ship = ship;
    }

    /**
     * This function returns a Ip of player
     * @return String
     */
    public String getPlayerIp() {
        return playerIp;
    }

    /**
     * This function tells you whether player is connected to the game or not
     * @return boolean
     */
    public boolean getOnlineStatus() {
        return onlineStatus;
    }

    /**
     * This function sets online status of Player
     * @param onlineStatus boolean
     */
    public void setOnlineStatus(boolean onlineStatus) {
        this.onlineStatus = onlineStatus;
    }
}
