package repo.ruinspvp.factions;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import repo.ruinspvp.factions.structure.shop.am.Armory;

public class TestCommand implements CommandExecutor, Listener {

    Factions factions;

    public TestCommand(Factions factions) {
        this.factions = factions;
    }

    @Override
    public boolean onCommand(final CommandSender commandSender, Command command, String s, String[] strings) {
        final Player player = (Player) commandSender;

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

    public void missle(final Player player, final int highest) {
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
                playParticles(EnumParticle.FIREWORKS_SPARK, loc, 10);

                if(loc.getBlock().getType() != Material.AIR) {
                    FireworkEffect effect = FireworkEffect.builder().trail(false).flicker(false).withColor(Color.WHITE).withFade(Color.YELLOW).with(FireworkEffect.Type.BURST).build();
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

                for (Entity e : loc.getChunk().getEntities()) {
                    if(e instanceof Player || e instanceof Monster) {
                        if (e.getLocation().distance(loc) < 3.0) {
                            if (e != player) {
                                LivingEntity livingEntity = (LivingEntity) e;
                                livingEntity.damage(5);
                                livingEntity.setVelocity(direction.multiply(2).add(new Vector(0, 1, 0)));
                                FireworkEffect effect = FireworkEffect.builder().trail(false).flicker(false).withColor(Color.WHITE).withFade(Color.YELLOW).with(FireworkEffect.Type.BURST).build();
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
                    }
                }

                loc.subtract(x, y, z);

                if (t > highest) {
                    loc.add(x, y, z);
                    FireworkEffect effect = FireworkEffect.builder().trail(false).flicker(false).withColor(Color.WHITE).withFade(Color.YELLOW).with(FireworkEffect.Type.BURST).build();
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
        PacketPlayOutWorldParticles particles = new PacketPlayOutWorldParticles(particle, true,
                (float) location.getX(), (float) location.getY(), (float) location.getZ(), 0, 0, 0, 0, amt);
        for (Player player : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(particles);
        }
    }

}
