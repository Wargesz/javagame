package me.wargesz.main;

import org.yaml.snakeyaml.Yaml;

import javax.net.ssl.SSLContext;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.*;
import java.util.*;

public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 640,HEIGHT = WIDTH/12*9;
    private Thread thread;
    private boolean running = false;

    private Handler handler =  new Handler();
    private Notification notification = new Notification(0,0,ID.Notification);
    private HUD hud = new HUD(0,0,ID.HUD,this);
    private Timer timer = new Timer(0,480,ID.Timer,this,handler,notification,hud);
    private MoveSet moveSet = new MoveSet(100,100,ID.MoveSet,getMoves());
    private int frames = 0;
    private Random r;
    private int score = 0;
    private int lvlups = 0;
    private int highscore = 0;
    private boolean inGame = false;
    private Yaml yaml = new Yaml();
    private Map<String,Object> data;
    private String path = "lib/data.yml";

    public static void main(String args[]) {
        new Game();
    }

    public Game() {
        loadYaml();
        this.addKeyListener(new KeyInput(handler,this,timer,notification,hud,moveSet));
        new Window(WIDTH, HEIGHT,"game",this);
        handler.addObject(new MainMenu(0,0,ID.MainMenu));
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }
    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000/amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now-lastTime)/ns;
            lastTime=now;
            while (delta>=1){
                tick();
                delta--;
            }
            if (running)
                render();
            frames++;
            if (System.currentTimeMillis()-timer>1000){
                timer+=1000;
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        handler.tick();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs==null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        Font myFont = new Font ("Courier New", 1, 17);
        g.setFont(myFont);
        g.setColor(Color.BLACK);
        g.fillRect(0,0,WIDTH,HEIGHT);
        handler.render(g);
        if (inGame) {
            g.setColor(Color.WHITE);
            g.drawString("FPS: " + String.valueOf(frames), 1, 20);
            g.drawString("Score: " + String.valueOf(score), 1, 40);
            g.drawString("Highscore: " + String.valueOf(highscore), 1, 60);
        }
        g.dispose();
        bs.show();
    }

    public String[] getMoves() {
        String[] moves = new String[4];
        for (int i = 0;i<4;i++) {
            r = new Random();
            switch (r.nextInt(4)) {
                case 0:
                    moves[i] = "up";
                    break;
                case 1:
                    moves[i] = "down";
                    break;
                case 2:
                    moves[i] = "left";
                    break;
                case 3:
                    moves[i] = "right";
                    break;
            }
        }
        return moves;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public int getScore() {
        return score;
    }
    public void endGame() {
        saveScore();
        saveData();
        timer.setIsOn(false);
        render();
        notification.setIsActive(false);
        System.out.println("GAME ENDED");
    }
    public boolean isRunning() {
        return running;
    }
    public boolean isLvlUp() {
        if (lvlups == 10) {
            return true;
        } else return false;
    }
    public void setLvlUps(int lvlups) {
        this.lvlups = lvlups;
    }
    public int getLvlUps() {
        return lvlups;
    }
    public void stopMoves() {
        for (int i = 0;i<handler.object.size();i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId()==ID.MoveSet) {
                MoveSet ms = (MoveSet) tempObject;
                handler.removeObject(ms);
            }
            if (tempObject.getId()==ID.Timer) {
                Timer t = (Timer) tempObject;
                handler.removeObject(t);
            }
        }
        notification.clearTexts();
    }
    public void saveScore() {
        if (score<=highscore) {
            return;
        } else {
            data.put("highscore",score);
        }
    }
    public void loadScore() {
        if (data.containsKey("highscore")) {
            highscore = (Integer) data.get("highscore");
            System.out.println("Highscore loaded.");
        } else {
            System.out.println("No highscore");
        }
    }
    public void updateScore() {
        highscore = (Integer) data.get("highscore");
    }
    public void setInGame (boolean inGame) {
        this.inGame = inGame;
    }

    public void saveData() {
        try {
            PrintWriter writer = new PrintWriter(new File(path));
            yaml.dump(data,writer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadYaml() {
        File file = new File(path);
        try {
            if (!file.exists()) {
                try {
                    file.createNewFile();
                    System.out.println("Creating Save file.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Save file found.");
            }
            if (null==yaml.load(new FileInputStream(file))) {
                data = new HashMap<>();
                System.out.println("Empty file.");
            } else {
                data = yaml.load(new FileInputStream(file));
                System.out.println("Data loaded.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }
}
