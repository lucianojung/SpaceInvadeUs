package de.lucianojung.spaceInvadeUs;

public class Config {
    private double playerSpeed;
    private double playerBulletSpeed;
    private int playerLives;

    private double enemySpeed;
    private double enemyBulletSpeed;

    //+++++++++++++++++++++++++++++++
    //getter                        +
    //+++++++++++++++++++++++++++++++

    public double getPlayerSpeed() {
        return playerSpeed;
    }

    public double getPlayerBulletSpeed() {
        return playerBulletSpeed;
    }

    public int getPlayerLives() {
        return playerLives;
    }

    public double getEnemySpeed() {
        return enemySpeed;
    }

    public double getEnemyBulletSpeed() {
        return enemyBulletSpeed;
    }
}
