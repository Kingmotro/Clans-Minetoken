package repo.minetoken.clans.structure.disguise;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import repo.minetoken.clans.Clans;
import repo.minetoken.clans.structure.disguise.packet.PacketEntity;
import repo.minetoken.clans.utilities.UtilHologram;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class DisguiseManager implements Listener {

    public JavaPlugin plugin;
    private PacketEntity packetEntity;
    private HashMap<UUID, DisguisePlayer> disguised = new HashMap<>();

    public DisguiseManager(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void disguisePlayerToAll(Player player) {
        Location loc = player.getLocation();
        WorldServer s = ((CraftWorld) loc.getWorld()).getHandle();

        EntityCreeper creeper = new EntityCreeper(s);

        creeper.setPosition(loc.getX(), loc.getY(), loc.getZ());
        creeper.d(player.getEntityId());
        creeper.setCustomName(player.getName());
        creeper.setCustomNameVisible(true);
        LivingEntity ent = (LivingEntity) creeper.getBukkitEntity();
        ent.setRemoveWhenFarAway(false);

        packetEntity = new PacketEntity(player, creeper);
        disguised.put(player.getUniqueId(), new DisguisePlayer(packetEntity, player));
        packetEntity.sendToAll();
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        disguised.remove(player.getUniqueId());
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        //TODO: Hologram to follow player disguised.
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        Set<UUID> players = disguised.keySet();

        for (final UUID uuid : players) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    disguised.get(uuid).getPacketEntity().destroyPlayer(player);
                    disguised.get(uuid).getPacketEntity().sendToPlayer(player);
                }
            }, 10L);
        }
    }

}
