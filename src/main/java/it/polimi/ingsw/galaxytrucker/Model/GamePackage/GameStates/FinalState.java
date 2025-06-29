package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//calculates the rewards

public class FinalState extends GameState implements Serializable {
    private List<Ship> activeShips = new ArrayList<>();
    private List<Ship> gaveUpShips = new ArrayList<>();
    private List<Ship> ships = new  ArrayList<>();
    private final Player disconnectionWinner;


    public FinalState(Game game) {
        this.game = game;
        disconnectionWinner = null;
    }

    public FinalState(Game game, Player disconnectionWinner) {
        this.game = game;
        this.disconnectionWinner = disconnectionWinner;
    }

    public Game getGame() {
        return game;
    }

    @Override
    public void next() {
        getGame().setGameState(null);
    }


    public void init(){
        for(Player p : game.getListOfActivePlayers()){
            Ship s = p.getShip();
            ships.add(s);
            if(s.getTravelDays() == null){
                gaveUpShips.add(s);
            }
            else{
                activeShips.add(s);
            }
        }
        activeShips.sort( (s1, s2) -> s2.getTravelDays()-s1.getTravelDays());
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
        for(Ship ship : activeShips){
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
        if (!activeShips.isEmpty()) {
            activeShips.stream().
                    forEach(s -> s.setCredits(s.getCredits() +
                            s.getListOfCargo().stream().
                                    flatMapToInt(c -> c.getTileContent().stream().
                                            mapToInt(Integer::intValue)).
                                    sum())
                    );
        }
        if (!gaveUpShips.isEmpty()) {
            gaveUpShips.stream().
                    forEach(s -> s.setCredits(s.getCredits() +
                            (s.getListOfCargo().stream().
                                    flatMapToInt(c -> c.getTileContent().stream().
                                            mapToInt(Integer::intValue)).
                                    sum()) / 2
                    ));
        }
    }

    private void computeLosses() {
        if(!ships.isEmpty()){
            ships.stream().forEach(s -> s.setCredits(s.getCredits() - s.getLostTiles()));
        }
    }

    public Player getDisconnectionWinner() {
        return disconnectionWinner;
    }
}
