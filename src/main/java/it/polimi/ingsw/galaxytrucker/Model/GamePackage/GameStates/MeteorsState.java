package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Meteor;
import it.polimi.ingsw.galaxytrucker.Model.Cards.MeteorsCard;
import it.polimi.ingsw.galaxytrucker.Model.Direction;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.*;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.CannonTile;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.ShieldOrientation;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.TilesVisitor.CannonTileVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.TilesVisitor.ShieldTileVisitor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MeteorsState extends TravellingState implements Serializable {
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
        if(stv.getList().isEmpty() || !shieldDefends(stv.getList().getFirst().getOrientation(), currentMeteor.direction())){
            throw new IllegalEventException("You didn't select a shield able to defent you");
        }
        else{
            EventHandler.handleEvent(event);
            handledPlayers.add(event.player());
            checkNext();
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
        }
        Direction direction = currentMeteor.direction();
        int cannonRow = event.cannons().get(0).get(1);
        int cannonCol = event.cannons().get(0).get(0);
        if(!( (direction == Direction.NORTH && currentMeteorDiceRoll == cannonCol)
        || (direction == Direction.SOUTH && (currentMeteorDiceRoll >= cannonCol - 1 && currentMeteorDiceRoll <= cannonCol + 1))
        || (direction == Direction.EAST && (currentMeteorDiceRoll >= cannonRow - 1 && currentMeteorDiceRoll <= cannonRow + 1))
        || (direction == Direction.WEST && (currentMeteorDiceRoll >= cannonRow - 1 && currentMeteorDiceRoll <= cannonRow + 1)) )){
            throw new IllegalEventException("The cannon's position makes it impossible to fire the incoming meteor");
        }
        else{
            EventHandler.handleEvent(event);
            handledPlayers.add(event.player());
            checkNext();
        }
    }

    public void handleEvent(NoChoiceEvent event){
        if(handledPlayers.contains(event.player())){
            throw new IllegalEventException("You are already defended");
        }
        else{
            handledPlayers.add(event.player());
            if(handledPlayers.containsAll(game.getListOfPlayers())){
                checkNext();
            }
        }
    }

    private void checkNext() {
        if (handledPlayers.containsAll(game.getListOfPlayers())) {
            for (Player player : game.getListOfPlayers()) {
                currentMeteor.getHit(player.getShip());
            }
            if (meteors.isEmpty()) {
                next();
            }
            currentMeteor = meteors.get(0);
            meteors.remove(0);
            currentMeteorDiceRoll = currentMeteor.rollTwoDice();
            for (Player player : game.getListOfPlayers()) {
                player.getShip().disactivateEverything();
            }
        }
    }
}
