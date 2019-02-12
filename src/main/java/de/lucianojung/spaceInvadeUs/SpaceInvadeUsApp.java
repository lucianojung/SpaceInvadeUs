package de.lucianojung.spaceInvadeUs;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.settings.GameSettings;
import de.lucianojung.Entities.BulletControl;
import de.lucianojung.Entities.InvaderControl;
import de.lucianojung.Entities.EntityType;
import de.lucianojung.Entities.ShipControl;
import javafx.geometry.Dimension2D;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class SpaceInvadeUsApp extends GameApplication {

    private static final Dimension2D WINDOWSIZE = new Dimension2D(960, 960);
    private static final String GAMETITLE = "Space Invade Us";
    private static final String GAMEVERSION = "0.1.0";
    private GameStatus gameStatus = GameStatus.START;
    private Entity shipBullet;
    private Music music;

    private enum GameStatus {
        START, RUN, WON, LOST
    }


    private Entity ship;
    private List<List<Entity>> invaders;

    //+++++++++++++++++++++++++++++++
    //init Settings                 +
    //+++++++++++++++++++++++++++++++

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setConfigClass(Config.class);

        settings.setWidth((int) WINDOWSIZE.getWidth());
        settings.setHeight((int) WINDOWSIZE.getHeight());
        settings.setTitle(GAMETITLE);
        settings.setVersion(GAMEVERSION);
        settings.setCloseConfirmation(false);

        invaders = new ArrayList<>();
    }

    //+++++++++++++++++++++++++++++++
    //init Game                     +
    //+++++++++++++++++++++++++++++++

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new SpaceInvadeUsFactory());
        getGameWorld().setLevelFromMap("SpaceInvadeUs.json");

        ship = getGameWorld().spawn("Ship");
        shipBullet = new Entity();

        startGame();
    }

    private void initInvaders(){
        int invadersAmount = ((Config) FXGL.getGameConfig()).getInvadersAmount();
        for (int i = 0; i < 5; i++) {
            invaders.add(new ArrayList<>());
            for (int j = 0; j < invadersAmount / 5; j++) {
                Entity currentInvader = null;
                if (i == 0)
                    currentInvader = getGameWorld().spawn("InvaderC");
                if (i == 1)
                    currentInvader = getGameWorld().spawn("InvaderB");
                if (i == 2)
                    currentInvader = getGameWorld().spawn("InvaderB");
                if (i == 3)
                    currentInvader = getGameWorld().spawn("InvaderA");
                if (i == 4)
                    currentInvader = getGameWorld().spawn("InvaderA");

                invaders.get(i).add(currentInvader);
                currentInvader.setPosition(64 * j, 64 * i);
                currentInvader.getComponent(InvaderControl.class).moveRight();
            }
        }

        getMasterTimer().runAtInterval(() -> {
            checkInvaderDirection();
            for (Entity invader : getAllInvaders()){
                if (invader.getY() < 850) continue;
                endGame(false);
            }
        }, Duration.millis(50));

        getMasterTimer().runAtInterval(() -> {
            int number = FXGLMath.random(getAllInvaders().size()-1);
            getAllInvaders().get(number).getComponent(InvaderControl.class).fireBullet();
        }, Duration.millis(((Config) getGameConfig()).getInvaderBulletAmount()));
    }

    private void checkInvaderDirection() {
        for (Entity invader : getAllInvaders()) {
            int xValue = ((int) invader.getX());
            if (xValue + 64 <= WINDOWSIZE.getWidth()) {
                if (xValue > 0) continue;
                System.out.println("Move Right because of newValue: " + xValue);
                for (Entity innerInvader : getAllInvaders()) {
                    innerInvader.getComponent(InvaderControl.class).moveDown();
                    innerInvader.getComponent(InvaderControl.class).moveRight();
                }
                return;
            }
            System.out.println("Move Left because of newValue: " + xValue);
            for (Entity innerInvader : getAllInvaders()) {
                innerInvader.getComponent(InvaderControl.class).moveDown();
                innerInvader.getComponent(InvaderControl.class).moveLeft();
            }
            return;
        }
    }

    public List<Entity> getAllInvaders(){
        List<Entity> entities = new ArrayList<>();
        for (List<Entity> invaderRows : invaders) {
            for (Entity invader : invaderRows) {
                if (invader.isActive())//todo or is Living if this later doesnt work
                    entities.add(invader);
            }
        }
        return entities;
    }

    //+++++++++++++++++++++++++++++++
    //init Input                    +
    //+++++++++++++++++++++++++++++++

    @Override
    public void initInput(){
        Input input = getInput();



        input.addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                ship.getComponent(ShipControl.class).moveLeft();
            }
        }, KeyCode.A);
        input.addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                ship.getComponent(ShipControl.class).moveRight();
            }
        }, KeyCode.D);
        input.addAction(new UserAction("Fire Ship Bullet") {
            @Override
            protected void onAction() {
                if (shipBullet.isActive()) return;

                shipBullet = getGameWorld().spawn("ShipBullet");
                shipBullet.setX(ship.getX() + 27);
                shipBullet.setY(ship.getY() - 15);
                System.out.println("Bullet fired");

                getAudioPlayer().playSound("ShipBullet.wav");
            }
        }, KeyCode.SPACE);
    }

    //+++++++++++++++++++++++++++++++
    //init Physics                  +
    //+++++++++++++++++++++++++++++++

    @Override
    protected void initPhysics(){
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.SHIPBULLET, EntityType.INVADERA){
            @Override
            protected void onCollisionBegin(Entity bullet, Entity invaderA){
                bulletCollisionInvader(bullet, invaderA);
            }
        });
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.SHIPBULLET, EntityType.INVADERB){
            @Override
            protected void onCollisionBegin(Entity bullet, Entity invaderB){
                bulletCollisionInvader(bullet, invaderB);
            }
        });
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.SHIPBULLET, EntityType.INVADERC){
            @Override
            protected void onCollisionBegin(Entity bullet, Entity invaderC){
                bulletCollisionInvader(bullet, invaderC);
            }
        });
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.INVADERBULLET, EntityType.SHIP){
            @Override
            protected void onCollisionBegin(Entity bullet, Entity ship){
                bullet.removeFromWorld();
                ship.getComponent(ShipControl.class).loseLive();

                if (ship.getComponent(ShipControl.class).getLives() > 0) return;
                endGame(false);
                ship.removeFromWorld(); //todo want to remove ship?
            }
        });
    }

    private void bulletCollisionInvader(Entity bullet, Entity invader) {
        bullet.removeFromWorld();
        invader.removeFromWorld();
        getAudioPlayer().playSound("InvaderHit.wav");
        if (getAllInvaders().size() == 0)
            endGame(true);
    }

    //+++++++++++++++++++++++++++++++
    //change Game Status            +
    //+++++++++++++++++++++++++++++++

    private void startGame(){
        gameStatus = GameStatus.RUN;
        initInvaders();
        music = getAudioPlayer().loopBGM("SpaceInvadeUs.mp3");
        getAudioPlayer().setGlobalMusicVolume(0.25);
    }

    private void endGame(boolean win) {
        getAudioPlayer().stopMusic(music);
        //todo play Music if won or lost
        if (win){
            gameStatus = GameStatus.WON;
            System.out.println("Won Game");
        } else {
            gameStatus = GameStatus.LOST;
            System.out.println("Lost game");
        }
    }

    //+++++++++++++++++++++++++++++++
    //main                          +
    //+++++++++++++++++++++++++++++++

    public static void main(String[] args) {
        launch(args);
    }
}
