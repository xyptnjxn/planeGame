package planeGame;

import java.awt.*;

public class GameObject {
    Image img;
    double x, y;
    int speed;
    int width, height;

    public void drawSelf(Graphics g){
        g.drawImage(img, (int)x, (int)y, null);
    }

    public Rectangle getRect(){
        return new Rectangle((int)x, (int)y, width, height);
    }

    public GameObject() {
    }

    public GameObject(Image img, double x, double y, int speed, int width, int height) {
        this.img = img;
        this.x =x;
        this.y = y;
        this.speed = speed;
        this.width = width;
        this.height = height;
    }
    public GameObject(Image img, double x, double y) {
        this.img = img;
        this.x =x;
        this.y = y;
    }
}
