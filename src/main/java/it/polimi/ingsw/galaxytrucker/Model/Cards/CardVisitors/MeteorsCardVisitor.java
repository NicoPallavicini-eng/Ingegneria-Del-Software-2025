package it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors;
import it.polimi.ingsw.galaxytrucker.Model.Cards.*;
import it.polimi.ingsw.galaxytrucker.Model.Game.*;
import it.polimi.ingsw.galaxytrucker.Model.*;

public class MeteorsCardVisitor extends CardVisitor {

    public void visitMeteorsCard() {}

    public void handleMeteorsCard(MeteorsCard meteorsCard, Player player) {
        meteorsCard.process();
    }
}
