package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class GameObject {

    Image img;
    double x, y;
    int speed;
    int width, height;

    public GameObject(Image img, double x, double y, int speed, int width, int height) {
        super();
        this.img = img;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.width = width;
        this.height = height;
    }

    public GameObject(Image img, double x, double y) {
        super();
        this.img = img;
        this.x = x;
        this.y = y;
    }

    public GameObject() {

    }

    public void drawSelf(Graphics g) {
        g.drawImage(img, (int)x, (int)y, null);
    }

    public Rectangle getRect() {
        return new Rectangle((int)x, (int)y, width, height);
    }

}