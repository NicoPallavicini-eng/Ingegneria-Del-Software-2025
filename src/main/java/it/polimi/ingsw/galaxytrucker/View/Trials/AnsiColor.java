package it.polimi.ingsw.galaxytrucker.View.Trials;

public enum AnsiColor {
    // Main Structures
    RED(196),
    YELLOW(226),
    GREEN(46),
    BLUE(33),
    PURPLE(201),
    ORANGE(208),

    // Tiles
    CANNON_COLOR(201),          // PURPLE
    DOUBLE_CANNON_COLOR(90),    // DARK PURPLE
    ENGINE_COLOR(208),          // ORANGE
    DOUBLE_ENGINE_COLOR(166),   // DARK ORANGE
    CABIN_COLOR(15),         // WHITE
    STRUCTURAL_COLOR(245),      // GREY
    REGULAR_CARGO_COLOR(117), // LIGHT BLUE
    RED_CARGO_COLOR(203),     // LIGHT RED
    BATTERY_COLOR(120),         // LIGHT GREEN
    SHIELD_COLOR(156),          // LIGHT LIME

    CREW_COLOR(224),            // LIGHT OCHRE
    CARGO_COLOR(189);           // LIGHT LILAC

    private final int code;

    AnsiColor(int code) {
        this.code = code;
    }

    public String fg() {
        return "\u001B[38;5;" + code + "m";
    }

    public String bg() {
        return "\u001B[48;5;" + code + "m";
    }

    public static final String RESET = "\u001B[0m";
}