package me.wargesz.main;

import java.awt.*;
import java.util.Random;

public class MainMenu extends GameObject {

    private String choice = "left";

    public MainMenu(int x,int y,ID id) {
        super(x, y, id);
    }

    public void tick() {

    }

    public void render(Graphics g) {
        String startword = "zsa szarni";
        g.setColor(Color.WHITE);
        Font myFont = new Font ("Courier New", 1, 40);
        g.setFont(myFont);
        g.drawString("marci menő játéka",320-g.getFontMetrics().stringWidth("marci menő játéka")/2,60);
        myFont = new Font ("Courier New", 1, 25);
        g.setFont(myFont);
        g.drawString(startword,30+160-g.getFontMetrics().stringWidth(startword)/2,200);
        g.drawString("ládikó",450-g.getFontMetrics().stringWidth("ládikó")/2,200);
        g.setColor(Color.red);
        if (choice=="left") {
            g.drawLine(30+160-g.getFontMetrics().stringWidth(startword)/2,210,30+160+g.getFontMetrics().stringWidth(startword)/2,210);
        } else {
            g.drawLine(450-g.getFontMetrics().stringWidth("ládikó")/2,210,450+g.getFontMetrics().stringWidth("ládikó")/2,210);
        }
    }
    public void setChoice(String choice) {
        this.choice = choice;
    }
    public String getChoice() {
        return choice;
    }
}
