package me.wargesz.main;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Notification extends GameObject{

    protected List<String> text = new ArrayList<>();
    protected List<Integer> lifeTime = new ArrayList<>();
    private boolean isActive = true;

    public Notification(int x,int y,ID id) {
        super(x, y, id);
    }

    public void tick() {
        if (isActive) {
            for (int i = 0; i < lifeTime.size(); i++) {
                if (lifeTime.get(i) == 30) {
                    lifeTime.remove(i);
                    text.remove(i);
                } else lifeTime.set(i, lifeTime.get(i) + 1);
            }
        }
    }

    public void render(Graphics g) {
        for (int i = 0;i<text.size();i++) {
            g.setColor(Color.WHITE);
            g.drawString(text.get(i),100,100+i*20);
        }
    }

    public void addText(String string) {
        text.add(string);
        lifeTime.add(0);
        counter++;
    }
    public void clearTexts() {
        text.clear();
        lifeTime.clear();
    }
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
