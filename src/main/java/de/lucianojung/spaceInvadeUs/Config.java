package de.lucianojung.spaceInvadeUs;

public class Config {
    private int gameWidth;
    private int gameHeight;

    private double playerSpeed;
    private int playerLives;

    private int invadersAmount;
    private double invaderSpeed;

    private double bulletSpeed;

    //+++++++++++++++++++++++++++++++
    //getter                        +
    //+++++++++++++++++++++++++++++++

    public int getGameWidth() {
        return gameWidth;
    }

    public int getGameHeight() {
        return gameHeight;
    }

    public double getPlayerSpeed() {
        return playerSpeed;
    }

    public int getPlayerLives() {
        return playerLives;
    }

    public int getInvadersAmount() {
        return invadersAmount;
    }

    public double getInvaderSpeed() {
        return invaderSpeed;
    }

    public double getBulletSpeed() {
        return bulletSpeed;
    }
}
