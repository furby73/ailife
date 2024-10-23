package life;

import ai.Net;

import java.util.ArrayList;

public class Predator extends Creature {

    public Predator(double posX, double posY) {

        super(posX, posY);
        maxRangeOfVision = 80;
        maxPopulation = 170;
        rangeOfVision = Math.PI / 3.0;
    }

    public Predator(double posX, double posY, Net network) {
        super(posX, posY);
        this.network = new Net(network);
        maxRangeOfVision = 80;
        maxPopulation = 170;
        rangeOfVision = Math.PI / 6.0;
    }


    public static void giveBirth(Predator a) {
        if (listOfPredators.size() < a.maxPopulation)
            listOfPredators.add(new Predator(a.posX, a.posY, new Net(a.network)));
    }

    public static void create(int n) {
        listOfPredators = new ArrayList<>();
        for (int i = 0; i < n; i++)
            listOfPredators.add(new Predator(Math.random() * f.getWidth(), Math.random() * f.getHeight()));
    }

}
