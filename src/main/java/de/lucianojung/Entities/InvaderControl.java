package de.lucianojung.Entities;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import de.lucianojung.spaceInvadeUs.Config;
import javafx.util.Duration;

public class InvaderControl extends Component {
    /*
    * Controller Class for Invaders
    * Differentiate between InvaderTypes (A, B, C)
    * got a Direction to Move (enum)
    *   -> can Move Left, Right and Down
    *   -> can check Direction
    *   -> can change Direction
    * got a texture (animated) and an animation
    *   -> textures in resources/assets/textures -> InvaderA, -B, -C
    * can fire Bullet
    * speed up if loose Invader()
    */

    private double speed;

    private AnimationChannel animFly;
    private AnimatedTexture texture;
    private Direction direction;

    enum Direction {
        LEFT, RIGHT
    }

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

        moveRight();
    }

    @Override
    public void onUpdate(double tpf) {
        entity.translateX(speed);
    }

    public void moveRight() {
        direction = Direction.RIGHT;
        this.speed = Math.abs(speed);
        getEntity().setScaleX(1);
    }

    public void moveLeft() {
        direction = Direction.LEFT;
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

    public void looseInvader(){
        speed = speed * ((Config) FXGL.getGameConfig()).getInvaderSpeedUp();
    }

    public boolean checkDirection(){
        if (direction == Direction.RIGHT)
            return entity.getX() + 64 >= ((Config) FXGL.getGameConfig()).getGameWidth();
        if (direction == Direction.LEFT)
            return entity.getX() <= 0;
        return false;
    }

    public void changeDirection(){
        if (direction == Direction.RIGHT)
            moveLeft();
        else
            moveRight();
    }
}
