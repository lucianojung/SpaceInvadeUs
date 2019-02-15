package de.lucianojung.Entities;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import de.lucianojung.spaceInvadeUs.Config;
import javafx.scene.image.Image;

public class ShipControl extends Component {
    /*
    * Controller Class for Ship
    * can move Left and Right
    * can fire Bullet
    * has lives (int)
    *   -> loose Live() if shot
    * return Lives for Game (if 0 Game is lost)
    */

    int lives;

    public ShipControl() {
        lives = ((Config) FXGL.getGameConfig()).getPlayerLives();
    }

    public void moveRight(){
        if (entity.getX() + 64 < ((Config) FXGL.getGameConfig()).getGameWidth())
            entity.translateX(((Config) FXGL.getGameConfig()).getPlayerSpeed());
    }
    public void moveLeft(){
        if (entity.getX() > 0)
            entity.translateX(-((Config) FXGL.getGameConfig()).getPlayerSpeed());
    }

    public Entity fireBullet() {
        Entity shipBullet = FXGL.getGameWorld().spawn("ShipBullet");
        shipBullet.setX(entity.getX() + 27);
        shipBullet.setY(entity.getY() - 15);
        FXGL.getAudioPlayer().playSound("ShipBullet.wav");

        System.out.println("Bullet fired");
        return shipBullet;
    }

    public void looseLive(){
        lives--;
        FXGL.getAudioPlayer().playSound("ShipHit.wav");
    }

    public int getLives() {
        return lives;
    }
}
