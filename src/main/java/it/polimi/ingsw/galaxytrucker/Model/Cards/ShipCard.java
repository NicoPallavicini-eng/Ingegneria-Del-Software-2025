package it.polimi.ingsw.galaxytrucker.Model.Cards;

public class ShipCard extends Card {
    private final int crewNumberLost;
    private final int credits;
    private final int daysToLose;

    public ShipCard(boolean levelTwo, boolean used, int crewNumberLost, int credits, int daysToLose) {
        super(levelTwo, used);
        this.crewNumberLost = crewNumberLost;
        this.credits = credits;
        this.daysToLose = daysToLose;
    }

    public int getCredits() {
        return credits;
    }

    public int getDaysToLose() {
        return daysToLose;
    }

    public int getCrewNumberLost() {
        return crewNumberLost;
    }

 /*   public void process(Player player, SequentialTravellingState state) {
        Ship ship = player.getShip();

        if ((ship.getNumberOfCrewMembers() >= crewNumberLost) && player.getEngages()) {
            state.setAccomplished(true);

            ship.setCredits(ship.getCredits() + credits);
            ship.setCrewMembers(ship.getNumberOfCrewMembers() - crewNumberLost);
            ship.setTravelDays(ship.getTravelDays() - daysToLose);
        }
    }

  */
}
