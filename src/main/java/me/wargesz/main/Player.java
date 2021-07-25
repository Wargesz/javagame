package me.wargesz.main;

import java.awt.*;
import java.util.Random;

public class Player extends GameObject{


    public Player(int x, int y,ID id,String[] moves) {
        super(x, y, id);
    }

    public void tick() {
        x += velX;
        y += velY;
    }
    public void render(Graphics g) {
        g.setColor(Color.blue);
        g.fillRect(x,y,32,32);
    }
}
