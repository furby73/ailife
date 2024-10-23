package life;

import ai.Net;
import positioning.Field;
import vision.reyCast;

import java.util.ArrayList;

public class Creature {
    int standTicks;
    int eatenPreysCount;
    static ArrayList<Creature> birthTo;
    int numberOfTicksAlive;
    static double energyDecreaseOfPredator = 0.28;
    int ticksToStayStill;
    static double eatingIncrease = 30;
    static double maxSpeed = 5;
    static double maxAngleSpeed = Math.PI / 4;
    static double radius = 1;
    static public ArrayList<Prey> listOfPreys;
    static public ArrayList<Predator> listOfPredators;

    public static int getNumberOfPreys() {
        return listOfPreys.size();
    }

    public static int getNumberOfPredators() {
        return listOfPredators.size();
    }

    static int numberOfTicks;

    public static void numberOfTicksIncrease() {
        numberOfTicks++;
    }

    public static final Field f = new Field(128, 128);
    Net network;
    double speed;
    double maxRangeOfVision;
    int maxPopulation;
    double angleSpeed;
    public double posX;
    public double posY;
    double direction;

    public double getDirection() {
        return direction;
    }

    static int numberOfRays = 5;
    static double rangeOfVision;
    double energy;
    static double energyCoefficient = 8;

    public static void runTickForEveryCreature() {
        birthTo = new ArrayList<>();
        ArrayList<Predator> deadPredator = new ArrayList<>();
        for (Predator predator : listOfPredators) {
            if (predator.tick() == 1)
                deadPredator.add(predator);
        }
        deathFromLowEnergy(deadPredator);
        if (listOfPreys.size() * listOfPredators.size() == 0)
            return;
        for (Prey prey : listOfPreys) {
            prey.tick();
        }
        ArrayList<Prey> deadPreys = new ArrayList<>();
        for (Prey prey : listOfPreys)
            for (Predator predator : listOfPredators) {
                if (Field.dist(predator.posX, predator.posY, prey.posX, prey.posY) < 2 * radius) {
                    predator.energy += eatingIncrease;
                    predator.eatenPreysCount++;
                    predator.energy = Math.max(predator.energy, 100);
                    deadPreys.add(prey);
                }
            }
        deathByEaten(deadPreys);
        if (!birthTo.isEmpty())
            for (Creature c : birthTo) {
                if (c instanceof Prey) {
                    Prey.giveBirth((Prey) c);
                } else
                    Predator.giveBirth((Predator) c);
            }
        birthTo.clear();
    }

    public Creature(double posX, double posY) {
        standTicks = 0;
        eatenPreysCount = 0;
        numberOfTicksAlive = 130;
        energyStillIncrease = 5;
        ticksToStayStill = 0;
        speed = 0;
        angleSpeed = 0;
        this.posX = posX;
        this.posY = posY;
        direction = 2 * Math.PI * Math.random();
        energy = 100;
        network = new Net(new int[]{numberOfRays + 1, 2});
    }

    public void directionAdd() {
        direction += angleSpeed;
    }

    public void posAdd() {
        posX += speed * Math.cos(direction);
        posY += speed * Math.sin(direction);
        if (posX > f.getWidth()) {
            posX -= f.getWidth();
        }
        if (posX < 0) {
            posX += f.getWidth();
        }
        if (posY > f.getHeight()) {
            posY -= f.getHeight();
        }
        if (posY < 0) {
            posY += f.getHeight();
        }
    }

    public void move() {
        directionAdd();
        posAdd();
        energy -= Math.abs(speed) * energyCoefficient;
    }

    public double[] getRayCastValues() {
        double[] raysValues = new double[numberOfRays];
        double directionOfRay = direction - rangeOfVision / 2;
        double[][] cordOfAnotherCreature;
        if (this instanceof Prey) {
            cordOfAnotherCreature = new double[listOfPredators.size()][2];
            int i = 0;
            for (Predator predator : listOfPredators) {
                cordOfAnotherCreature[i++] = new double[]{predator.posX, predator.posY};
            }
        } else {
            cordOfAnotherCreature = new double[listOfPreys.size()][2];
            int i = 0;
            for (Prey prey : listOfPreys) {
                cordOfAnotherCreature[i++] = new double[]{prey.posX, prey.posY};
            }
        }
        for (int i = 0; i < numberOfRays; i++) {
            raysValues[i] = reyCast.rey(maxRangeOfVision, radius, cordOfAnotherCreature, posX, posY, directionOfRay);
            directionOfRay += rangeOfVision / (numberOfRays - 1);
        }
        return raysValues;
    }

    double energyStillIncrease;

    public int tick() {

        if (this instanceof Predator) {
            energy -= energyDecreaseOfPredator;
        }
        if (ticksToStayStill != 0) {
            ticksToStayStill--;
            speed = 0;
            energy += energyStillIncrease;
            return 0;
        } else {
            numberOfTicksAlive++;
        }
        double[] inputLayer = new double[numberOfRays + 1];
        double[] reyCastValues = getRayCastValues();
        for (int j = 0; j < numberOfRays; j++)
            inputLayer[j] = reyCastValues[j];
        inputLayer[numberOfRays] = 1;
        double[] m = network.outputLayer(inputLayer);
        speed = (m[0] - 0.5) * maxSpeed;
        angleSpeed = (m[1] - 0.5) * maxAngleSpeed;
        move();

        if (energy <= 0) {
            if (this instanceof Predator) return 1;
            ticksToStayStill = 10;
        }
        if (numberOfTicksAlive % 144 == 0 && this instanceof Prey) {
            birthTo.add(this);
        }

        if (eatenPreysCount >= 2) {
            birthTo.add(this);
            eatenPreysCount = 0;
        }
        return 0;

    }


    private static void deathByEaten(ArrayList<Prey> deadPreys) {
        listOfPreys.removeAll(deadPreys);
    }

    public static void deathFromLowEnergy(ArrayList<Predator> i) {
        listOfPredators.removeAll(i);
    }


}
