package repo.ruinspvp.factions;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class TestCommand implements CommandExecutor {

    Factions factions;

    public TestCommand(Factions factions) {
        this.factions = factions;
    }

    @Override
    public boolean onCommand(final CommandSender commandSender, Command command, String s, String[] strings) {
        final Player player = (Player) commandSender;

        missle(player);

        return false;
    }

    public void testMath(final Player player) {
        factions.getServer().getScheduler().scheduleSyncRepeatingTask(factions, new Runnable() {
            double t = 0;

            public void run() {
                t = t + 0.5;
                Location loc = player.getLocation();
                Vector direction = loc.getDirection().normalize();
                double x = Math.sin(t) * 1.5;
                double y = player.getEyeHeight();
                double z = Math.cos(t) * 1.5;
                loc.add(x, y, z);
                playParticles(EnumParticle.FLAME, loc, 2);
                loc.subtract(x, y, z);
            }
        }, 0L, 0L);
    }

    public void missle(final Player player) {
        new BukkitRunnable() {
            double t = 0;

            public void run() {
                t = t + 0.5;
                Location loc = player.getLocation();
                Vector direction = loc.getDirection().normalize();
                double x = direction.getX() * t;
                double y = direction.getY() * t + 1.5;
                double z = direction.getZ() * t;
                loc.add(x, y, z);
                playParticles(EnumParticle.REDSTONE, loc, 10);

                for (Entity e : loc.getChunk().getEntities()) {
                    if (e.getLocation().distance(loc) < 3.0) {
                        if (e != player) {
                            LivingEntity livingEntity = (LivingEntity) e;
                            livingEntity.damage(5);
                            this.cancel();
                        }
                    }
                }

                loc.subtract(x, y, z);

                if (t > 20) {
                    loc.add(x, y, z);
                    FireworkEffect effect = FireworkEffect.builder().trail(false).flicker(false).withColor(Color.RED).withFade(Color.ORANGE).with(FireworkEffect.Type.BALL_LARGE).build();
                    final Firework fw = loc.getWorld().spawn(loc, Firework.class);
                    FireworkMeta meta = fw.getFireworkMeta();
                    meta.addEffect(effect);
                    meta.setPower(0);
                    fw.setFireworkMeta(meta);
                    new BukkitRunnable() {
                        public void run() {
                            fw.detonate();
                        }
                    }.runTaskLater(factions, 2);
                    this.cancel();
                }
            }
        }.runTaskTimer(factions, 0, 0);
    }

    public void playParticles(EnumParticle particle, Location location, int amt) {
        PacketPlayOutWorldParticles particles = new PacketPlayOutWorldParticles(particle, false,
                (float) location.getX(), (float) location.getY(), (float) location.getZ(), 0, 0, 0, 0, amt);
        for (Player player : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(particles);
        }
    }

}
