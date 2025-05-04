package it.polimi.ingsw.galaxytrucker.Model.Cards;

public class EpidemicCard extends Card {
    public EpidemicCard(boolean levelTwo, boolean used) {
        super(levelTwo, used);
    }


/*    public void process(Ship ship) {
        List <CabinTile> cabins = ship.getListOfCabin();
        List <CabinTile> visited = new ArrayList<>();

        // Process each cabin
        for (CabinTile tile : cabins) {
            if (visited.contains(tile)) continue; // Skip already visited cabins

            visited.add(tile);
            List <Tile> adjacentTiles = ship.getAdiacentTiles(tile);
            List <CabinTile> adjacentCabins = new ArrayList<>();

            // Find adjacent cabins
            for (Tile adjacent : adjacentTiles) {
                if (cabins.contains(adjacent)) {
                    adjacentCabins.add((CabinTile) adjacent);
                }
            }

            // Process adjacent cabins
            for (CabinTile adjacentCabin : adjacentCabins) {
                if (visited.contains(adjacentCabin)) continue; // Skip already visited cabins

                visited.add(adjacentCabin);
                updateInhabitants(adjacentCabin);
                updateInhabitants(tile);
            }
        }
    }

    // Helper method to update inhabitants
    private void updateInhabitants(CabinTile cabin) {
        if (cabin.getInhabitants() == ONE) {
            cabin.updateInhabitants(NONE);
        } else if (cabin.getInhabitants() == TWO) {
            cabin.updateInhabitants(ONE);
        } else if (cabin.getInhabitants() == ALIEN) {
            cabin.updateInhabitants(NONE);
        }
    }

 */
}
