package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualCards;

import java.util.List;

public class VirtualMeteorsCard extends VirtualCard {
    private final List <VirtualMeteor> meteors;

    public VirtualMeteorsCard(boolean levelTwo, boolean used, List <VirtualMeteor> meteors) {
        super(levelTwo, used);
        this.meteors = meteors;
    }

    public List <VirtualMeteor> getMeteorsList() {
        return meteors;
    }
}
