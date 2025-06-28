package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//calculates the rewards

public class FinalState extends GameState implements Serializable {
    private final Game game;
    private List<Ship> ships = new ArrayList<>();
    private List<Ship> gaveUpShips = new ArrayList<>();



    public FinalState(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    @Override
    public void next() {
        getGame().setGameState(null);
    }


    public void init(){
        ships = game.getListOfActivePlayers().stream().map(Player::getShip).toList();
        process();
    }

    private void process() {
        computeFinishOrderReward();
        computeBLSReward();
        computeSaleOfGoods();
        computeLosses();
        game.getListOfPlayers().sort( (p1, p2) -> p2.getShip().getCredits() - p1.getShip().getCredits());
        game.notifyObservers(game, "final");
    }

    private void computeFinishOrderReward() {
        int i=0;
        for(Ship ship : ships){
            if(ship.getTravelDays()!=null){
                ship.setCredits(ship.getCredits()+8-2*i);
                i++;
            }
        }
    }

    private void computeBLSReward() {
        if(!ships.isEmpty()){
            int min = ships.stream().
                    map(Ship::getExposedConnectors).
                    min(Integer::compareTo).get();

            ships.stream().
                    filter(s -> s.getExposedConnectors() == min).forEach(s -> {
                        s.setCredits(s.getCredits()+4);
                    });
        }
    }

    private void computeSaleOfGoods() {
        if(!ships.isEmpty()){
            ships.stream().
                    forEach(s -> s.setCredits(s.getCredits() +
                            s.getListOfCargo().stream().
                                    flatMapToInt(c -> c.getTileContent().stream().
                                            mapToInt(Integer::intValue)).
                                    sum())
                    );
            gaveUpShips.stream().
                    forEach(s -> s.setCredits(s.getCredits() +
                            (s.getListOfCargo().stream().
                                    flatMapToInt(c -> c.getTileContent().stream().
                                            mapToInt(Integer::intValue)).
                                    sum())/2
                    ));
        }
    }

    private void computeLosses() {
        if(!ships.isEmpty()){
            ships.stream().forEach(s -> s.setCredits(s.getCredits() - s.getLostTiles()));
        }
    }


}
