package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components;

import javafx.scene.image.Image;

public enum CardImage {
    openSpaceCard1("GT-cards_I_IT_4.jpg", 1),
    openSpaceCard2("GT-cards_I_IT_5.jpg", 1),
    openSpaceCard3("GT-cards_I_IT_6.jpg", 1),
    openSpaceCard4("GT-cards_II_IT_6.jpg", 2),
    openSpaceCard5("GT-cards_II_IT_6.jpg", 2),
    openSpaceCard6("GT-cards_II_IT_6.jpg", 2),
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
    stardustCard1("GT-cards_I_IT_3.jpg", 1),
    stardustCard2("GT-cards_II_IT_3.jpg", 2),
    combatZoneCardL("GT-cards_I_IT_15.jpg", 1),
    combatZoneCardNotL("GT-cards_II_IT_15.jpg", 2),
    epidemicCard1("GT-cards_II_IT_4.jpg", 2);

    private final String fileName;
    private final Image image;

    CardImage(String fileName, int lev) {
        if (lev == 1) {
            this.fileName = fileName;
            this.image = new Image(getClass().getResource("/Images/cards/lev1/" + fileName).toExternalForm());
        } else {
            this.fileName = fileName;
            this.image = new Image(getClass().getResource("/Images/cards/lev2/" + fileName).toExternalForm());
        }
    }

    public Image getImage() {
        return image;
    }

    public String getFileName() {
        return fileName;
    }

    public static TileImage fromJsonName(String name) { // TODO capi
        try {
            return TileImage.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.err.println("Unknown tile name in JSON: " + name);
            return null;
        }
    }
}
