package me.wargesz.main;

import java.awt.*;

public class Timer extends GameObject{

    private Game game;
    private Handler handler;
    private Notification notification;
    private HUD hud;
    private int diff = 1;
    private int width = 640;
    private boolean isOn = true;

    public Timer(int x,int y,ID id,Game game,Handler handler,Notification notification,HUD hud) {
        super(x, y, id);
        this.game = game;
        this.handler = handler;
        this.notification = notification;
        this.hud = hud;
    }

    public void tick() {
        if (!isOn) {
            return;
        }
        if (game.isLvlUp()) {
            notification.addText("Advancement");
            diff++;
            game.setLvlUps(0);
            hud.advance();
        }
        width = width-diff;
        if (width<=0) {
            notification.addText("FINAL SCORE: "+game.getScore());
            game.endGame();
        }
    }

    public void render(Graphics g) {
        //g.setColor(getColor());
        g.setColor(Color.gray);
        g.fillRect(0,0,width,480);
    }

    public Color getColor() {
        Color color = Color.GRAY;
        int i = hud.getLvl();
        switch (i) {
            case 1:
                color = Color.gray;
                break;
            case 2:
                color = Color.BLUE;
                break;
            case 3:
                color = Color.CYAN;
                break;
            case 4:
                color = Color.MAGENTA;
                break;
            case 5:
                color = Color.orange;
                break;
            default:
                color = Color.gray;
                break;
        }
        return color;
    }
    public void setWidth(int w) {
        width = w;
    }
    public void setIsOn(boolean isOn) {
        this.isOn = isOn;
    }
    public boolean isOn() {
        return isOn;
    }
    public void resetDif() {
        diff = 1;
    }
}