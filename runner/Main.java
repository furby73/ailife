package runner;

import life.Creature;
import life.Predator;
import life.Prey;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        PrintStream o = new PrintStream(new File("src/input.txt"));
        System.setOut(o);

        // random change when birth
        Prey.create(170);
        Predator.create(20);
        String output = "";
        int i = 0;
        StringBuilder bld = new StringBuilder();

        while (Creature.getNumberOfPreys() * Creature.getNumberOfPredators() > 0) {
            i++;
            Creature.numberOfTicksIncrease();
            Creature.runTickForEveryCreature();
            bld.append(Creature.getNumberOfPreys()).append("\n");
            for (Creature a : Creature.listOfPreys)
                bld.append(a.posX).append(" ").append(a.posY).append(" ").append(a.getDirection()).append("\n");
            bld.append(Creature.getNumberOfPredators()).append("\n");
            for (Creature a : Creature.listOfPredators)
                bld.append(a.posX).append(" ").append(a.posY).append(" ").append(a.getDirection()).append("\n");
            if (i == 500000)
                break;
        }
        output = bld.toString();
        System.out.println(i);
        System.out.println(output);
    }
}
