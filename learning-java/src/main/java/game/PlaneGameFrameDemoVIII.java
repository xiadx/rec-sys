package game;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class PlaneGameFrameDemoVIII extends Frame {

    Image plane = GameUtil.getImage("images/plane.png");
    Image bg = GameUtil.getImage("images/bg.jpg");

    Plane p = new Plane(plane, 250, 250);
    Shell[] ss = new Shell[50];

    @Override
    public void paint(Graphics g) {
        g.drawImage(bg, 0, 0, null);
        p.drawSelf(g);
        for (int i = 0; i < ss.length; i++) {
            ss[i].draw(g);

            boolean e = ss[i].getRect().intersects(p.getRect());
            if (e) {
                p.live = false;
            }
        }
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
        this.setTitle("PlaneGameFrameDemoVIII");
        this.setVisible(true);
        this.setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
        this.setLocation(300, 300);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        new PaintThread().start();
        addKeyListener(new KeyMonitor());

        for (int i = 0; i < ss.length; i++) {
            ss[i] = new Shell();
        }
    }

    public static void main(String[] args) {
        PlaneGameFrameDemoVIII f = new PlaneGameFrameDemoVIII();
        f.launchFrame();
    }

    private Image offScreenImage = null;

    public void update(Graphics g) {
        if (offScreenImage == null)
            offScreenImage = this.createImage(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);

        Graphics gOff = offScreenImage.getGraphics();
        paint(gOff);
        g.drawImage(offScreenImage, 0, 0, null);
    }

}
