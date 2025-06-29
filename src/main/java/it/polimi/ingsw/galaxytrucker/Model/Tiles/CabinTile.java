package it.polimi.ingsw.galaxytrucker.Model.Tiles;

import it.polimi.ingsw.galaxytrucker.Model.Color;

import java.io.Serializable;


/**
 * CabinTile represent a Cabin Tile from BoardGame
 */
public class CabinTile extends Tile implements Serializable {
    private CabinInhabitants inhabitants;
    private final boolean mainCapsule;
    private Color color;
    private int pinkAdaptors;
    private int orangeAdaptors;
    private AlienColor alienColor;
    private boolean attached;
    private boolean facingUp;
    private boolean choosable;
    private boolean reserved;
    private String name;

    /**
     * Constructor of Cabin Tile,some parameters are set to default values
     * @param north
     * @param south
     * @param east
     * @param west
     * @param inhabitants
     * @param mainCapsule
     * @param color
     * @param pinkAdaptors
     * @param orangeAdaptors
     */
    public CabinTile(ConnectorType north, ConnectorType south, ConnectorType east, ConnectorType west, CabinInhabitants inhabitants, boolean mainCapsule, Color color, int pinkAdaptors, int orangeAdaptors) {
        super(north, south, east, west);
        this.inhabitants = inhabitants;
        this.mainCapsule = mainCapsule;

        if (mainCapsule) {
            this.facingUp = true;
            this.choosable = false;
        } else {
            this.facingUp = false;
            this.choosable = true;
        }

        this.color = color;
        this.attached = false;
        this.reserved = false;
        this.pinkAdaptors = pinkAdaptors;
        this.orangeAdaptors = orangeAdaptors;
    }

    public Color getColor() {
        return color;
    }

    /**
     * This function returns an Alien Color if it's present
     * @return AlienColor
     */
    public AlienColor getAlienColor() {
        return alienColor;
    }

    /**
     * This function sets an AlienColor of CabinTile
     * @param alienColor AlienColor
     */
    public void setAlienColor(AlienColor alienColor) {
        this.alienColor = alienColor;
    }

    /**
     * this function set Inhabitants
     * @param inhabitants Inhabitants
     */
    public void updateInhabitants(CabinInhabitants inhabitants){
        this.inhabitants = inhabitants;
    }

    /**
     * This function return the Inhabitants that occupy the cabin
     * @return Inhabitants
     */
    public CabinInhabitants getInhabitants() {
        return inhabitants;
    }

    /**
     * This function removes 1 pink adaptors that connected to Cabin
     */
    public void removePinkAdaptors() {
        pinkAdaptors -= 1;
    }
    /**
     * This function removes 1 orange adaptors that connected to Cabin
     */
    public void removeOrangeAdaptors() {
        orangeAdaptors -= 1;
    }

    /**
     * This function tells you whether the cabin is Central Cabin or not
     * @return boolean
     */
    public boolean isMainCapsule() {
        return this.mainCapsule;
    }

    /**
     * This function sets the number of pink adpators that are connected to Cabin
     * @param purple int
     */
    public void setPurple(int purple){
        pinkAdaptors = purple;
    }
    /**
     * This function sets the number of orange adpators that are connected to Cabin
     * @param orange int
     */
    public void setOrange(int orange){
        orangeAdaptors = orange;
    }

    /**
     *  This function gets the number of pink adpators that are connected to Cabin
     * @return int
     */
    public int getPurple() {
        return pinkAdaptors;
    }

    /**
     * This function gets the number of orange adpators that are connected to Cabin
     * @return int
     */
    public int getOrange() {
        return orangeAdaptors;
    }

    /**
     * These function removes Inhabitants from Cabin Tile
     * @param people
     * @throws InhabitantsException
     */
    public void removeInhabitants(int people) throws InhabitantsException{
        if (people >= 0) {
            if (inhabitants == CabinInhabitants.NONE){
                throw new InhabitantsException("Tile has no people inside.");
            }
            else if (inhabitants == CabinInhabitants.ONE){
                if (people == 1) {
                    this.inhabitants = CabinInhabitants.NONE;
                } else if (people > 1) {
                    throw  new InhabitantsException("There are not enough people in this tile");
                }
            }
            else if (inhabitants == CabinInhabitants.TWO){
                if (people == 1){
                    this.inhabitants = CabinInhabitants.ONE;
                }
                else if (people == 2){
                    this.inhabitants = CabinInhabitants.NONE;
                }
                else if (people > 2){
                    throw new InhabitantsException("There are not enough people in this tile");
                }
            }
            else if(inhabitants == CabinInhabitants.ALIEN){
                if (people == 1){
                    this.inhabitants = CabinInhabitants.NONE;
                }
                else if (people > 1){
                    throw new InhabitantsException("There are not enough alien in this tile");
                }
            }
        }
        else{
            throw new InhabitantsException("Cannot remove a negative number of people");
        }
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
