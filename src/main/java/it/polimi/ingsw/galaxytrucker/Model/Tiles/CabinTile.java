package it.polimi.ingsw.galaxytrucker.Model.Tiles;

import java.io.Serializable;

public class CabinTile extends Tile implements Serializable {
    private CabinInhabitants inhabitants;
    private final boolean mainCapsule;
    private int pinkAdaptors;
    private int orangeAdaptors;
    private AlienColor alienColor;

    public CabinTile(ConnectorType north, ConnectorType south, ConnectorType east, ConnectorType west, CabinInhabitants inhabitants, boolean mainCapsule, int pinkAdaptors, int orangeAdaptors) {
        super(north, west, south, east);
        this.inhabitants = inhabitants;
        this.mainCapsule = mainCapsule;
        this.pinkAdaptors = pinkAdaptors;
        this.orangeAdaptors = orangeAdaptors;
    }
    public AlienColor getAlienColor() {
        return alienColor;
    }
    public void setAlienColor(AlienColor alienColor) {
        this.alienColor = alienColor;
    }
    public void updateInhabitants(CabinInhabitants inhabitants){
        this.inhabitants = inhabitants;
    }

    public CabinInhabitants getInhabitants() {
        return inhabitants;
    }

    public void removePinkAdaptors() {
        pinkAdaptors -= 1;
    }

    public void removeOrangeAdaptors() {
        orangeAdaptors -= 1;
    }

    public boolean isMainCapsule() {
        return this.mainCapsule;
    }

    public void setPurple(int purple){
        pinkAdaptors = purple;
    }

    public void setOrange(int orange){
        orangeAdaptors = orange;
    }

    public int getPurple() {
        return pinkAdaptors;
    }

    public int getOrange() {
        return orangeAdaptors;
    }

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

    @Override
    public void accept(TileVisitor visitor) {
        visitor.visit(this);
    }
}
