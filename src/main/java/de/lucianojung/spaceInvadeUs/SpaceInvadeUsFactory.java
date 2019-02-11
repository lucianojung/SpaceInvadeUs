package de.lucianojung.spaceInvadeUs;

import com.almasb.fxgl.entity.*;
import de.lucianojung.Entities.BulletControl;
import de.lucianojung.Entities.InvaderControl;
import de.lucianojung.Entities.EntityType;
import de.lucianojung.Entities.ShipControl;

public class SpaceInvadeUsFactory implements EntityFactory {

    @Spawns("InvaderA")
    public Entity newInvaderA(SpawnData data){
        return Entities.builder()
                .with(new InvaderControl(EntityType.INVADERA))
                .build();
    }

    @Spawns("InvaderB")
    public Entity newInvaderB(SpawnData data){
        return Entities.builder()
                .with(new InvaderControl(EntityType.INVADERB))
                .build();
    }

    @Spawns("InvaderC")
    public Entity newInvaderC(SpawnData data){
        return Entities.builder()
                .with(new InvaderControl(EntityType.INVADERC))
                .build();
    }

    @Spawns("Ship")
    public Entity newShip(SpawnData data){
        return Entities.builder()
                .at(480, 860)
                .with(new ShipControl(EntityType.SHIP))
                .build();
    }

    @Spawns("Player")
    public Entity newPlayer(SpawnData data){
        return newShip(data);
    }

    @Spawns("Bullet")
    public Entity newBullet(SpawnData data){
        return Entities.builder()
                .with(new BulletControl(EntityType.BULLET))
                .build();
    }
}
