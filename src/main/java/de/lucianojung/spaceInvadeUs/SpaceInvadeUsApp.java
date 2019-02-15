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
import com.almasb.fxgl.texture.Texture;
import de.lucianojung.Entities.InvaderControl;
import de.lucianojung.Entities.EntityType;
import de.lucianojung.Entities.ShipControl;
import javafx.geometry.Dimension2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpaceInvadeUsApp extends GameApplication{

    private static final Dimension2D WINDOWSIZE = new Dimension2D(960, 960);
    private static final String GAMETITLE = "Space Invade Us";
    private static final String GAMEVERSION = "0.1.12";
    private Entity shipBullet;
    private Music music;

    private Entity ship;
    private List<Entity> invaders;

    //+++++++++++++++++++++++++++++++
    //init Settings                 +
    //+++++++++++++++++++++++++++++++

    @Override
    protected void initSettings(GameSettings settings) {
        /*
        * set Config Class
        * set App Icon
        * set Width and Height
        * set Title and Version
        */

        settings.setConfigClass(Config.class);
        settings.setIntroEnabled(false);
        settings.setMenuEnabled(false);                                                                                 //without default Menu
        settings.setAppIcon("Icon.png");

        settings.setWidth((int) WINDOWSIZE.getWidth());
        settings.setHeight((int) WINDOWSIZE.getHeight());
        settings.setTitle(GAMETITLE);
        settings.setVersion(GAMEVERSION);
        settings.setCloseConfirmation(false);
    }

    //+++++++++++++++++++++++++++++++
    //init Game                     +
    //+++++++++++++++++++++++++++++++

    @Override
    protected void initGame() {
        /*
        * initialisations for App-Start
        * then GameStart()
        */
        getGameWorld().addEntityFactory(new SpaceInvadeUsFactory());
        getGameWorld().setLevelFromMap("SpaceInvadeUs.json");

        startGame();
    }

    private void initInvaders(){
        /*
        * create new list and iterate through 5 rows
        * get max Invaders from config
        * if-else statements for each row
        * add Invader to List and set right position
        *
        * set rnAtIntervall for:
        *   - invaders direction (so they dont get outside the frame)
        *   - random invader fires bullet
        */
        invaders = new ArrayList<>();
        int invadersAmount = ((Config) FXGL.getGameConfig()).getInvadersAmount();
        for (int i = 0; i < invadersAmount; i++) {
            Entity currentInvader;
            int j = i % 5;

            if (j == 0)
                currentInvader = getGameWorld().spawn("InvaderC");
            else if (j == 1)
                currentInvader = getGameWorld().spawn("InvaderB");
            else if (j == 2)
                currentInvader = getGameWorld().spawn("InvaderB");
            else if (j == 3)
                currentInvader = getGameWorld().spawn("InvaderA");
            else
                currentInvader = getGameWorld().spawn("InvaderA");

            invaders.add(currentInvader);
            currentInvader.setPosition(64 * (i / 5), 64 + 64 * (i % 5));     //12 * i == 64 * i/5
        }

        getMasterTimer().runAtInterval(() -> {
            /*
            * check and change direction of invaders (if one is outside the frame)
            * check if invaders are down enough to win
            */
            checkInvaderDirection();
            for (Entity invader : invaders){
                if (invader.getY() >= 850) endGame(false);
            }
        }, Duration.millis(50));

        getMasterTimer().runAtInterval(() -> {
            //let one random ship fire a Bullet each ... seconds (config: invaderbulletAmount sets bullets per minute)
            int number = FXGLMath.random(invaders.size()-1);
            invaders.get(number).getComponent(InvaderControl.class).fireBullet();
        }, Duration.millis(((Config) getGameConfig()).getInvaderBulletAmount()));
    }

    private void checkInvaderDirection() {
        /*
        * set boolean direction Change true if an invader is outside the frame
        * if (directionChnage)
        *   move each invader first down,
        *   than change direction
        */
        boolean directionChange = false;
        for (Entity invader : invaders) {
            if (!invader.getComponent(InvaderControl.class).checkDirection()) continue;
            directionChange = true;
            break;
        }
        if (!directionChange) return;

        for (Entity invader : invaders) {
            invader.getComponent(InvaderControl.class).moveDown();
            invader.getComponent(InvaderControl.class).changeDirection();
        }
    }

    //+++++++++++++++++++++++++++++++
    //init Input                    +
    //+++++++++++++++++++++++++++++++

    @Override
    public void initInput(){
        /*
        * input Listener for Ship Control:
        *   - Move Left with A
        *   - Move Right wth D
        *   - fire Bullet with SPACE (cant fire if last Bullet is Active)
        */
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
                shipBullet = ship.getComponent(ShipControl.class).fireBullet();
            }
        }, KeyCode.SPACE);
    }

    //+++++++++++++++++++++++++++++++
    //init UI & Vars                +
    //+++++++++++++++++++++++++++++++

    @Override
    protected void initUI(){
        /*
        * UI's:
        *   - Text lives (Show Lives of Ship (integer))
        *   - Texture heart (show Image of an Heart)
        */
        Text textLives = new Text();
        textLives.setTranslateX(70);
        textLives.setTranslateY(50);
        textLives.setFill(Color.LIGHTGREEN);
        textLives.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
        textLives.textProperty().bind(getGameState().intProperty("shipLives").asString());
        getGameScene().addUINode(textLives);

        Texture heart = getAssetLoader().loadTexture("Heart.png");
        heart.setTranslateX(25);
        heart.setTranslateY(25);
        getGameScene().addUINode(heart);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars){
        //init State "shipLives" with 0 (add Lives in Game Start)
        vars.put("shipLives", 0);
    }

    //+++++++++++++++++++++++++++++++
    //init Physics                  +
    //+++++++++++++++++++++++++++++++

    @Override
    protected void initPhysics(){
        /*
        * Collision Detectors-Listener for:
        *   - ShipBullet -> InvaderA, -B or -C
        *   - InvaderBullet -> Ship
        */
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
                /*
                * remove Bullet
                * let ship loose a Live
                *   -> returns true if ship Lives == 0 -> Loose Game
                */
                bullet.removeFromWorld();
                if (looseLive())
                    endGame(false);
            }
        });
    }

    private boolean looseLive(){
        /*
         * update UI-ShipLives
         * update ship.lives
         */
        getGameState().increment("shipLives", -1);
        ship.getComponent(ShipControl.class).looseLive();
        return (ship.getComponent(ShipControl.class).getLives() == 0);
    }

    private void bulletCollisionInvader(Entity bullet, Entity shotInvader) {
        /*
         * remove Bullet
         * remove shotInvader from GameWorld and List
         * play Hit Invader Sound
         * look if Game win
         * else: tell every invader (so they can get angrier and speed up)
         */
        bullet.removeFromWorld();
        shotInvader.removeFromWorld();
        invaders.remove(shotInvader);
        getAudioPlayer().playSound("InvaderHit.wav");
        if (invaders.size() == 0)
            endGame(true);
        else
            for (Entity invader : invaders){
                invader.getComponent(InvaderControl.class).looseInvader();
            }
    }

    //+++++++++++++++++++++++++++++++
    //change Game Status            +
    //+++++++++++++++++++++++++++++++

    private void startGame(){
        /*
        * spawn Ship and set Lives
        * create Ship Bullet
        * init Invaders()
        * play Music and reduce Volume
        */
        ship = getGameWorld().spawn("Ship");
        getGameState().setValue("shipLives", ((Config) getGameConfig()).getPlayerLives());

        shipBullet = new Entity();
        initInvaders();

        music = getAudioPlayer().loopBGM("SpaceInvadeUs.mp3");
        getAudioPlayer().setGlobalMusicVolume(0.1);
        getAudioPlayer().setGlobalSoundVolume(0.1);
    }

    private void endGame(boolean win) {
        /*
        * stop Music
        * show Message
        * if loose DONT increase bulletAmount
        * restart Game()
        */
        getAudioPlayer().stopMusic(music);
        //todo play Music if won or lost
        ship.removeFromWorld();
        FXGL.getDisplay().showMessageBox((win) ? "You Win!" : "You Loose!");

        if (!win){
            for (Entity invader : invaders){
            invader.removeFromWorld();
            }
            getMasterTimer().clear();
        }
        startGame();
    }

    //+++++++++++++++++++++++++++++++
    //main                          +
    //+++++++++++++++++++++++++++++++

    public static void main(String[] args) {
        launch(args);
    }
}
