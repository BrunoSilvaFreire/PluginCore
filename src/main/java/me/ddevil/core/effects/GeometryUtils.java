package me.ddevil.core.effects;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BRUNO II on 16/06/2016.
 */
public class GeometryUtils {
    public static List<Location> getLine(World world, Vector pos1, Vector pos2, GeometryResolution resolution) {
        return getLine(world, pos1, pos2, resolution.getResolution());
    }

    public static List<Location> getCircle(World world, Vector center, float radius, GeometryResolution resolution) {
        return getCircle(center.toLocation(world), radius, resolution.getResolution());
    }

    public static List<Location> getLine(World world, Vector pos1, Vector pos2, int totalPoints) {
        //Find out info
        Vector direction = pos2.clone().subtract(pos1);
        direction.normalize();
        double distance = pos1.distance(pos2);
        double ratio = distance / totalPoints;
        direction.multiply(ratio);

        //Find out points
        ArrayList<Location> points = new ArrayList();
        Location point = pos1.toLocation(world);
        for (int i = 0; i < totalPoints; i++) {
            point.add(direction);
            points.add(point.clone());
        }
        return points;
    }

    public static List<Location> getCircle(Location centro, float radius, int totalPoints) {
        World world = centro.getWorld();
        double ratio = (2 * Math.PI) / totalPoints;
        ArrayList<Location> locations = new ArrayList<>();
        for (int i = 0; i < totalPoints; i++) {
            double angle = i * ratio;
            double x = centro.getX() + (radius * Math.cos(angle));
            double z = centro.getZ() + (radius * Math.sin(angle));
            locations.add(new Location(world, x, centro.getY(), z));
        }
        return locations;
    }

}
