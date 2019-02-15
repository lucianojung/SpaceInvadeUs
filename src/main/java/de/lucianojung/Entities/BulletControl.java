package de.lucianojung.Entities;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.component.Component;
import de.lucianojung.spaceInvadeUs.Config;

public class BulletControl extends Component {
    /*
    * Controller Class for Bullets
    * Differentiate between Ship and Invader Bullet
    * has an int speed (got from config.kv)
    * can Move Up (ShipBullet) or Down (InvaderBullet)
    */
    private double speed;

    public BulletControl() {
        this.speed = ((Config) FXGL.getGameConfig()).getBulletSpeed();
    }

    @Override
    public void onAdded(){
        if (entity.getType() == EntityType.SHIPBULLET)
            moveUp();
        else if (entity.getType() == EntityType.INVADERBULLET)
            moveDown();
    }

    @Override
    public void onUpdate(double tpf) {
        entity.translateY(speed);
        if (entity.getY() < 0)
            entity.removeFromWorld();
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
