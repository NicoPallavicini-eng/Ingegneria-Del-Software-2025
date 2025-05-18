package it.polimi.ingsw.galaxytrucker.Network;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;

import java.io.Serializable;

//type String or Game
public class Message implements Serializable {
    private final String type;
    private final Game game;
    private  String nickname;
    private Tile tile = null;
    private final String message;
    public Message(String type,Game game, String message) {
        this.type = type;
        this.game = game;
        this.message = message;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public boolean isStringMessage() {
        return type.equals("String");
    }
    public String getMessage() {
        return message;
    }
//    public Game getGame(){
//        return game;
//    }
    public String getType() {
        return type;
    }
    public Tile getTile() {
        return tile;
    }
    public void setTile(Tile tile) {
        this.tile = tile;
    }
    public String getNickname() {
        return nickname;
    }
    public Game getGame() {
        return game;
    }
}
