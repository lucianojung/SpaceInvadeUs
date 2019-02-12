package de.lucianojung.spaceInvadeUs;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.settings.GameSettings;
import de.lucianojung.Entities.BulletControl;
import de.lucianojung.Entities.InvaderControl;
import de.lucianojung.Entities.EntityType;
import de.lucianojung.Entities.ShipControl;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Dimension2D;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class SpaceInvadeUsApp extends GameApplication {

    private static final Dimension2D WINDOWSIZE = new Dimension2D(960, 960);
    private static final String GAMETITLE = "Space Invade Us";
    private static final String GAMEVERSION = "0.1.0";
    private EntityType entityType = EntityType.NONE;
    private SpaceInvadeUsFactory factory;


    private Entity ship;
    private List<List<Entity>> invaders;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setConfigClass(Config.class);

        settings.setWidth((int) WINDOWSIZE.getWidth());
        settings.setHeight((int) WINDOWSIZE.getHeight());
        settings.setTitle(GAMETITLE);
        settings.setVersion(GAMEVERSION);
        settings.setCloseConfirmation(false);

        invaders = new ArrayList<List<Entity>>();
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new SpaceInvadeUsFactory());
        getGameWorld().setLevelFromMap("SpaceInvadeUs.json");

        int invadersAmount = ((Config) FXGL.getGameConfig()).getInvadersAmount();
        for (int i = 0; i < 5; i++) {
            invaders.add(new ArrayList<Entity>());
            for (int j = 0; j < invadersAmount / 5; j++) {
                Entity currentInvader = null;
                if (i == 0)
                    currentInvader = spawnInvader(EntityType.INVADERC);
                if (i == 1)
                    currentInvader = spawnInvader(EntityType.INVADERB);
                if (i == 2)
                    currentInvader = spawnInvader(EntityType.INVADERB);
                if (i == 3)
                    currentInvader = spawnInvader(EntityType.INVADERA);
                if (i == 4)
                    currentInvader = spawnInvader(EntityType.INVADERA);

                invaders.get(i).add(currentInvader);
                currentInvader.setPosition(64 * j, 64 * i);
                currentInvader.getComponent(InvaderControl.class).moveRight();
            }
        }
        getMasterTimer().runAtInterval(() -> {
            System.out.println("check");
            checkInvaderDirection();
        }, Duration.millis(50));

        ship = getGameWorld().spawn("Ship");
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

    private Entity spawnInvader(EntityType entityType) {
        if (entityType == EntityType.INVADERA)
            return getGameWorld().spawn("InvaderA");
        if (entityType == EntityType.INVADERB)
            return getGameWorld().spawn("InvaderB");
        if (entityType == EntityType.INVADERC)
            return getGameWorld().spawn("InvaderC");
        return null;

    }

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
        input.addAction(new UserAction("Fire Bullet") {
            @Override
            protected void onActionBegin() {
                //todo code => fire Bullet
                Entity bullet = getGameWorld().spawn("Bullet");
                bullet.setX(ship.getX() + 27);
                bullet.setY(ship.getY());
                System.out.println("Bullet fired");
                bullet.getComponent(BulletControl.class).moveUp();
            }
        }, KeyCode.SPACE);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
