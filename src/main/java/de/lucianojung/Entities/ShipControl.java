package de.lucianojung.Entities;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import de.lucianojung.spaceInvadeUs.Config;
import javafx.scene.image.Image;

public class ShipControl extends Component {

    private Texture texture;

    public ShipControl(EntityType entityType) {
        if (!EntityType.SHIP.equals(entityType)) return;

        texture = FXGL.getAssetLoader().loadTexture("Ship.png");

    }

    @Override
    public void onAdded(){
        entity.setView(texture);
    }

    @Override
    public void onUpdate(double tpf) {

    }

    public void moveRight(){
        if (entity.getX() + 64 < ((Config) FXGL.getGameConfig()).getGameWidth())
            entity.translateX(((Config) FXGL.getGameConfig()).getPlayerSpeed());
    }
    public void moveLeft(){
        if (entity.getX() > 0)
            entity.translateX(-((Config) FXGL.getGameConfig()).getPlayerSpeed());
    }

}
