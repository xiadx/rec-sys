package game;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PlaneGameFrameDemoII extends JFrame {

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        Font f = g.getFont();
        g.setColor(Color.GRAY);

        g.drawLine(100, 100, 300, 300);
        g.drawRect(100, 100, 300, 300);
        g.drawOval(100, 100, 300, 300);
        g.fillRect(100, 100, 40, 40);
        g.setFont(new Font("Times New Roman", Font.BOLD, 50));
        g.drawString("abc", 200, 200);

        g.setFont(f);
        g.setColor(c);
    }

    public void launchFrame() {
        this.setTitle("PlaneGameFrameDemoII");
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

    public static void printAvailableFontFamilyNames() {
        GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontName = e.getAvailableFontFamilyNames();
        for (String s : fontName) {
            System.out.println(s);
        }
    }

    public static void main(String[] args) {
        printAvailableFontFamilyNames();
        PlaneGameFrameDemoII f = new PlaneGameFrameDemoII();
        f.launchFrame();
    }

}