package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.*;

import java.io.Serializable;

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
        EventHandler.handleEvent(event, game);
        //todo check if we have to do checks
    }

    public void handleEvent(DisconnectEvent event,Game game) throws IllegalEventException {
        EventHandler.handleEvent(event, game);
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
}
