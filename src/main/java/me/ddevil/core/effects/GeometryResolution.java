package me.ddevil.core.effects;

/**
 * Created by BRUNO II on 16/06/2016.
 */
public enum GeometryResolution {
    LOWEST(10),
    LOW(25),
    MEDIUM(50),
    HIGH(100),
    ULTRA(150);

    private final int resolution;

    GeometryResolution(int points) {
        this.resolution = points;
    }

    public int getResolution() {
        return resolution;
    }
}
