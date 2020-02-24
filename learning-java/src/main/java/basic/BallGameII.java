package basic;

import java.awt.*;
import javax.swing.*;

public class BallGameII extends JFrame {

    Image ball = Toolkit.getDefaultToolkit().getImage("src/main/resources/images/ball.png");
    Image desk = Toolkit.getDefaultToolkit().getImage("src/main/resources/images/desk.jpg");

    double x = 100;
    double y = 100;
    double degree = 3.14 / 3;

    public void paint(Graphics g) {
        System.out.println("draw once");
        g.drawImage(desk, 0, 0, null);
        g.drawImage(ball, (int)x, (int)y, null);

        x = x + 10 * Math.cos(degree);
        y = y + 10 * Math.sin(degree);

        if (y > 500 - 40 - 30 || y < 40 + 40) {
            degree = -degree;
        }

        if (x < 40 || x > 856 - 40 - 30) {
            degree = 3.14 - degree;
        }
    }

    void launchFrame() {
        setSize(856, 500);
        setLocation(50, 50);
        setVisible(true);

        while (true) {
            repaint();
            try {
                Thread.sleep(40);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        BallGameII game = new BallGameII();
        game.launchFrame();
    }

}
