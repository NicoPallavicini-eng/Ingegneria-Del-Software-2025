package it.polimi.ingsw.galaxytrucker.View.Trials;

public class ColorPreview {
    public static void main(String[] args) {
        int[] codes = {
                // Main structures
                196, 226, 46, 33, 201, 208,
                // Tiles
                90, 166, 15, 245, 117, 203, 120, 156, 224, 189
        };

        String[] names = {
                // Main structures
                "Red", "Yellow", "Green", "Blue", "Purple", "Orange",
                // Tiles
                "Darker Purple", "Darker Orange", "White", "Grey",
                "Light Blue", "Light Red", "Light Green", "Lime Green", "Light Ochre", "Light Lilac"
        };

        for (int i = 0; i < codes.length; i++) {
            System.out.println("\u001B[38;5;" + codes[i] + "m" + names[i] + " (Code " + codes[i] + ")\u001B[0m");
        }

        System.out.println(AnsiColor.ORANGE.fg() + "This is the trial" + AnsiColor.RESET);
    }
}