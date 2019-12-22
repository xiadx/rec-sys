package game;

import java.util.Date;

import java.awt.Frame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class PlaneGameFrameDemoIX extends Frame {

    Image plane = GameUtil.getImage("images/plane.png");
    Image bg = GameUtil.getImage("images/bg.jpg");

    Plane p = new Plane(plane, 250, 250);
    Shell[] ss = new Shell[50];

    Explode ex;
    Date startTime = new Date();
    Date endTime;

    int period;

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.drawImage(bg, 0, 0, null);
        p.drawSelf(g);
        for (int i = 0; i < ss.length; i++) {
            if (ss[i] != null)
                ss[i].draw(g);

            boolean e = ss[i].getRect().intersects(p.getRect());
            if (e) {
                p.live = false;
                if (ex == null) {
                    ex = new Explode(p.x, p.y);
                    endTime = new Date();
                    period = (int)((endTime.getTime() - startTime.getTime()) / 1000);
                }
                ex.draw(g);
            }

            if (!p.live) {
                g.setColor(Color.RED);
                Font f = new Font("Times New Roman", Font.BOLD, 50);
                g.setFont(f);
                g.drawString("" + period, (int) p.x, (int) p.y);
            }
        }
        g.setColor(c);
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
        this.setTitle("PlaneGameFrameDemoIX");
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
        PlaneGameFrameDemoIX f = new PlaneGameFrameDemoIX();
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
