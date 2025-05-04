package it.polimi.ingsw.galaxytrucker.Model.Cards;

public class SlaversCard extends Card {
    private final int firepower;
    private final int credits;
    private final int crewLost;
    private final int daysToLose;

    public SlaversCard(boolean levelTwo, boolean used, int firepower, int credits, int crewLost, int daysToLose) {
        super(levelTwo, used);
        this.firepower = firepower;
        this.credits = credits;
        this.crewLost = crewLost;
        this.daysToLose = daysToLose;
    }

    public int getFirepower() {
        return firepower;
    }

    public int getNumberOfCredits() {
        return credits;
    }

    public int getNumberOfCrewLost() {
        return crewLost;
    }

    public int getNumberOfDaysToLose() {
        return daysToLose;
    }


 /*   public void process(Player player, SequentialTravellingState state) {
        Ship ship = player.getShip();

        if (ship.getFirepower() < firepower) {
            ship.setCrewMembers(ship.getNumberOfCrewMembers() - crewLost);
        } else if (ship.getFirepower() > firepower) {
            state.setAccomplished(true);

            if (player.getEngages()) {
                ship.setCredits(ship.getCredits() + credits);
                ship.setTravelDays(ship.getTravelDays() - daysToLose);
            }
        }
    }

  */
}
