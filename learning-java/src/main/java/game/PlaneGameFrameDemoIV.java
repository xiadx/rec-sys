package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class PlaneGameFrameDemoIV extends JFrame {

    Image plane = GameUtil.getImage("images/plane.png");
    Image bg = GameUtil.getImage("images/bg.jpg");

    Plane p1 = new Plane(plane, 250, 250);
    Plane p2 = new Plane(plane, 350, 350);
    Plane p3 = new Plane(plane, 450, 450);

    @Override
    public void paint(Graphics g) {
        g.drawImage(bg, 0, 0, null);

        p1.drawSelf(g);
        p2.drawSelf(g);
        p3.drawSelf(g);
    }

    class PaintThread extends Thread {
        @Override
        public void run() {
            while (true) {
                repaint();

                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void launchFrame() {
        this.setTitle("PlaneGameFrameDemoIV");
        this.setVisible(true);
        this.setSize(500, 500);
        this.setLocation(300, 300);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        new PaintThread().start();
    }

    public static void main(String[] args) {
        PlaneGameFrameDemoIV f = new PlaneGameFrameDemoIV();
        f.launchFrame();
    }

}