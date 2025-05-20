package it.polimi.ingsw.galaxytrucker.Model.Tiles;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class BioadaptorTile extends Tile implements Serializable {
    private final AlienColor color;
    private boolean isOrange;
    private boolean isPurple;
    private boolean attached;
    private boolean facingUp;
    private boolean choosable;
    private boolean reserved;

    public BioadaptorTile(ConnectorType north, ConnectorType south, ConnectorType east, ConnectorType west, AlienColor color){
        super(north, west, south, east);
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

    @Override
    public void accept(TileVisitor visitor) {
        visitor.visit(this);
    }
}
