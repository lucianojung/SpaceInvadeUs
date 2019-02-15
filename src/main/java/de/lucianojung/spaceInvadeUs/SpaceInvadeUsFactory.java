package de.lucianojung.spaceInvadeUs;

import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.entity.components.CollidableComponent;
import de.lucianojung.Entities.BulletControl;
import de.lucianojung.Entities.InvaderControl;
import de.lucianojung.Entities.EntityType;
import de.lucianojung.Entities.ShipControl;

public class SpaceInvadeUsFactory implements EntityFactory {

    /*
    * Subclass of EntityFactory used in FXGL to spawn Entities
    * spawns:
    *   - InvaderA, -B and -C
    *   - Invader Bullet
    *   - Ship
    *   - Ship Bullet
    */

    @Spawns("InvaderA")
    public Entity newInvaderA(SpawnData data){
        return Entities.builder()
                .type(EntityType.INVADERA)
                .with(new InvaderControl())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("InvaderB")
    public Entity newInvaderB(SpawnData data){
        return Entities.builder()
                .type(EntityType.INVADERB)
                .with(new InvaderControl())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("InvaderC")
    public Entity newInvaderC(SpawnData data){
        return Entities.builder()
                .type(EntityType.INVADERC)
                .with(new InvaderControl())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("InvaderBullet")
    public Entity newInvaderBullet(SpawnData data){
        return Entities.builder()
                .type(EntityType.INVADERBULLET)
                .viewFromTextureWithBBox("Bullet.png")
                .with(new BulletControl())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("Ship")
    public Entity newShip(SpawnData data){
        return Entities.builder()
                .at(480, 860)
                .type(EntityType.SHIP)
                .viewFromTextureWithBBox("Ship.png")
                .with(new ShipControl())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("ShipBullet")
    public Entity newShipBullet(SpawnData data){
        return Entities.builder()
                .type(EntityType.SHIPBULLET)
                .viewFromTextureWithBBox("Bullet.png")
                .with(new BulletControl())
                .with(new CollidableComponent(true))
                .build();
    }
}
