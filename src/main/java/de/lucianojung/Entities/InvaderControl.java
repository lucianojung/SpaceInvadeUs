package de.lucianojung.Entities;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import de.lucianojung.spaceInvadeUs.Config;
import javafx.util.Duration;

public class InvaderControl extends Component {
    private double speed;

    private AnimationChannel animFly;
    private AnimatedTexture texture;

    public InvaderControl() {
        this.speed = ((Config) FXGL.getGameConfig()).getInvaderSpeed();


    }

    @Override
    public void onAdded() {
        if (EntityType.INVADERA.equals(entity.getType()))
            animFly = new AnimationChannel("InvaderA.png", 2, 64, 64, Duration.seconds(1), 0, 1);
        else if (EntityType.INVADERB.equals(entity.getType()))
            animFly = new AnimationChannel("InvaderB.png", 2, 64, 64, Duration.seconds(1), 0, 1);
        else if (EntityType.INVADERC.equals(entity.getType()))
            animFly = new AnimationChannel("InvaderC.png", 2, 64, 64, Duration.seconds(1), 0, 1);

        texture = new AnimatedTexture(animFly);

        entity.setViewWithBBox(texture);
        texture.loopAnimationChannel(animFly);
    }

    @Override
    public void onUpdate(double tpf) {
        entity.translateX(speed);

        //Example:
        /*entity.translateX(speed * tpf);

        if (speed != 0) {

            if (texture.getAnimationChannel() == animIdle) {
                texture.loopAnimationChannel(animWalk);
            }

            speed = (int) (speed * 0.9);

            if (FXGLMath.abs(speed) < 1) {
                speed = 0;
                texture.loopAnimationChannel(animIdle);
            }
        }*/
    }

    public void moveRight() {
        this.speed = Math.abs(speed);
        getEntity().setScaleX(1);
    }

    public void moveLeft() {
        this.speed = -1 * Math.abs(speed);
        getEntity().setScaleX(-1);
    }

    public void moveDown() {
        this.speed = Math.abs(speed);
        entity.translateY(10);
    }

    public void fireBullet() {
        Entity invaderBullet = FXGL.getGameWorld().spawn("InvaderBullet");
        invaderBullet.setX(entity.getX() + 27);
        invaderBullet.setY(entity.getY() - 15);
        FXGL.getAudioPlayer().playSound("InvaderBullet.wav");
    }
}
