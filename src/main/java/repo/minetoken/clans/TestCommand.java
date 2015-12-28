package repo.minetoken.clans;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import repo.minetoken.clans.structure.clancenter.ClansCenterMenu;

import java.util.Random;

public class TestCommand implements CommandExecutor, Listener {

    static Clans clans;

    public TestCommand(Clans clans) {
        this.clans = clans;
    }

    @Override
    public boolean onCommand(final CommandSender commandSender, Command command, String s, String[] strings) {
        final Player player = (Player) commandSender;
        treeEffect(player.getLocation());
        return false;
    }

    public static void particleCone(Location location1, double phi) {

        double x, y, z;
        for (double t = 0; t <= 2 * Math.PI; t = t + Math.PI / 16) {
            for (double i = 0; i <= 1; i = i + 1) {
                x = 0.4 * (2 * Math.PI - t) * 0.5 * Math.cos(t + phi + i * Math.PI);
                y = 0.5 * t;
                z = 0.4 * (2 * Math.PI - t) * 0.5 * Math.sin(t + phi + i * Math.PI);
                location1.add(x, y, z);
                Color mix = Color.fromRGB(255, 195, 0);
                Random random = new Random();
                int rand = random.nextInt(6) + 1;
                random = new Random();
                int red = (random.nextInt(256) + mix.getRed()) / 2;
                int green = (random.nextInt(256) + mix.getGreen()) / 2;
                int blue = (random.nextInt(256) + mix.getBlue()) / 2;

                if (rand < 6) {
                    ParticleEffect.REDSTONE.display(new ParticleEffect.OrdinaryColor(Color.fromRGB(52, 166, 95)), location1, 50);
                } else {
                    ParticleEffect.REDSTONE.display(new ParticleEffect.OrdinaryColor(Color.fromRGB(red, green, blue)), location1, 50);
                }
                location1.subtract(x, y, z);
            }
        }
    }

    public static void treeEffect(final Location location) {
        new BukkitRunnable() {
            double phi = 0;

            public void run() {
                phi = phi + Math.PI / 8;

                location.add(0, Math.PI + 0.15, 0);
                ParticleEffect.REDSTONE.display(new ParticleEffect.OrdinaryColor(Color.fromRGB(255, 195, 0)), location, 50);
                location.subtract(0, Math.PI + 0.15, 0);
                for (double t = 0; t <= Math.PI; t = t + Math.PI / 16) {
                    location.add(0, t, 0);
                    ParticleEffect.REDSTONE.display(new ParticleEffect.OrdinaryColor(Color.fromRGB(127, 51, 0)), location, 50);
                    location.subtract(0, t, 0);
                }
                particleCone(location, phi);

                /** You can uncomment this part if you want it disapearing in a few seconds. Change 30 to the time in seconds
                 * if (phi > 30 * Math.PI) {
                 *    this.cancel();
                 * }
                 */
            }
        }.runTaskTimer(clans, 0, 3);
    }

    public void testMath(final Player player) {
        clans.getServer().getScheduler().scheduleSyncRepeatingTask(clans, new Runnable() {
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
                    }.runTaskLater(clans, 2);
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
                                }.runTaskLater(clans, 2);
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
                    }.runTaskLater(clans, 2);
                    this.cancel();
                }
            }
        }.runTaskTimer(clans, 0, 0);
    }

    public void playParticles(EnumParticle particle, Location location, int amt) {
        PacketPlayOutWorldParticles particles = new PacketPlayOutWorldParticles(particle, true,
                (float) location.getX(), (float) location.getY(), (float) location.getZ(), 0, 0, 0, 0, amt);
        for (Player player : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(particles);
        }
    }

}
