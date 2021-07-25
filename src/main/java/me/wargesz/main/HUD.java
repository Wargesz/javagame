package me.wargesz.main;

import java.awt.*;

public class HUD extends GameObject {

    private Game game;
    private int lvl = 1;

    public HUD(int x,int y,ID id,Game game) {
        super (x,y,id);
        this.game = game;
    }

    public void tick() {

    }

    public void render(Graphics g) {
        /*
        g.setColor(Color.green);
        for (int i = 1;i<=lvl;i++) {
            g.fillRect((50+40+10)*i,70,40,10);
        }
         */
    }

    public void advance() {
        lvl++;
    }

    public Integer getLvl() {
        return lvl;
    }
}
