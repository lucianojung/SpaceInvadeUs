# SpaceInvadeUs
A simple **Space Invader Game** to start Game Development using [FXGL](https://github.com/AlmasB/FXGL)
> documented Code for better Understanding

Getting Started
-
coming soon...

Story
-
```
You have a Ship                           (That only can move Left and Right)
And there are many Invaders               (Who are not able to move downwards either)
Protect the Planet                        (That exists outside the Application)
Fire Bullets to destroy the Enemies       (So they can get Angry and Faster)
Avoid the Enemies Bullets                 (Because you dont have any Bases)
Each time you win they shoot more Bullets (Because of an funny Bug)
```

Features
-
> see Todo.txt
- Player:
    - Control a Ship with A and D
    - fire Bullet with SPACE
    - show Lives in the TopLeft-Corner
- Invaders:
    - Animated Textures
    - Moving Left, Right and DOWN
    - shoot Bullets from random Invader
- Detection
    - Bullet hits Invader
    - Bullet hits Ship
- config.kv for Global Game Settings (src/main/resources/assets/kv)
- Restart Finished Game (-> make Game harder if won)

Potential of improvement
- 
- better Balancing (-> simply change config.kv)
- gain Points for shooting Invaders
- set Bases to Protect Ship (like in the Original Space Invader)
- right Level-Design

Authors
-
- Luciano Jung - Initial work - [lucianojung](https://github.com/lucianojung)

Acknowledgements
- 
- Thanks for Almas Baimagambetov - [AlmasB](https://github.com/AlmasB) and his simple Game Engine [FXGL](https://github.com/AlmasB/FXGL) for JavaFX
- Using [IntelliJ](https://www.jetbrains.com/idea/) with Maven Plugin for Programming
- Using [TileMapEditor](https://www.mapeditor.org/) for Background (.json-File)
- Using [UMLet](https://www.umlet.com/) for Diagramms in Folder UML