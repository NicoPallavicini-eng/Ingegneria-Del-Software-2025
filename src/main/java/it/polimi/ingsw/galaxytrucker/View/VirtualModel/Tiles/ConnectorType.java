package it.polimi.ingsw.galaxytrucker.View.VirtualModel.Tiles;

public enum ConnectorType {
    NONE,           //
    SINGLE,         //      vertical: |     horizontal: -
    DOUBLE,         //      vertical: ||    horizontal: =
    UNIVERSAL,      //      vertical: |||   horizontal: ≡
    CANNON_SINGLE,  //      N:   ↑       S:   ↓      E:  →     W:  ←
    CANNON_DOUBLE,  //      N:  ↑ ↑      S:  ↓ ↓     E:  ⇉     W:  ⇇
    ENGINE_SINGLE,  //      N:   V       S:   Λ      E:  <     W:  >
    ENGINE_DOUBLE   //      N:  V V      S:  Λ Λ     E:  ≤     W:  ≥
}
