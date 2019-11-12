package planeGame;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

public class MyGameFrame extends Frame {

    //solve the splash
    private Image offScreenImage = null;

    @Override
    public void update(Graphics g) {
        if(offScreenImage == null)
            offScreenImage = this.createImage(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);//这是游戏窗口的宽度和高度

        Graphics gOff = offScreenImage.getGraphics();
        paint(gOff);
        g.drawImage(offScreenImage, 0, 0, null);
    }
    //done

    Image bg = GameUtil.getImage("img/bg.jpg");
    Image planeImg = GameUtil.getImage("img/plane.png");

    Plane plane = new Plane(planeImg, 250, 750);
    Shell[] shells = new Shell[50];
    Explode explode;

    Date startTime = new Date();
    Date endTime;
    int period;

    //paint
    @Override
    public void paint(Graphics g){ //execute automatically, and g is a brush
        super.paint(g);
        /*Color c = g.getColor();
        //System.out.println(c);
        g.setColor(Color.black);
        g.drawLine(100, 100, 200, 200);
        g.drawImage(ball, 200, 200, null);

        g.setColor(c);*/
        Color c = g.getColor();
        Font f = g.getFont();
        g.drawImage(bg, 0, 0, null);
        plane.drawSelf(g);
        for (int i=0; i<shells.length; i++) {
            shells[i].draw(g);
            boolean bang = shells[i].getRect().intersects(plane.getRect());
            if (bang) {
                plane.live = false;
                if (explode == null)
                    explode = new Explode(plane.x, plane.y);
                if (endTime == null) {
                    endTime = new Date();
                    explode.draw(g);
                }

            }
        }
        if (!plane.live) {
            period = (int)(endTime.getTime() - startTime.getTime()) / 1000;
            g.setColor(Color.red);
            Font newF = new Font("宋体", Font.BOLD, 50);
            g.setFont(newF);
            g.drawString("duration:"+period, 500, 500);
        }

        g.setColor(c);
        g.setFont(f);

    }

    //paint thread
    class PaintThread extends Thread {
        @Override
        public void run() {
            while(true){
                //System.out.println("repaint once");
                repaint();

                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //keyboard monitor
    class KeyMonitor extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            plane.addDirection(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            plane.subDirection(e);
        }
    }

    //generate the window
    public void LaunchWindow(){
        this.setTitle("my plane game window");
        this.setVisible(true);
        this.setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
        this.setLocation(300, 100);
        //terminate the program on closing the window
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        for (int i=0; i<shells.length; i++)
            shells[i] = new Shell();

        new PaintThread().start();
        addKeyListener(new KeyMonitor());
    }

    public static void main(String[] args) {
        MyGameFrame f = new MyGameFrame();
        f.LaunchWindow();
    }
}
