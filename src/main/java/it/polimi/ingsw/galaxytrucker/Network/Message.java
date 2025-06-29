package it.polimi.ingsw.galaxytrucker.Network;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;

import java.io.Serializable;

/** * The Message class represents a message sent between the client and server.
 * It contains information about the type of message, the game it is associated with,
 * the nickname of the player, the tile (if applicable), and the actual message content.
 */
public class Message implements Serializable {
    private final String type;
    private final Game game;
    private  String nickname;
    private Tile tile = null;
    private final String message;
    private String gameID = null;

    /**
     * Constructs a Message with the specified type, game, and message content.
     *
     * @param type    the type of the message (e.g., "String", "Tile")
     * @param game    the game associated with this message
     * @param message the content of the message
     */
    public Message(String type,Game game, String message) {
        this.type = type;
        this.game = game;
        this.message = message;
    }

    /**
     * Constructs a Message with the specified type, game, tile, and message content.
     *
     * @param type    the type of the message (e.g., "Tile")
     * @param game    the game associated with this message
     * @param tile    the tile associated with this message
     * @param message the content of the message
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isStringMessage() {
        return type.equals("String");
    }

    public String getMessage() {
        return message;
    }

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
    public String getGameID() {
        return gameID;
    }
    public void setGameID(String gameID) {
        this.gameID = gameID;
    }
}
