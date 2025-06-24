package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Cannonball;
import it.polimi.ingsw.galaxytrucker.Model.Cards.PiratesCard;
import it.polimi.ingsw.galaxytrucker.Model.Direction;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.*;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.ShieldOrientation;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.TilesVisitor.ShieldTileVisitor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*Players fight pirates according to their travel order,
once pirates have been slain or all player have been defeated
the reckoning phase starts for all defeated players
lastly the player that defeated pirates can loot them if they wish in the claiming phase
*/

//todo view devo veder quale cannonball sta arrivando

public class PiratesState extends TravellingState implements Serializable {
    private PiratesCard currentCard;
    private Player piratesSlayer;
    private List<Player> defeatedPlayers;
    private boolean reckoningPhase = false;
    private boolean claimingPhase = false;
    private boolean shipLegalityPhase = false;
    private Cannonball currentCannonball;
    private List<Player> defendedPlayers;
    private List<Player> playersWithIllegalShips;

    public PiratesState(Game game, PiratesCard card) {
        super(game, card);
        currentCard = card;
    }

    public void init(){
        super.init();
        defeatedPlayers = new ArrayList<>();
        defendedPlayers = new ArrayList<>();
        game.notifyObservers(game, "pirates");
    }

    public void handleEvent(ActivateCannonsEvent event){
        if(!event.player().equals(currentPlayer)){
            throw new IllegalEventException("It is not your turn");
        }
        else{
            EventHandler.handleEvent(event);
            if(currentPlayer.getShip().getFirepower() < currentCard.getFirepower()){
                defeatedPlayers.add(currentPlayer);
                nextPlayer();
            }
            else if(currentPlayer.getShip().getFirepower() == currentCard.getFirepower()){
                nextPlayer();
            }
            else{
                piratesSlayer = currentPlayer;
                currentPlayer = null;
                reckoningPhase = true;
                game.notifyObservers(game, "piratesDefeated");
                consequences();
            }
        }
    }

    @Override
    protected void nextPlayer(){
        super.nextPlayer();
        if(currentPlayer == null){
            reckoningPhase = true;
            consequences();
        }
    }

    public void handleEvent(ClaimRewardEvent event){
        if(!event.player().equals(piratesSlayer)){
            throw new IllegalEventException("You have not slain the pirates");
        }
        else if (!claimingPhase) {
            throw new IllegalEventException("Wait for all defeated players to be attacked by pirates");
        }
        else{
            piratesSlayer.getShip().setCredits(piratesSlayer.getShip().getCredits() + currentCard.getCredits());
            EventHandler.moveBackward(piratesSlayer.getShip(), currentCard.getDaysToLose(), game);
            next();
        }
    }

    public void handleEvent(NoChoiceEvent event){
        if(claimingPhase){
            if(!event.player().equals(piratesSlayer)){
                throw new IllegalEventException("You have not slain the pirates");
            }
            else{
                next();
            }
        }
        else if(reckoningPhase) {
            if(!defeatedPlayers.contains(event.player()) || defendedPlayers.contains(event.player())){
                throw new IllegalEventException("no choice to make");
            }
            else{
                currentCannonball.getHit(event.player().getShip());
                if(event.player().getShip().isShipBroken()){
                    playersWithIllegalShips.add(event.player());
                }
                defendedPlayers.add(event.player());
                if(defendedPlayers.containsAll(defeatedPlayers)){
                    consequences();
                }
            }
        }
        else{
            if(!event.player().equals(currentPlayer)){
                throw new IllegalEventException("It is not your turn");
            }
            else{
                if(currentPlayer.getShip().getFirepower() < currentCard.getFirepower()){
                defeatedPlayers.add(currentPlayer);
                nextPlayer();
                }
                else if(currentPlayer.getShip().getFirepower() == currentCard.getFirepower()){
                    nextPlayer();
                }
                else{
                    piratesSlayer = currentPlayer;
                    currentPlayer = null;
                    consequences();
                }
            }
        }

    }

    private void consequences(){
        if(!playersWithIllegalShips.isEmpty()){
            reckoningPhase = false;
            shipLegalityPhase = true;
            return;
        }
        if(currentCard.getCannonballList().isEmpty() || defeatedPlayers.isEmpty()){
            reckoningPhase = false;
            claimingPhase = true;
            if(piratesSlayer == null){
                next();
            }
        }
        else{
            reckoningPhase = true;
            defendedPlayers.clear();
            currentCannonball = currentCard.getCannonballList().getFirst();
            currentCard.getCannonballList().removeFirst();
            if(currentCannonball.bigCannonball()){
                for(Player player : defeatedPlayers){
                    currentCannonball.getHit(player.getShip());
                    if(!player.getShip().checkFloorPlanConnection()){
                        playersWithIllegalShips.add(player);
                    }
                }
                consequences();
            }
        }
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
        if(reckoningPhase){
            throw new IllegalEventException("Not time for activating shield");
        }
        else if(defeatedPlayers.contains(event.player()) || defendedPlayers.contains(event.player())){
            throw new IllegalEventException("you shall not defend");
        }
        Optional<Tile> tile = event.player().getShip().getTileOnFloorPlan(event.shieldRow(), event.shieldCol());
        ShieldTileVisitor stv = new ShieldTileVisitor();
        tile.ifPresent(t -> t.accept(stv));
        if(stv.getList().isEmpty() || !shieldDefends(stv.getList().getFirst().getOrientation(), currentCannonball.direction())){
            throw new IllegalEventException("You didn't select a shield able to defend you");
        }
        else{
            EventHandler.handleEvent(event);
            defendedPlayers.add(event.player());
            if(defendedPlayers.containsAll(defeatedPlayers)){
                consequences();
            }
        }
    }

    public Player getPiratesSlayer() {
        return piratesSlayer;
    }

    public List<Player> getDefeatedPlayers() {
        return defeatedPlayers;
    }

    public boolean isReckoningPhase() {
        return reckoningPhase;
    }

    public boolean isClaimingPhase() {
        return claimingPhase;
    }

    public List<Player> getDefendedPlayers() {
        return defendedPlayers;
    }

    protected void disconnectionConsequences(Player p){
        defeatedPlayers.remove(p);
        defendedPlayers.remove(p);
        if(piratesSlayer.equals(p)){
            piratesSlayer = null;
        }
        if(reckoningPhase) {
            if(defendedPlayers.containsAll(defeatedPlayers)){
                consequences();
            }
        }
        else if (claimingPhase) {
            if(piratesSlayer == null){
                next();
            }
        }
        else{
            super.disconnectionConsequences(p);
        }
    }

    public void handleEvent(ChooseSubShipEvent event) {
        if(!shipLegalityPhase) {
            throw new IllegalEventException("Not legal to remove in this phase");
        }
        else if(!playersWithIllegalShips.contains(event.player())){
            throw new IllegalEventException("You have already a functioning spaceship");
        }
        else{
            EventHandler.handleEvent(event);
            synchronized (playersWithIllegalShips) {
                playersWithIllegalShips.remove(event.player());
            }
            game.notifyObservers(game, "legalship");
            consequences();
        }
    }

}
