package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Meteor;
import it.polimi.ingsw.galaxytrucker.Model.Cards.MeteorsCard;
import it.polimi.ingsw.galaxytrucker.Model.Direction;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.ActivateCannonsEvent;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.ActivateShieldEvent;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.EventHandler;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.CannonTile;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.ShieldOrientation;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.ShieldTile;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.TilesVisitor.CannonTileVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.TilesVisitor.ShieldTileVisitor;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MeteorsState extends TravellingState{
    private MeteorsCard currentCard;
    private ArrayList<Player> handledPlayers;
    private List<Meteor> meteors;
    private Meteor currentMeteor;
    private int currentMeteorDiceRoll;

    public MeteorsState(Game game, MeteorsCard card) {
        super(game, card);
    }

    public void init(){
        handledPlayers = new ArrayList<>();
        meteors = currentCard.getMeteorsList();
        currentMeteor = meteors.get(0);
        meteors.remove(0);
        currentMeteorDiceRoll = currentMeteor.rollTwoDice();
    }

    private boolean shieldDefends(ShieldOrientation shieldOrientation, Direction direction) {
        switch (shieldOrientation) {
            case NORTHEAST -> {
                return direction == Direction.NORTH || direction == Direction.EAST;
            }
            case NORTHWEST -> {
                return direction == Direction.NORTH || direction == Direction.WEST;
            }
            case SOUTHEAST -> {
                return direction == Direction.SOUTH || direction == Direction.EAST;
            }
            case SOUTHWEST -> {
                return direction == Direction.SOUTH || direction == Direction.WEST;
            }
        }
        return false;
    }

    public void handleEvent(ActivateShieldEvent event){
        if(handledPlayers.contains(event.player())){
            throw new IllegalEventException("You are already defended");
        }
        if(currentMeteor.bigMeteor()){
            throw new IllegalEventException("You can't defend yourself from a big meteor with a shield");
        }
        Optional<Tile> tile = event.player().getShip().getTileOnFloorPlan(event.shieldRow(), event.shieldCol());
        ShieldTileVisitor stv = new ShieldTileVisitor();
        tile.ifPresent(t -> t.accept(stv));
        if(stv.getList().isEmpty() || !defends(stv.getList().getFirst().getOrientation(), currentMeteor.direction())){
            throw new IllegalEventException("You didn't select a shield able to defent you");
        }
        else{
            EventHandler.handleEvent(event);
            handledPlayers.add(event.player());
            if(handledPlayers.containsAll(game.getListOfPlayers())){
                nextMeteor();
            }
        }
    }

    public void handleEvent(ActivateCannonsEvent event){
        if(handledPlayers.contains(event.player())){
            throw new IllegalEventException("You are already defended");
        }
        if(!currentMeteor.bigMeteor()){
            throw new IllegalEventException("You can't defend yourself from a small meteor with a cannon");
        }
        Optional<Tile> tile = event.player().getShip().getTileOnFloorPlan(event.cannons().getFirst().getFirst(), event.cannons().getFirst().get(1));
        CannonTileVisitor ctv = new CannonTileVisitor();
        tile.ifPresent(t -> t.accept(ctv));
        if(ctv.getList().isEmpty()){
            throw new IllegalEventException("You didn't select a cannon");
        }

        CannonTile cannon  = ctv.getList().getFirst();
        if(currentMeteor.direction() != cannon.getDirection()) {//nb
            throw new IllegalEventException("The cannon is not oriented towards the incoming meteor");
        } else if((currentMeteor.direction() == Direction.NORTH && currentMeteorDiceRoll != event.cannons().get(0).get(1)
        || (currentMeteor.direction() == Direction.SOUTH && currentMeteorDiceRoll < event.cannons().get(0).get(1) - 1 || currentMeteorDiceRoll > event.cannons().get(0).get(1) + 1)
        || (currentMeteor.direction() == Direction.EAST

                )){

        }


    }

    private void checkCannon(int row, int col)

    private void nextMeteor(){
        meteors.remove(0);
        if(meteors.isEmpty()){
            next();
        }
        currentMeteor = meteors.get(0);
    }
}
