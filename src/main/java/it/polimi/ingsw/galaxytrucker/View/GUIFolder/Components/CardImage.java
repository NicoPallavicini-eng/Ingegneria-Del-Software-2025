package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components;

import javafx.scene.image.Image;

/**
 * Enum representing the different card images used in the GUI.
 * Each enum constant holds the file name, image, and level of the card.
 */
public enum CardImage {
    openSpaceCard1("GT-cards_I_IT_4.jpg", 1),
    openSpaceCard2("GT-cards_I_IT_5.jpg", 1),
    openSpaceCard3("GT-cards_I_IT_6.jpg", 1),
    openSpaceCard4("GT-cards_II_IT_5.jpg", 2),
    openSpaceCard5("GT-cards_II_IT_6.jpg", 2),
    openSpaceCard6("GT-cards_II_IT_7.jpg", 2),
    openSpaceCard7("GT-cards_I_IT_7.jpg", 1),
    planetsCard1("GT-cards_I_IT_12.jpg", 1),
    planetsCard2("GT-cards_I_IT_13.jpg", 1),
    planetsCard3("GT-cards_I_IT_11.jpg", 1),
    planetsCard4("GT-cards_I_IT_14.jpg", 1),
    planetsCard5("GT-cards_II_IT_13.jpg", 2),
    planetsCard6("GT-cards_II_IT_12.jpg", 2),
    planetsCard7("GT-cards_II_IT_14.jpg", 2),
    planetsCard8("GT-cards_II_IT_11.jpg", 2),
    meteorsCard1("GT-cards_I_IT_10.jpg", 1),
    meteorsCard2("GT-cards_I_IT_8.jpg", 1),
    meteorsCard3("GT-cards_I_IT_9.jpg", 1),
    meteorsCard4("GT-cards_II_IT_9.jpg", 2),
    meteorsCard5("GT-cards_II_IT_8.jpg", 2),
    meteorsCard6("GT-cards_II_IT_10.jpg", 2),
    stationCard1("GT-cards_I_IT_18.jpg", 1),
    stationCard2("GT-cards_I_IT_19.jpg", 1),
    stationCard3("GT-cards_II_IT_18.jpg", 2),
    stationCard4("GT-cards_II_IT_19.jpg", 2),
    ShipCard1("GT-cards_I_IT_17.jpg", 1),
    ShipCard2("GT-cards_I_IT_16.jpg", 1),
    ShipCard3("GT-cards_II_IT_17.jpg", 2),
    ShipCard4("GT-cards_II_IT_16.jpg", 2),
    slaversCard1("GT-cards_I_IT_0.jpg", 1),
    slaversCard2("GT-cards_II_IT_0.jpg", 2),
    smugglersCard1("GT-cards_I_IT_1.jpg", 1),
    smugglersCard2("GT-cards_II_IT_1.jpg", 2),
    piratesCard1("GT-cards_I_IT_2.jpg", 1),
    piratesCard2("GT-cards_II_IT_2.jpg", 2),
    stardustCard1("GT-cards_I_IT_3.jpg", 1), // TODO undertsnad why this card isnt view properly
    stardustCard2("GT-cards_II_IT_3.jpg", 2),
    combatZoneCardL("GT-cards_I_IT_15.jpg", 1),
    combatZoneCardNotL("GT-cards_II_IT_15.jpg", 2),
    epidemicCard1("GT-cards_II_IT_4.jpg", 2);

    private final String fileName;
    private final Image image;
    private final int level;
    /**
     * Constructs a CardImage enum constant.
     *
     * @param fileName the file name of the image
     * @param lev the level of the card (1 or 2)
     */
    CardImage(String fileName, int lev) {
        if (lev == 1) {
            this.fileName = fileName;
            this.level = lev;
            this.image = new Image(getClass().getResource("/Images/cards/lev1/" + fileName).toExternalForm());
        } else {
            this.fileName = fileName;
            this.level = lev;
            this.image = new Image(getClass().getResource("/Images/cards/lev2/" + fileName).toExternalForm());
        }
    }

    /**
     * Returns the level of the card.
     *
     * @return the card level
     */
    public int getLevel() {
        return level;
    }
    /**
     * Returns the JavaFX Image object for the card.
     *
     * @return the card image
     */
    public Image getImage() {
        return image;
    }

    /**
     * Returns the file name of the card image.
     *
     * @return the file name
     */
    public String getFileName() {
        return fileName;
    }
}
