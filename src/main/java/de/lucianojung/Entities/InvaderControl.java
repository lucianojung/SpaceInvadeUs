package de.lucianojung.Entities;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import de.lucianojung.spaceInvadeUs.SpaceInvadeUsApp;
import javafx.util.Duration;

public class InvaderControl extends Component {
    private double speed;
    private double bulletSpeed;

    private AnimationChannel animFly;
    private AnimatedTexture texture;

    public InvaderControl(EntityType entityType) {
        this.speed = 5.0;
        this.bulletSpeed = 1.0;

        animFly = new AnimationChannel("InvaderA.png", 2, 64, 64, Duration.seconds(3), 0, 1);

        texture = new AnimatedTexture(animFly);
    }

    @Override
    public void onAdded(){
        entity.setView(texture);
        texture.loopAnimationChannel(animFly);
    }

    @Override
    public void onUpdate(double tpf){
        entity.translateX(speed * tpf);

        if (speed != 0){

        }

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

    public void moveRight(){
        this.speed = Math.abs(speed);
        getEntity().setScaleX(1);
    }

    public void moveLeft(){
        this.speed = -1 * Math.abs(speed);
        getEntity().setScaleX(-1);
    }
}
