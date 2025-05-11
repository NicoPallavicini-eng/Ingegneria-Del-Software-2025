package it.polimi.ingsw.galaxytrucker.Model.Tiles;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class BioadaptorTile extends Tile implements Serializable {
    private final AlienColor color;
    private boolean isOrange;
    private boolean isPurple;

    @JsonCreator
    public BioadaptorTile(@JsonProperty("northConnector")ConnectorType north, @JsonProperty("southConnector")ConnectorType south, @JsonProperty("eastConnector")ConnectorType east, @JsonProperty("westConnector")ConnectorType west,@JsonProperty("color") AlienColor color){
        super(north, west, south, east);
        this.color = color;
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
