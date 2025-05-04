package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGameEvents;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGame;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualPlayerShip.VirtualShip;

public class VirtualEventHandler {

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

    public static void handleEvent(VirtualDoneEvent event){
        // observer to EventHandler
    }
    public static void handleEvent(VirtualNoChoiceEvent event){
        // observer to EventHandler
    }

    public static void handleEvent(VirtualConnectEvent event) {
        // observer to EventHandler
    }

    public static void handleEvent(VirtualDisconnectEvent event) {
        // observer to EventHandler
    }

    public static void handleEvent(VirtualSetNumberOfPlayersEvent event) {
        // observer to EventHandler
    }

    public static void handleEvent(VirtualPickUpTileEvent event) {
        // observer to EventHandler
    }

    public static void handleEvent(VirtualRotateTileEvent event) {
        // observer to EventHandler
    }

    public static void handleEvent(VirtualPutDownTileEvent event) {
        // observer to EventHandler
    }

    public static void handleEvent(VirtualPlaceOrangeAlienEvent event) {
        // observer to EventHandler
    }

    public static void handleEvent(VirtualPlacePurpleAlienEvent event) {
        // observer to EventHandler
    }

    public static void handleEvent(VirtualPlaceTileEvent event) {
        // observer to EventHandler
    }

    public static void handleEvent(VirtualReserveTileEvent event) {
        // observer to EventHandler
    }

    public static void handleEvent(VirtualRemoveTileEvent event) {
        // observer to EventHandler
    }

    public static void handleEvent(VirtualFlipHourglassEvent event) {
        // observer to EventHandler
    }

    public static void handleEvent(VirtualSetPositionEvent event) {
        // observer to EventHandler
    }

    public static void handleEvent(VirtualPickUpFromShipEvent event) {
        // observer to EventHandler
    }

    public static void handleEvent(VirtualPickUpReservedTileEvent event) {
        // observer to EventHandler
    }

    public static void handleEvent(VirtualViewDeckEvent event) {
        // observer to EventHandler
    }

    public static void handleEvent(VirtualChoosePlanetEvent event) {
        // observer to EventHandler warn exception
    }

    public static void handleEvent(VirtualActivateEnginesEvent event) {
        // observer to EventHandler warn exception
    }

    public static void handleEvent(VirtualActivateCannonsEvent event) {
        // observer to EventHandler warn exception
    }

    public static void handleEvent(VirtualActivateShieldEvent event) {
        // observer to EventHandler warn exception
    }

    public static void handleEvent(VirtualRemoveCargoEvent event) {
        // observer to EventHandler warn exception
    }

    public static void handleEvent(VirtualAddCargoEvent event) {
        // observer to EventHandler warn exception
    }

    public static void handleEvent(VirtualSwitchCargoEvent event) {
        // observer to EventHandler
    }

    public static void handleEvent(VirtualEjectPeopleEvent event) {
        // observer to EventHandler warn exception
    }

    public static void handleEvent(VirtualGiveUpEvent event) {
        // observer to EventHandler
    }

    public static void moveForward(VirtualShip ship, int days, VirtualGame game){
        // observer to EventHandler
    }

    public static void moveBackward(VirtualShip ship, int days, VirtualGame game){
        // observer to EventHandler
    }
}

