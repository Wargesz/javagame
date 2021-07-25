package me.wargesz.main;

import java.awt.*;

public class MoveSet extends GameObject{

    protected String[] moves;

    public MoveSet(int x, int y,ID id,String[] moves) {
        super(x, y, id);
        this.moves = moves;
    }

    public void tick() {
        x += velX;
        y += velY;
    }
    public void render(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        for (int i = 0;i<moves.length;i++) {
            switch (moves[i]) {
                case "up":
                    //g.drawString("up",40+60*i,200);
                    g.drawRect(220+(30+16)*i,200,30,30);
                    g.drawString("↑",230+(30+16)*i,220);
                    break;
                case "down":
                    //g.drawString("down",40+60*i,200);
                    g.drawRect(220+(30+16)*i,200,30,30);
                    g.drawString("↓",230+(30+16)*i,220);
                    break;
                case "left":
                    //g.drawString("left",40+60*i,200);
                    g.drawRect(220+(30+16)*i,200,30,30);
                    g.drawString("←",230+(30+16)*i,220);
                    break;
                case "right":
                    //g.drawString("right",40+60*i,200);
                    g.drawRect(220+(30+16)*i,200,30,30);
                    g.drawString("→",230+(30+16)*i,220);
                    break;
            }
        }
        g.setColor(Color.red);
        g.drawLine(220+(30+16)*counter,240,28+220+(30+16)*counter,240);
    }
    public String[] getMoves() {
        return moves;
    }
}
