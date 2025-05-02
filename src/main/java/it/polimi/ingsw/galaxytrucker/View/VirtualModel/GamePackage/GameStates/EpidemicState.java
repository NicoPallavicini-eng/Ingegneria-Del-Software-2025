package it.polimi.ingsw.galaxytrucker.View.VirtualModel.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.Cards.EpidemicCard;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.Tiles.CabinTile;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.Tiles.Tile;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.galaxytrucker.Model.Tiles.CabinInhabitants.*;


public class EpidemicState extends TravellingState {

    private ArrayList<Thread> threads;

    public EpidemicState(Game game, EpidemicCard card) {
        super(game, card);
    }

    public void init(){
        threads = new ArrayList<>();
        process();
    }

    public void process(){
        for(Player player : game.getListOfPlayers()){
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

}
