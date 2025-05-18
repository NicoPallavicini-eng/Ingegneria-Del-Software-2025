package it.polimi.ingsw.galaxytrucker.Model.Tiles;

import java.io.Serializable;

public enum TileType implements Serializable {
    CANNON,         // C +                                      PURPLE/SLIGHTLY DARKER PURPLE
    ENGINE,         // E ¤                                      ORANGE/SLIGHTLY DARKER ORANGE
    CAPSULE,        // C main capsule: ⌂    regular: ⚲          LIGHT GREY
    STORAGE,        // S red: ■             regular: □          LIGHT BLUE/LIGHT RED
    BIOADAPTOR,     // B ✶                                      PURPLE/ORANGE
    BATTERY,        // B §                                      LIGHT GREEN
    SHIELD          // S #                                      VERY LIGHT GREEN
    // structural tile: " $ " content of tile is null           GREY
    // empty : [ ]
    // upside down : x
}
