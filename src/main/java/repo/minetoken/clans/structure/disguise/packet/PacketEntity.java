package repo.minetoken.clans.structure.disguise.packet;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class PacketEntity {

    private Player player;
    private EntityLiving entityLiving;

    public PacketEntity(Player player, EntityLiving entityLiving) {
        this.player = player;
        this.entityLiving = entityLiving;
    }

    public void sendToAll(EntityLiving entityLiving) {
        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(entityLiving);
        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (!(pl.getName().equalsIgnoreCase(this.player.getName()))) {
                ((CraftPlayer) pl).getHandle().playerConnection.sendPacket(packet);
            }
        }
    }

    public void sendToAll() {
        destroy();
        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(entityLiving);
        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (!(pl.getName().equalsIgnoreCase(this.player.getName()))) {
                ((CraftPlayer) pl).getHandle().playerConnection.sendPacket(packet);
            }
        }
    }

    public void sendToPlayer(Player player) {
        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(entityLiving);
        if (!(player.getName().equalsIgnoreCase(this.player.getName()))) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        }
    }

    public void destroyPlayer(Player player) {
        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(player.getEntityId());
        if (!(player.getName().equalsIgnoreCase(this.player.getName()))) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        }
    }

    public void destroy() {
        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(player.getEntityId());
        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (!(pl.getName().equalsIgnoreCase(this.player.getName()))) {
                ((CraftPlayer) pl).getHandle().playerConnection.sendPacket(packet);
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public EntityLiving getEntityLiving() {
        return entityLiving;
    }
}
