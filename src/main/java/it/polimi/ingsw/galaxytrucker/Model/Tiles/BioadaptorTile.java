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

    public BioadaptorTile(ConnectorType north, ConnectorType south, ConnectorType east, ConnectorType west, AlienColor color){
        super(north, west, south, east);
        this.color = color;
        this.facingUp = false;
        this.choosable = true;
        this.attached = false;
    }
    public AlienColor getColor() {
        return this.color;
    }

    public AlienColor getAlienColor(){
        AlienColor alienColor = null;
        if (this.isOrange && !this.isPurple){
            alienColor = AlienColor.ORANGE;
        }
        else if (this.isPurple && !this.isOrange){
            alienColor = AlienColor.PURPLE;
        }
        return alienColor;
    }

    public void setColor(AlienColor color){
        if (color == AlienColor.ORANGE){
            this.isOrange = true;
            this.isPurple = false;
        }
        else if (color == AlienColor.PURPLE){
            this.isPurple = true;
            this.isOrange = false;
        }
    }

    @Override
    public void accept(TileVisitor visitor) {
        visitor.visit(this);
    }
}
