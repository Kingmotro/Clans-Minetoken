package repo.minetoken.clans.structure.disguise;

import net.minecraft.server.v1_8_R3.EntityHorse;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import repo.minetoken.clans.structure.disguise.packet.PacketEntity;

import java.util.HashMap;
import java.util.UUID;

public class DisguiseManager implements Listener {

    public JavaPlugin plugin;
    private PacketEntity packetEntity;
    private HashMap<UUID, DisguisePlayer> disguised = new HashMap<>();

    public DisguiseManager(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void disguisePlayer(Player player) {
        EntityHorse horse = new EntityHorse((World) player.getWorld());
        packetEntity = new PacketEntity(player, horse);
        disguised.put(player.getUniqueId(), new DisguisePlayer(packetEntity, player));
        packetEntity.destroy();

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                packetEntity.send();
            }
        }, 20L);
    }

    public void updateEntity(Player player, Location location) {
        if(disguised.containsKey(player.getUniqueId())) {
            packetEntity.move(location);
        } else {
            System.out.println("Update entity was called for some reason.");
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();

        if(disguised.containsKey(player.getUniqueId())) {
            updateEntity(player, location);
        }
    }

}
