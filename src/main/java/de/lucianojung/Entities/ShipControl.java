package de.lucianojung.Entities;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import de.lucianojung.spaceInvadeUs.Config;
import javafx.scene.image.Image;

public class ShipControl extends Component {

    int lives;

    public ShipControl() {
        lives = ((Config) FXGL.getGameConfig()).getPlayerLives();
    }

    @Override
    public void onAdded(){

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

    public void loseLive(){
        lives--;
        FXGL.getAudioPlayer().playSound("ShipHit.wav");
    }

    public int getLives() {
        return lives;
    }
}
