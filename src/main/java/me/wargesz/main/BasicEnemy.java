package me.wargesz.main;

import java.awt.*;

public class BasicEnemy extends GameObject {

    public BasicEnemy(int x,int y,ID id) {
        super(x,y,id);
    }

    @Override
    public void tick() {
        x+=velX;
        y+=velY;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x,y,16,16);
    }
}
