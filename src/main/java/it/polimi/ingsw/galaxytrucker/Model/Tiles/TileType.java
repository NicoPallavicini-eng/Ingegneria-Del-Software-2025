package it.polimi.ingsw.galaxytrucker.Model.Tiles;

import java.io.Serializable;

public enum TileType implements Serializable {
    CANNON,         // C +
    ENGINE,         // E ¤
    CAPSULE,        // C main capsule: ⌂    regular: ⚲
    STORAGE,        // S red: ■             regular: □
    BIOADAPTOR,     // B ⚘
    BATTERY,        // B §
    SHIELD          // S #
    // connector tile: " " content of tile is null
}
