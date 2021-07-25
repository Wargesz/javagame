package me.wargesz.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.util.Random;

public class KeyInput extends KeyAdapter {

    private Handler handler;
    private Game game;
    private Timer timer;
    private Notification notification;
    private HUD hud;
    private MoveSet moveSet;
    private boolean keyInUse = false;
    private Random r = new Random();

    public KeyInput(Handler handler,Game game,Timer timer,Notification notification,HUD hud,MoveSet moveSet) {
        this.handler = handler;
        this.game = game;
        this.timer = timer;
        this.notification = notification;
        this.hud = hud;
        this.moveSet = moveSet;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key==KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
        if (!keyInUse && game.isRunning()) {
            keyInUse = true;
            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);
                if (tempObject.getId() == ID.MoveSet) {
                    MoveSet moveSet = (MoveSet) tempObject;
                    if (timer.isOn()) {
                        if (moveSet.getMoves()[tempObject.getCounter()].equals("up") && key == KeyEvent.VK_UP) {
                            if (tempObject.getCounter() != 3) {
                                tempObject.setCounter(tempObject.getCounter() + 1);
                            } else {
                                newMoveSet(tempObject,true);
                                return;
                            }
                        } else if (moveSet.getMoves()[tempObject.getCounter()].equals("down") && key == KeyEvent.VK_DOWN) {
                            if (tempObject.getCounter() != 3) {
                                tempObject.setCounter(tempObject.getCounter() + 1);
                            } else {
                                newMoveSet(tempObject,true);
                                return;
                            }
                        } else if (moveSet.getMoves()[tempObject.getCounter()].equals("left") && key == KeyEvent.VK_LEFT) {
                            if (tempObject.getCounter() != 3) {
                                tempObject.setCounter(tempObject.getCounter() + 1);
                            } else {
                                newMoveSet(tempObject,true);
                                return;
                            }
                        } else if (moveSet.getMoves()[tempObject.getCounter()].equals("right") && key == KeyEvent.VK_RIGHT) {
                            if (tempObject.getCounter() != 3) {
                                tempObject.setCounter(tempObject.getCounter() + 1);
                            } else {
                                newMoveSet(tempObject,true);
                                return;
                            }
                        } else {
                            notification.addText("FINAL SCORE: " + game.getScore());
                            notification.addText("press space to start again");
                            game.saveScore();
                            game.endGame();
                        }
                    } else {
                        if (key == KeyEvent.VK_SPACE) {
                            notification.clearTexts();
                            game.setScore(0);
                            game.updateScore();
                            newMoveSet(tempObject,false);
                            timer.resetDif();
                            timer.setIsOn(true);
                            notification.setIsActive(true);
                        }
                    }
                }
                if (tempObject.getId() == ID.MainMenu) {
                    MainMenu mainMenu = (MainMenu) tempObject;
                    if (key == KeyEvent.VK_RIGHT&&mainMenu.getChoice()!="right") {
                        mainMenu.setChoice("right");
                    }
                    if (key == KeyEvent.VK_LEFT&&mainMenu.getChoice()!="left") {
                        mainMenu.setChoice("left");
                    }
                    if (key == KeyEvent.VK_ENTER) {
                        if (mainMenu.getChoice()=="left") {
                            handler.removeObject(tempObject);
                            handler.addObject(timer);
                            handler.addObject(hud);
                            handler.addObject(moveSet);
                            handler.addObject(notification);
                            game.setInGame(true);
                            game.loadScore();
                            return;
                        }
                        if (mainMenu.getChoice()=="right") {

                        }
                    }
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyInUse = false;
    }

    public void newMoveSet(GameObject tempObject,boolean isLvlUp) {
        game.setLvlUps(game.getLvlUps()+1);
        handler.removeObject(tempObject);
        handler.addObject(new MoveSet(100,100,ID.MoveSet,game.getMoves()));
        notification.addText(getMsg());
        if (isLvlUp) {
            game.setScore(game.getScore() + 1);
        }
        for (int i = 0;i<handler.object.size();i++) {
            GameObject obj = handler.object.get(i);
            if (obj.getId() == ID.Timer) {
                Timer timer = (Timer) obj;
                timer.setWidth(640);
            }
        }
    }

    public String getMsg() {
        String msg = "";
        int choice = r.nextInt(11);
        switch (choice) {
            case 0:
                msg = "well done";
                break;
            case 1:
                msg = "good boy";
                break;
            case 2:
                msg = "bravo";
                break;
            case 3:
                msg = "hip,hip,hooray";
                break;
            case 4:
                msg = "my compliments";
                break;
            case 5:
                msg = "nice one";
                break;
            case 6:
                msg = "that's the way";
                break;
            case 7:
                msg = "good thinking";
                break;
            case 8:
                msg = "you're a genius";
                break;
            case 9:
                msg = "nice rhythm";
                break;
            case 10:
                msg = "keep it up";
                break;
        }
        return msg;
    }

    public void toMenu() {
        game.endGame();
        for (int i = 0;i<handler.object.size();i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId()==ID.Timer) {
                handler.removeObject(tempObject);
            }
            if (tempObject.getId()==ID.HUD) {
                handler.removeObject(tempObject);
            }
            if (tempObject.getId()==ID.MoveSet) {
                handler.removeObject(tempObject);
            }
            if (tempObject.getId()==ID.Notification) {
                handler.removeObject(tempObject);
            }
        }
        handler.addObject(new MainMenu(0,0,ID.MainMenu));
    }
}
