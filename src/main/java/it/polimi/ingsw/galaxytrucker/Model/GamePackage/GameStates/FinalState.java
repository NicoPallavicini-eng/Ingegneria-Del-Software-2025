package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//calculates the rewards

/**
 * This is Final State Class.When Deck is Empty, the Game arrive to Final State
 */
public class FinalState extends GameState implements Serializable {
    private List<Ship> activeShips = new ArrayList<>();
    private List<Ship> gaveUpShips = new ArrayList<>();
    private List<Ship> ships = new  ArrayList<>();


    /**
     * Constructor of Final State
     * @param game
     */
    public FinalState(Game game) {
        this.game = game;
    }

    /**
     * This function return game
     * @return Game
     */
    public Game getGame() {
        return game;
    }

    /**
     * This function sets the next Game Set to null
     */
    @Override
    public void next() {
        getGame().setGameState(null);
    }


    /**
     * This function initialize Final State
     */
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

    /**
     * This function processes the Final State rewarding
     */
    private void process() {
        computeFinishOrderReward();
        computeBLSReward();
        computeSaleOfGoods();
        computeLosses();
        game.getListOfPlayers().sort( (p1, p2) -> p2.getShip().getCredits() - p1.getShip().getCredits());
        game.notifyObservers(game, "final");
    }

    /**
     * This function assign credits to player in order of their positions on Leaderboard
     */
    private void computeFinishOrderReward() {
        int i=0;
        for(Ship ship : activeShips){
            if(ship.getTravelDays()!=null){
                ship.setCredits(ship.getCredits()+8-2*i);
                i++;
            }
        }
    }

    /**
     * This function assign credits to the Player with Less Exposes Connectors
     */
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

    /**
     * This function compute the value of Goods for each Ship,and assign credits
     */
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

    /**
     * This function computes the number of pieces lost during the Game,and reduce credit proportionally to Lost Tiles of each Ship
     */
    private void computeLosses() {
        if(!ships.isEmpty()){
            ships.stream().forEach(s -> s.setCredits(s.getCredits() - s.getLostTiles()));
        }
    }


}
