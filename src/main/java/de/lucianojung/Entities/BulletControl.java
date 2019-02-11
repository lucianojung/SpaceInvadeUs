package de.lucianojung.Entities;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import de.lucianojung.spaceInvadeUs.Config;
import javafx.scene.image.Image;

public class BulletControl extends Component {
    private Texture texture;
    private double speed;

    public BulletControl(EntityType entityType) {
        if (!EntityType.BULLET.equals(entityType)) return;

        this.speed = ((Config) FXGL.getGameConfig()).getBulletSpeed();

        texture = FXGL.getAssetLoader().loadTexture("Bullet.png");

    }

    @Override
    public void onAdded(){
        entity.setView(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        entity.translateY(speed);
    }

    public void moveDown(){
        this.speed = Math.abs(speed);
        getEntity().setScaleY(1);
    }

    public void moveUp(){
        this.speed = -1 * Math.abs(speed);
        getEntity().setScaleY(-1);
    }

}
