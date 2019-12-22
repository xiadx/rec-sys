package game;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class PlaneGameFrameDemoI extends JFrame {

    Image ball = GameUtil.getImage("images/ball.png");

    @Override
    public void paint(Graphics g) {
        g.drawImage(ball, 250, 250, null);
    }

    public void launchFrame() {
        this.setTitle("PlaneGameFrameDemoI");
        this.setVisible(true);
        this.setSize(500, 500);
        this.setLocation(300, 300);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        PlaneGameFrameDemoI f = new PlaneGameFrameDemoI();
        f.launchFrame();
    }

}