package vision;

public class reyCast {

    public static double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    static final double[][] move = {
            {0, 0},
            {128,
                    128},
            {128,
                    -128},
            {-128,
                    128},
            {-128,
                    -128},
            {0, 128},
            {128, 0},
            {0, -128},
            {-128, 0}};

    public static double rey(double maxR, double r, double[][] cordOfAnotherCreatures, double x, double y, double angle) {
        double[][] newCords = new double[cordOfAnotherCreatures.length * 9][2];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < cordOfAnotherCreatures.length; j++) {
                newCords[j * 9 + i][0] = cordOfAnotherCreatures[j][0] + move[i][0];
                newCords[j * 9 + i][1] = cordOfAnotherCreatures[j][1] + move[i][1];
            }
        }
        // added coordinates are for vision "through" walls

        double res = maxR;

        for (double[] cordOfAnotherCreature : newCords) {
            double rx = cordOfAnotherCreature[0];
            double ry = cordOfAnotherCreature[1];
            double tempx = x;
            double tempy = y;
            x -= rx;
            y -= ry;
            double sinq = Math.sin(angle);
            double cosq = Math.cos(angle);

            // x sin q - y cos q + (b cos q - a sin q) = 0

            double a = sinq, b = -cosq, c = x * cosq - y * sinq;


            double x0 = -a * c / (a * a + b * b), y0 = -b * c / (a * a + b * b);
            double v = r * r * (a * a + b * b);
            double EPS = 1e-7;
            if (c * c > v + EPS)
                res = maxR;
            else if (Math.abs(c * c - v) < EPS) {
                double d = dist(x, y, x0, y0);
                if (cosq * ((x0 - x) / d) > 0 && sinq * ((y0 - y) / d) > 0) {
                    res = Math.min(d, maxR);
                } else {
                    res = maxR;
                }
            } else {
                double d = r * r - c * c / (a * a + b * b);
                double mult = Math.sqrt(d / (a * a + b * b));
                double ax, ay, bx, by;
                ax = x0 + b * mult;
                bx = x0 - b * mult;
                ay = y0 - a * mult;
                by = y0 + a * mult;
                d = dist(x, y, ax, ay);
                if (cosq * ((ax - x) / d) > 0 && sinq * ((ay - y) / d) > 0) {
                    res = Math.min(d, maxR);
                } else {
                    res = maxR;
                }
                d = dist(x, y, bx, by);
                if (cosq * ((bx - x) / d) > 0 && sinq * ((by - y) / d) > 0) {
                    res = Math.min(d, res);
                    res = Math.min(maxR, res);
                }
            }
            x = tempx;
            y = tempy;
        }
        if (res != maxR)
            return maxR - res;
        return maxR - res;
    }
}
