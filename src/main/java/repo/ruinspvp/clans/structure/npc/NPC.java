package repo.ruinspvp.clans.structure.npc;

import net.minecraft.server.v1_8_R3.EntityCreature;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftCreature;
import org.bukkit.entity.Entity;

public class NPC {

    Entity entity;
    String name;
    Location location;
    int radius;
    boolean returning;

    public NPC(Entity entity, String name, Location location, int radius) {
        this.entity = entity;
        this.name = name;
        this.location = location;
        this.radius = radius;
    }

    public Entity getEntity() {
        return entity;
    }

    public String getName() {
        return name;
    }

    public int getRadius() {
        return radius;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isReturning() {
        return returning;
    }

    public boolean isInRadius() {
        Location entityLocation = this.entity.getLocation();
        return Math.abs(entityLocation.getBlockX() - this.location.getBlockX()) + Math.abs(entityLocation.getBlockY() - this.location.getBlockY()) + Math.abs(entityLocation.getBlockZ() - this.location.getBlockZ()) <= this.radius;
    }

    public void returnWithinRaidus() {
        EntityCreature ec = ((CraftCreature)this.entity).getHandle();
        ec.getNavigation().a(this.location.getX(), this.location.getY(), this.location.getZ(), 0.800000011920929D);

        this.returning = true;
    }

    public void clearGoals() {
        this.returning = false;

        Location entityLocation = this.entity.getLocation();
        EntityCreature ec = ((CraftCreature) this.entity).getHandle();
        ec.getNavigation().a(entityLocation.getX(), entityLocation.getY(), entityLocation.getZ(), 0.800000011920929D);
    }
}
