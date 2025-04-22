package it.polimi.ingsw.galaxytrucker.Model.Tiles;

public enum ConnectorType {
    NONE,       //      │ (tile border)
    SINGLE,     //      vertical: |     horizontal: -
    DOUBLE,     //      vertical: ||    horizontal: =
    UNIVERSAL,  //      vertical: |||   horizontal: ≡
    CANNON,     //      up:  ↑        down:   ↓       right:  →     left:  ←
                //      up2: ↑ ↑      down2:  ↓ ↓     right2: ⇉     left2: ⇇
    ENGINE      //      up:  V        down:   Λ       right:  <     left:  >
                //      up2: V V      down2:  Λ Λ     right2: ≤     left2: ≥
}               // TODO capire come fare vedere shield in TUI
