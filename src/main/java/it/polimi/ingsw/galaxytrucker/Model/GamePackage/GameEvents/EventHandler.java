package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

public class EventHandler {

    /*
    GLOBAL:
 - /help
 - /viewships
 - /viewleaderboard

BEFORE GAME:
 - /connect
 - /disconnect
 - /setnumberofplayers
 - /setcolor *

BUILDING:
 - /pickuptile
 - /rotatetile
 - /putdowntile
 - /placetile
 - /reservetile
 - /fliphourglass
 - /setposition
 - /pickupfromship
 - /choosedreservedtile

TRAVELLING:
 - /activatenengines
 - /activatecannons
 - /activateshields
 - /removecargo
 - /addcargo
 - /switchcargo
 - /ejectpeople
 - /giveup
 - /viewinventory


     */

    public void handleEvent(ConnectEvent event) {}
    public void handleEvent(DisconnectEvent event) {}
    public void handleEvent(SetNumberOfPlayersEvent event) {}
    public void handleEvent(PickUpTileEvent event) {}
    public void handleEvent(RotateTileEvent event) {}
    public void handleEvent(PutDownTileEvent event) {}
    public void handleEvent(PlaceTileEvent event) {}
    public void handleEvent(ReserveTileEvent event) {}
    public void handleEvent(FlipHourglassEvent event) {}
    public void handleEvent(SetPositionEvent event) {}
    public void handleEvent(PickUpFromShipEvent event) {}
    public void handleEvent(PickUpReservedTileEvent event) {}

    public void handleEvent(ActivateEnginesEvent event) {}
    public void handleEvent(ActivateCannonsEvent event) {}
    public void handleEvent(ActivateShieldEvent event) {}
    public void handleEvent(RemoveCargoEvent event) {}
    public void handleEvent(AddCargoEvent event) {}
    public void handleEvent(SwitchCargoEvent event) {}
    public void handleEvent(EjectPeopleEvent event) {}
    public void handleEvent(GiveUpEvent event) {}


}
