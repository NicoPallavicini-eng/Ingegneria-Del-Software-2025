package it.polimi.ingsw.galaxytrucker.Model.Tiles;

public enum ConnectorType {
    NONE,       //      x
    SINGLE,     //      vertical: |   horizontal: -
    DOUBLE,     //      vertical: ‖   horizontal: =
    UNIVERSAL,  //      vertical: ⦀   horizontal: ≡
    CANNON,     //      up:  ↑      down:   ↓     right:  →     left:  ←
                //      up2: ⇈      down2: ⇊     right2: ⇉     left2: ⇇
    ENGINE      //      up:  v      down:   ^     right:  <     left:  >
                //      up2: w      down2: ^^     right2: ≤     left2: ≥
}
