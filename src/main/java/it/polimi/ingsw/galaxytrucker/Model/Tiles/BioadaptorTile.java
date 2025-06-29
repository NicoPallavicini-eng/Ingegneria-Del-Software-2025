package it.polimi.ingsw.galaxytrucker.Model.Tiles;

import java.io.Serializable;


/**
 * BioadptorTile represent Bioadaptor Tile of Board game
 */
public class BioadaptorTile extends Tile implements Serializable {
    private final AlienColor color;
    private boolean isOrange;
    private boolean isPurple;
    private boolean attached;
    private boolean facingUp;
    private boolean choosable;
    private boolean reserved;
    private String name;

    /**
     * Constructor of Bioadaptor Tile,some parameters are set to default values
     * @param north
     * @param south
     * @param east
     * @param west
     * @param color
     */
    public BioadaptorTile(ConnectorType north, ConnectorType south, ConnectorType east, ConnectorType west, AlienColor color){
        super(north, south, east, west);
        this.color = color;
        this.facingUp = false;
        this.choosable = true;
        this.attached = false;
        this.reserved = false;
    }

    public AlienColor getColor() {
        return this.color;
    }

    public AlienColor getAlienColor(){
        return color;
    }

    /**
     * This function is used to find the Tile Type
     * @param visitor The visitor to accept.
     */
    @Override
    public void accept(TileVisitor visitor) {
        visitor.visit(this);
    }
}
