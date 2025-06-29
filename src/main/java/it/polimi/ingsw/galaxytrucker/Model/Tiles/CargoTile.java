package it.polimi.ingsw.galaxytrucker.Model.Tiles;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * CargoTile represent Cargo Tile of Board game
 */
public class CargoTile extends Tile implements Serializable {
    private final int slotsNumber;
    private final boolean fitsRed;
    private List<Integer> tileContent;
    private boolean attached;
    private boolean facingUp;
    private boolean choosable;
    private boolean reserved;
    private String name;

    /**
     * Constructor of Cargo Tile,some parameters are set to default values
     * @param north
     * @param south
     * @param east
     * @param west
     * @param slotsNumber
     * @param fitsRed
     * @param tileContent
     */
    public CargoTile(ConnectorType north, ConnectorType south, ConnectorType east, ConnectorType west, int slotsNumber, boolean fitsRed, List<Integer> tileContent) {
        super(north, south, east, west);
        this.fitsRed = fitsRed;
        this.slotsNumber = slotsNumber;
        //this.tileContent = tileContent;
        this.facingUp = false;
        this.choosable = true;
        this.attached = false;
        this.reserved = false;
        this.tileContent = new ArrayList<>();

    }

    /**
     * This function returns List of Blocks that cargo has on it
     * @return List<Integer>
     */
    public List<Integer> getTileContent() {
        return tileContent;
    }

    /**This function adds Block(Cargo) in to existing List
     * @param block
     */
    public void addBlock(Integer block) {
        if(block == 4 && !fitsRed){
            throw new IllegalEventException("You can't put red cargo in this normal cargo tile");
        }
        this.tileContent.add(block);
    }

    /**
     * This function removes a Block(cargo) froma CargoTile
     * @param block
     */
    public void removeBlock(Integer block) {
        tileContent.remove(block);
    }

    /**
     * This function tells you whether this cargo can fit red Blocks or not
     * @return boolean
     */
    public boolean fitsRed() {
        return fitsRed;
    }

    /**
     * This function return the number of Slots that CargoTile can carry
     * @return int
     */
    public int getSlotsNumber() {
        return slotsNumber;
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