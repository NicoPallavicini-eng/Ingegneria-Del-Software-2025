package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGameStates;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.Game;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualPlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualPlayerShip.Ship;

import java.util.ArrayList;
import java.util.List;

public class FinalState extends GameState {
    private final Game game;
    private List<Ship> ships = new ArrayList<>();


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
        ships = game.getListOfPlayers().stream().map(Player::getShip).toList();
        process();
    }

    private void process() {
        computeFinishOrderReward();
        computeBLSReward();
        computeSaleOfGoods();
        computeLosses();
        next();
    }

    private void computeFinishOrderReward() {
        Ship s = ships.getFirst();
        for(int i=0; s!=null; s = ships.get(++i)){
            s.setCredits(s.getCredits()+8-2*i);
        }
    }

    private void computeBLSReward() {
        int min = ships.stream().
                map(Ship::getExposedConnectors).
                min(Integer::compareTo).get();

        ships.stream().
                filter(s -> s.getExposedConnectors() == min).forEach(s -> {
                    s.setCredits(s.getCredits()+4);
                });
    }

    private void computeSaleOfGoods() {
        ships.stream().
                forEach(s -> s.setCredits(s.getCredits() +
                        s.getListOfCargo().stream().
                                flatMapToInt(c -> c.getTileContent().stream().
                                        mapToInt(Integer::intValue)).
                                sum())
                        );
    }

    private void computeLosses() {
        ships.stream().forEach(s -> s.setCredits(s.getCredits() - s.getLostTiles()));
    }
}
