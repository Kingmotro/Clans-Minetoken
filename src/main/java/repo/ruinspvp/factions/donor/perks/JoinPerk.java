package repo.ruinspvp.factions.donor.perks;

import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import repo.ruinspvp.factions.donor.DonorPerk;
import repo.ruinspvp.factions.structure.rank.enums.Ranks;
import repo.ruinspvp.factions.utilities.Format;

public class JoinPerk extends DonorPerk {

    public JoinPerk(JavaPlugin plugin) {
        super(plugin, "Join Perk", "ruinspvp.d1", Ranks.DONOR1);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPermission(getPermssion())) {
            return;
        }

        event.setJoinMessage("");
        Bukkit.broadcastMessage(Format.main("Join", ChatColor.BOLD + player.getName() + " has joined the server."));

        final Location location = player.getLocation();

        new BukkitRunnable() {
            public void run() {
                FireworkEffect effect = FireworkEffect.builder().trail(false).flicker(false).withColor(Color.WHITE).withFade(Color.YELLOW).with(FireworkEffect.Type.BALL_LARGE).build();
                final Firework fw = location.getWorld().spawn(location, Firework.class);
                FireworkMeta meta = fw.getFireworkMeta();
                meta.addEffect(effect);
                meta.setPower(0);
                fw.setFireworkMeta(meta);
                new BukkitRunnable() {
                    public void run() {
                        fw.detonate();
                    }
                }.runTaskLater(getPlugin(), 2);
            }
        }.runTaskLater(getPlugin(), 10);
    }

}