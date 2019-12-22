package object;

public class PointDemo {

    double x, y;

    public PointDemo(double _x, double _y) {
        x = _x;
        y = _y;
    }

    public double getDistance(PointDemo p) {
        return Math.sqrt((x - p.x) * (x - p.x) + (y - p.y) * (y - p.y));
    }

    public static void main(String[] args) {
        PointDemo p1 = new PointDemo(0.0, 0.0);
        PointDemo p2 = new PointDemo(3.0, 4.0);
        System.out.println(p1.getDistance(p2));
    }

}