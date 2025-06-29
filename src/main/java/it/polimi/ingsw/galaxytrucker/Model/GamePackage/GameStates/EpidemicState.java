package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.EpidemicCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.CabinTile;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.galaxytrucker.Model.Tiles.CabinInhabitants.*;

// all is done indipendently

/**
 * Epidemic State eliminates from each cabin an Inhabitant,if it's connected to other Cabins
 */
public class EpidemicState extends TravellingState implements Serializable {
    private EpidemicCard currentCard;
    private ArrayList<Thread> threads;


    /**
     * @param game
     * @param card
     */
    public EpidemicState(Game game, EpidemicCard card) {
        super(game, card);
        currentCard = card;
    }

    /**
     * This function initialize Epidemic State
     */
    public void init(){
        threads = new ArrayList<>();
        process();
        game.notifyObservers(game, "epidemic");
    }

    /**
     * This function processes the epidemic state by checking each player's ship for inhabited cabins.
     * It identifies cabins that are connected to other inhabited cabins and updates their inhabitants accordingly.
     * It runs in parallel for each player to ensure efficient processing of the epidemic state.
     */
    public void process(){
        for(Player player : game.getListOfActivePlayers()){
            Thread t = new Thread(() -> {
                List <CabinTile> InhabitedCabins = player.getShip().getListOfCabin().stream()
                        .filter(c -> c.getInhabitants() != NONE).toList();
                List <CabinTile> affected = new ArrayList<>();

                for(CabinTile cabin : InhabitedCabins) {
                    List <Tile> adjacentTiles = player.getShip().getAdiacentTiles(cabin);
                    for(Tile adjacent : adjacentTiles) {
                        if(InhabitedCabins.contains(adjacent)) {
                            affected.add(cabin);
                            break;
                        }
                    }
                }

                for(CabinTile cabin : affected) {
                    updateInhabitants(cabin);
                }

            });
            t.start();
            threads.add(t);
        }

        for(Thread t : threads){
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        next();
    }

    /**
     * Helper method to update inhabitants
     * @param cabin
     */
    private void updateInhabitants(CabinTile cabin) {
        if (cabin.getInhabitants() == ONE) {
            cabin.updateInhabitants(NONE);
        } else if (cabin.getInhabitants() == TWO) {
            cabin.updateInhabitants(ONE);
        } else if (cabin.getInhabitants() == ALIEN) {
            cabin.updateInhabitants(NONE);
        }
    }

}
