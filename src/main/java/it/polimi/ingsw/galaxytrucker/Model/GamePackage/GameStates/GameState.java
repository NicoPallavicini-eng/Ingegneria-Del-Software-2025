package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.*;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;
import java.util.Optional;

/*superclass of all the gamestates,
it defines the handleEvent methods that are overloaded.
Usually these methods are overriden in the gamestates in which they are available,
unless they are always available, like connect and disconnect
 */

public abstract class GameState implements Serializable {
    protected Game game;

    public void handleEvent(GameEvent gameEvent) throws IllegalEventException {
         throw new IllegalEventException("The player used a command not available in this phase of the game.");
    }

    public void next(){};
    public void init(){};

    public void handleEvent(ConnectEvent event, Game game) throws IllegalEventException {
        Optional<Player> playerOptional = game.getListOfPlayers().stream().filter(p -> p.getNickname().equals(event.nickname())).findAny();
        if(playerOptional.isPresent()) {
            Player player = playerOptional.get();
            if(player.getOnlineStatus()){
                throw new IllegalEventException("The player is already online");
            }
            else{
                player.setOnlineStatus(true);
            }
        }
        else{
            throw new IllegalEventException("no player is present with this nickname");
        }
    }

    public void handleEvent(DisconnectEvent event,Game game) throws IllegalEventException {
        EventHandler.handleEvent(event, game);
        disconnectionConsequences(event.player());
    }

    public void handleEvent(PickUpTileEvent event) throws IllegalEventException {
        throw new IllegalEventException("The player used a command not available in this phase of the game.");
    }

    public void handleEvent(RotateTileEvent event) throws IllegalEventException {
        throw new IllegalEventException("The player used a command not available in this phase of the game.");
    }

    public void handleEvent(PutDownTileEvent event) throws IllegalEventException {
        throw new IllegalEventException("The player used a command not available in this phase of the game.");
    }

    public void handleEvent(PlaceOrangeAlienEvent event) throws IllegalEventException {
        throw new IllegalEventException("The player used a command not available in this phase of the game.");
    }

    public void handleEvent(PlacePurpleAlienEvent event) throws IllegalEventException {
        throw new IllegalEventException("The player used a command not available in this phase of the game.");
    }

    public void handleEvent(PlaceTileEvent event) throws IllegalEventException {
        throw new IllegalEventException("The player used a command not available in this phase of the game.");
    }

    public void handleEvent(ReserveTileEvent event) throws IllegalEventException {
        throw new IllegalEventException("The player used a command not available in this phase of the game.");
    }

    public void handleEvent(FlipHourglassEvent event, Game game) throws IllegalEventException {
        throw new IllegalEventException("The player used a command not available in this phase of the game.");
    }

    public void handleEvent(SetPositionEvent event) throws IllegalEventException {
        throw new IllegalEventException("The player used a command not available in this phase of the game.");
    }

    public void handleEvent(PickUpFromShipEvent event) throws IllegalEventException {
        throw new IllegalEventException("The player used a command not available in this phase of the game.");
    }

    public void handleEvent(PickUpReservedTileEvent event) throws IllegalEventException {
        throw new IllegalEventException("The player used a command not available in this phase of the game.");
    }

    public void handleEvent(ViewDeckEvent event) throws IllegalEventException {
        throw new IllegalEventException("The player used a command not available in this phase of the game.");
    }

    public void handleEvent(ActivateEnginesEvent event) throws IllegalEventException {
        throw new IllegalEventException("The player used a command not available in this phase of the game.");
    }

    public void handleEvent(ActivateCannonsEvent event) throws IllegalEventException {
        throw new IllegalEventException("The player used a command not available in this phase of the game.");
    }

    public void handleEvent(ActivateShieldEvent event) throws IllegalEventException {
        throw new IllegalEventException("The player used a command not available in this phase of the game.");
    }

    public void handleEvent(RemoveCargoEvent event) throws IllegalEventException {
        throw new IllegalEventException("The player used a command not available in this phase of the game.");
    }

    public void handleEvent(AddCargoEvent event) throws IllegalEventException {
        throw new IllegalEventException("The player used a command not available in this phase of the game.");
    }

    public void handleEvent(SwitchCargoEvent event) throws IllegalEventException {
        throw new IllegalEventException("The player used a command not available in this phase of the game.");
    }

    public void handleEvent(EjectPeopleEvent event) throws IllegalEventException {
        throw new IllegalEventException("The player used a command not available in this phase of the game.");
    }

    public void handleEvent(GiveUpEvent event) throws IllegalEventException {
        throw new IllegalEventException("The player used a command not available in this phase of the game.");
    }

    public void handleEvent(ClaimRewardEvent event) throws IllegalEventException {
        throw new IllegalEventException("The player used a command not available in this phase of the game.");
    }
    public void handleEvent(SetNumberOfPlayersEvent event) throws IllegalEventException {
        throw new IllegalEventException("The player used a command not available in this phase of the game.");
    }

    public void handleEvent(FlipHourglassEvent event) throws IllegalEventException {
        throw new IllegalEventException("The player used a command not available in this phase of the game.");
    }

    public void handleEvent(NoChoiceEvent event) throws IllegalEventException {
        throw new IllegalEventException("The player used a command not available in this phase of the game.");
    }

    public void handleEvent(DoneEvent event) throws IllegalEventException {
        throw new IllegalEventException("The player used a command not available in this phase of the game.");
    }

    public void handleEvent(RemoveBatteriesEvent event) throws IllegalEventException {
        throw new IllegalEventException("The player used a command not available in this phase of the game.");
    }

    public void handleEvent(RemoveTileEvent event) throws IllegalEventException {
        throw new IllegalEventException("The player used a command not available in this phase of the game.");
    }

    public void handleEvent(ChoosePlanetEvent event) throws IllegalEventException {
        throw new IllegalEventException("The player used a command not available in this phase of the game.");
    }

    protected void disconnectionConsequences(Player p){
    }
}
