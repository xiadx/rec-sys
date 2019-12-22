package game;

import java.awt.Color;
import java.awt.Graphics;

public class Shell extends GameObject {

    double degree;

    public Shell() {
        x = 200;
        y = 200;
        width = 10;
        height = 10;
        speed = 3;
        degree = Math.random() * Math.PI * 2;
    }

    public void draw(Graphics g) {
        Color c = g.getColor();

        g.setColor(Color.YELLOW);
        g.fillOval((int)x, (int)y, width, height);

        x += speed * Math.cos(degree);
        y += speed * Math.sin(degree);

        if (x < 0 || x > Constant.GAME_WIDTH - width) {
            degree = Math.PI - degree;
        }

        if (y < 30 || y > Constant.GAME_HEIGHT - height) {
            degree = -degree;
        }

        g.setColor(c);
    }

}
