package it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors;
import it.polimi.ingsw.galaxytrucker.Model.Cards.*;
import it.polimi.ingsw.galaxytrucker.Model.Game.*;
import it.polimi.ingsw.galaxytrucker.Model.*;

public class EpidemicCardVisitor extends CardVisitor {

    public void visitEpidemicCard() {}

    public void handleEpidemicCard(EpidemicCard epidemicCard, Player player) {
        epidemicCard.process();
    }
}
