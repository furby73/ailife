package positioning;

public class Field {
    private final int width;
    private final int height;

    public static double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public Field(int height, int width) {
        this.height = height;
        this.width = width;
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
