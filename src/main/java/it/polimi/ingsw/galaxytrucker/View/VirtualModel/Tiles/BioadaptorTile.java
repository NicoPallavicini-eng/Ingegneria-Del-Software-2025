package it.polimi.ingsw.galaxytrucker.View.VirtualModel.Tiles;

public class BioadaptorTile extends Tile {
    private final AlienColor color;
    private boolean isOrange;
    private boolean isPurple;

    public BioadaptorTile(ConnectorType north, ConnectorType west, ConnectorType south, ConnectorType east, AlienColor color){
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
