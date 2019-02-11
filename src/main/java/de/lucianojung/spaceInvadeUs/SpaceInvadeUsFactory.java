package de.lucianojung.spaceInvadeUs;

import com.almasb.fxgl.entity.*;
import de.lucianojung.Entities.InvaderControl;
import de.lucianojung.Entities.EntityType;

public class SpaceInvadeUsFactory implements EntityFactory {

    @Spawns("Invader")
    public Entity newEnemy(SpawnData data){
        return Entities.builder()
                .at(111, 73)
                .with(new InvaderControl(EntityType.INVADERA))
                .build();
    }
}
