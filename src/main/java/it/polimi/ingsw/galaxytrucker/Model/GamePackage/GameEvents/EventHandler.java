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

    public static void handleEvent(ConnectEvent event) {}
    public static void handleEvent(DisconnectEvent event) {}
    public static void handleEvent(SetNumberOfPlayersEvent event) {}
    public static void handleEvent(PickUpTileEvent event) {}
    public static void handleEvent(RotateTileEvent event) {}
    public static void handleEvent(PutDownTileEvent event) {}
    public static void handleEvent(PlaceOrangeAlienEvent event) {}
    public static void handleEvent(PlacePurpleAlienEvent event) {}
    public static void handleEvent(PlaceTileEvent event) {}
    public static void handleEvent(ReserveTileEvent event) {}
    public static void handleEvent(RemoveTileEvent event) {}
    public static void handleEvent(FlipHourglassEvent event) {}
    public static void handleEvent(SetPositionEvent event) {}
    public static void handleEvent(PickUpFromShipEvent event) {}
    public static void handleEvent(PickUpReservedTileEvent event) {}
    public static void handleEvent(ViewDeckEvent event) {}

    public static void handleEvent(ActivateEnginesEvent event) {}
    public static void handleEvent(ActivateCannonsEvent event) {}
    public static void handleEvent(ActivateShieldEvent event) {}
    public static void handleEvent(RemoveCargoEvent event) {}
    public static void handleEvent(AddCargoEvent event) {}
    public static void handleEvent(SwitchCargoEvent event) {}
    public static void handleEvent(EjectPeopleEvent event) {}
    public static void handleEvent(GiveUpEvent event) {}


}
