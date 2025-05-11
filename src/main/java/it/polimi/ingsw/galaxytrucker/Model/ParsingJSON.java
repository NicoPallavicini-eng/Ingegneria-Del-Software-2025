package it.polimi.ingsw.galaxytrucker.Model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.*;

import java.io.File;
import java.util.*;

public class ParsingJSON {
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Map<String, Object>> allTiles = mapper.readValue(
                new File("C://Users//nicop//Desktop//PROGETTO_INGEGNERIA_SOFTWARE//src//main//java//it//polimi//ingsw//galaxytrucker//View//json//tile.json"),
                new TypeReference<Map<String, Map<String, Object>>>() {});

        List<Object> instantiatedTiles = new ArrayList<>();

        for (Map.Entry<String, Map<String, Object>> entry : allTiles.entrySet()) {
            String key = entry.getKey();
            Map<String, Object> props = entry.getValue();
            Object tile;

            if (key.startsWith("structural")) {
                tile = mapper.convertValue(props, Tile.class);
            } else if (key.startsWith("singleCannon") || key.startsWith("doubleCannon")) {
                tile = mapper.convertValue(props, CannonTile.class);
            } else if (key.startsWith("shield")) {
                tile = mapper.convertValue(props, ShieldTile.class);
            } else if (key.startsWith("centralCabin") || key.startsWith("cabin")) {
                tile = mapper.convertValue(props, CabinTile.class);
            } else if (key.startsWith("orangeBioadaptor") || key.startsWith("purpleBioadaptor")) {
                tile = mapper.convertValue(props, BioadaptorTile.class);
            } else if (key.startsWith("normalStorage") || key.startsWith("redStorage")) {
                tile = mapper.convertValue(props, CargoTile.class);
            } else if (key.startsWith("battery") || key.startsWith("batteryTriple")) {
                tile = mapper.convertValue(props, BatteryTile.class);
            } else if (key.startsWith("engine") || key.startsWith("doubleEngine")) {
                tile = mapper.convertValue(props, EngineTile.class);
            } else {
                System.out.println("Tile not recognized: " + key);
                continue;
            }

            instantiatedTiles.add(tile);
        }

        System.out.println("Tiles loaded: " + instantiatedTiles.size());
    }
}
