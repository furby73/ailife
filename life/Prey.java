package life;

import ai.Net;

import java.util.ArrayList;

public class Prey extends Creature {


    public Prey(double posX, double posY) {
        super(posX, posY);
        maxRangeOfVision = 40.0;
        rangeOfVision = Math.PI * 1.8;
        maxPopulation = 800;
    }

    public Prey(double posX, double posY, Net network) {
        super(posX, posY);
        this.network = new Net(network);

        maxRangeOfVision = 40.0;
        rangeOfVision = Math.PI * 1.8;
        maxPopulation = 500;

    }


    public static void giveBirth(Prey a) {
        if (listOfPreys.size() < a.maxPopulation)
            listOfPreys.add(new Prey(a.posX, a.posY, new Net(a.network)));
    }

    public static void create(int n) {
        listOfPreys = new ArrayList<>();
        for (int i = 0; i < n; i++)
            listOfPreys.add(new Prey(Math.random() * f.getWidth(), Math.random() * f.getHeight()));
    }


}
