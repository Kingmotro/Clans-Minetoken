package repo.minetoken.clans.structure.disguise.packet;

import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketEntity {

    public Player player;
    public PacketPlayOutSpawnEntityLiving packet;
    public EntityLiving entityLiving;

    public PacketEntity(Player player, net.minecraft.server.v1_8_R3.EntityLiving entityLiving) {
        this.player = player;
        this.entityLiving = entityLiving;
        this.packet = new PacketPlayOutSpawnEntityLiving(entityLiving);
    }

    public void send() {
        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (!(pl.getName().equalsIgnoreCase(this.player.getName()))) {
                ((CraftPlayer) pl).getHandle().playerConnection.sendPacket(packet);
            }
        }
    }

    public void destroy() {
        PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(player.getEntityId());
        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (!(pl.getName().equalsIgnoreCase(this.player.getName()))) {
                ((CraftPlayer) pl).getHandle().playerConnection.sendPacket(destroy);
            }
        }
    }

    public void move(Location location) {
        PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook packet = new PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook(entityLiving.getBukkitEntity().getEntityId(),
                (byte) location.getX(), (byte) location.getY(), (byte) location.getZ(), (byte) 140, (byte) -360, true);
        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (!(pl.getName().equalsIgnoreCase(this.player.getName()))) {
                ((CraftPlayer) pl).getHandle().playerConnection.sendPacket(packet);

            }
        }
    }
}
