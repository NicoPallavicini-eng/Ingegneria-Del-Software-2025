package it.polimi.ingsw.galaxytrucker.View.VirtualModel.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.Cards.Meteor;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.Cards.MeteorsCard;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.PlayerShip.Player;

import java.util.ArrayList;
import java.util.List;

public class MeteorsState extends TravellingState {
    private MeteorsCard currentCard;
    private ArrayList<Player> handledPlayers;
    private List<Meteor> meteors;
    private Meteor currentMeteor;

    public MeteorsState(Game game, MeteorsCard card) {
        super(game, card);
    }

    public void init(){
        handledPlayers = new ArrayList<>();
        meteors = currentCard.getMeteorsList();
        currentMeteor = meteors.get(0);
        meteors.remove(0);
    }

    //todo capire come vogliamo gestire i bro che attivano scudi e cannoni
    public void handleInput(){
    }
}
