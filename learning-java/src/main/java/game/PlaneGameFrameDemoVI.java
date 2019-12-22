package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class PlaneGameFrameDemoVI extends JFrame {

    Image plane = GameUtil.getImage("images/plane.png");
    Image bg = GameUtil.getImage("images/bg.jpg");

    Plane p = new Plane(plane, 250, 250);

    @Override
    public void paint(Graphics g) {
        g.drawImage(bg, 0, 0, null);
        p.drawSelf(g);
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

    class KeyMonitor extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            p.addDirection(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            p.minusDirection(e);
        }
    }

    public void launchFrame() {
        this.setTitle("PlaneGameFrameDemoVI");
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
        addKeyListener(new KeyMonitor());
    }

    public static void main(String[] args) {
        PlaneGameFrameDemoVI f = new PlaneGameFrameDemoVI();
        f.launchFrame();
    }

}
