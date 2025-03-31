package it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors;

public abstract class CardVisitor {
    public CardVisitor() {}

    // handle cards

    public void handleStationCard() {}

    public void handleShipCard() {}

    public void handleOpenSpaceCard() {}

    public void handleStardustCard() {}

    public void handleEpidemicCard() {}

    public void handleCombatZoneCard() {}

    public void handlePlanetsCard() {}

    public void handleSmugglersCard() {}

    public void handleMeteorsCard() {}

    public void handleSlaversCard() {}

    public void handlePiratesCard() {}

    // set next states

    public void setNextStateCombatZoneCard() {}

    public void setNextStateEpidemicCard() {}

    public void setNextStateMeteorsCard() {}

    public void setNextStateOpenSpaceCard() {}

    public void setNextStatePiratesCard() {}

    public void setNextStatePlanetsCard() {}

    public void setNextStateShipCard() {}

    public void setNextStateSlaversCard() {}

    public void setNextStateSmugglersCard() {}

    public void setNextStateStardustCard() {}

    public void setNextStateStationCard() {}
}
