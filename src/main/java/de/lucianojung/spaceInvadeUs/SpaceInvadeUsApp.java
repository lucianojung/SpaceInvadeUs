package de.lucianojung.spaceInvadeUs;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.settings.GameSettings;
import de.lucianojung.Entities.InvaderControl;
import de.lucianojung.Entities.EntityType;
import javafx.geometry.Dimension2D;

import java.util.ArrayList;
import java.util.List;

public class SpaceInvadeUsApp extends GameApplication {

    private static final Dimension2D WINDOWSIZE = new Dimension2D(960, 960);
    private static final String GAMETITLE = "Space Invade Us";
    private static final String GAMEVERSION = "0.1.0";
    private EntityType entityType = EntityType.NONE;
    private SpaceInvadeUsFactory factory;


    //private Ship ship;
    private List<Entity> invaders;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setConfigClass(Config.class);

        settings.setWidth((int) WINDOWSIZE.getWidth());
        settings.setHeight((int) WINDOWSIZE.getHeight());
        settings.setTitle(GAMETITLE);
        settings.setVersion(GAMEVERSION);
        settings.setCloseConfirmation(false);


    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new SpaceInvadeUsFactory());
        getGameWorld().setLevelFromMap("SpaceInvadeUs.json");

        invaders = new ArrayList<Entity>();
        invaders.add(getGameWorld().spawn("Invader"));
        invaders.get(0).getComponent(InvaderControl.class).moveRight();

        Entity entity = Entities.builder()
                .at(200, 200)
                .with(new InvaderControl(EntityType.INVADERA))
                .buildAndAttach();
        entity.getComponent(InvaderControl.class).moveLeft();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
