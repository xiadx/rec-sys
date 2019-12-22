package game;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class PlaneGameFrameDemoIII extends JFrame {

    Image plane = GameUtil.getImage("images/plane.png");
    Image bg = GameUtil.getImage("images/bg.jpg");

    int planeX = 250, planeY = 250;

    @Override
    public void paint(Graphics g) {
        g.drawImage(bg, 0, 0, null);
        g.drawImage(plane, planeX, planeY, null);
        planeX++;
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
        this.setTitle("PlaneGameFrameDemoIII");
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
        PlaneGameFrameDemoIII f = new PlaneGameFrameDemoIII();
        f.launchFrame();
    }

}