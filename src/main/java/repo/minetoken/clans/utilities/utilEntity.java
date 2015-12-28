package repo.minetoken.clans.utilities;

import java.util.HashMap;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftCreature;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Giant;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import net.minecraft.server.v1_8_R3.EntityCreature;
import net.minecraft.server.v1_8_R3.Navigation;

public class UtilEntity {
    private static HashMap<org.bukkit.entity.Entity, String> nameM = new HashMap<Entity, String>();
    private static HashMap<String, EntityType> creatureMap = new HashMap<String, EntityType>();

    public static HashMap<org.bukkit.entity.Entity, String> getEntityNames() {
        return nameM;
    }

    public static String getName(org.bukkit.entity.Entity ent) {
        if (ent == null) {
            return "Null";
        }
        if (ent.getType() == EntityType.PLAYER) {
            return ent.getName();
        }
        if (getEntityNames().containsKey(ent)) {
            return getEntityNames().get(ent);
        }
        if ((ent instanceof LivingEntity)) {
            LivingEntity le = (LivingEntity) ent;
            if (le.getCustomName() != null) {
                return le.getCustomName();
            }
        }
        return getName(ent.getType());
    }

    public static String getName(EntityType type) {
        for (String cur : creatureMap.keySet()) {
            if (creatureMap.get(cur) == type) {
                return cur;
            }
        }
        return type.getName();
    }


    public static HashMap<LivingEntity, Double> getInRadius(Location loc, double dR) {
        HashMap<LivingEntity, Double> ents = new HashMap<LivingEntity, Double>();
        for (org.bukkit.entity.Entity cur : loc.getWorld().getEntities()) {
            if (((cur instanceof LivingEntity)) && ((!(cur instanceof Player)) || (((Player) cur).getGameMode() != GameMode.CREATIVE))) {
                LivingEntity ent = (LivingEntity) cur;

                double offset = UtilMath.offset(loc, ent.getLocation());
                if (offset < dR) {
                    ents.put(ent, Double.valueOf(1.0D - offset / dR));
                }
            }
        }
        return ents;
    }

    public static boolean hitBox(Location loc, LivingEntity ent, double mult, EntityType disguise) {
        if (disguise != null) {
            if (disguise == EntityType.SQUID) {
                if (UtilMath.offset(loc, ent.getLocation().add(0.0D, 0.4D, 0.0D)) < 0.6D * mult) {
                    return true;
                }
                return false;
            }
        }
        if ((ent instanceof Player)) {
            Player player = (Player) ent;
            if (UtilMath.offset(loc, player.getEyeLocation()) < 0.4D * mult) {
                return true;
            }
            if (UtilMath.offset2d(loc, player.getLocation()) < 0.6D * mult) {
                if ((loc.getY() > player.getLocation().getY()) && (loc.getY() < player.getEyeLocation().getY())) {
                    return true;
                }
            }
        } else if ((ent instanceof Giant)) {
            if ((loc.getY() > ent.getLocation().getY()) && (loc.getY() < ent.getLocation().getY() + 12.0D) &&
                    (UtilMath.offset2d(loc, ent.getLocation()) < 4.0D)) {
                return true;
            }
        } else if ((loc.getY() > ent.getLocation().getY()) && (loc.getY() < ent.getLocation().getY() + 2.0D) &&
                (UtilMath.offset2d(loc, ent.getLocation()) < 0.5D * mult)) {
            return true;
        }
        return false;
    }

    public static boolean isGrounded(org.bukkit.entity.Entity ent) {
        if ((ent instanceof CraftEntity)) {
            return ((CraftEntity) ent).getHandle().onGround;
        }
        return UtilBlock.solid(ent.getLocation().getBlock().getRelative(BlockFace.DOWN));
    }

    public static void PlayDamageSound(LivingEntity damagee) {
        Sound sound = Sound.HURT_FLESH;
        if (damagee.getType() == EntityType.BAT) {
            sound = Sound.BAT_HURT;
        } else if (damagee.getType() == EntityType.BLAZE) {
            sound = Sound.BLAZE_HIT;
        } else if (damagee.getType() == EntityType.CAVE_SPIDER) {
            sound = Sound.SPIDER_IDLE;
        } else if (damagee.getType() == EntityType.CHICKEN) {
            sound = Sound.CHICKEN_HURT;
        } else if (damagee.getType() == EntityType.COW) {
            sound = Sound.COW_HURT;
        } else if (damagee.getType() == EntityType.CREEPER) {
            sound = Sound.CREEPER_HISS;
        } else if (damagee.getType() == EntityType.ENDER_DRAGON) {
            sound = Sound.ENDERDRAGON_GROWL;
        } else if (damagee.getType() == EntityType.ENDERMAN) {
            sound = Sound.ENDERMAN_HIT;
        } else if (damagee.getType() == EntityType.GHAST) {
            sound = Sound.GHAST_SCREAM;
        } else if (damagee.getType() == EntityType.GIANT) {
            sound = Sound.ZOMBIE_HURT;
        } else if (damagee.getType() == EntityType.IRON_GOLEM) {
            sound = Sound.IRONGOLEM_HIT;
        } else if (damagee.getType() == EntityType.MAGMA_CUBE) {
            sound = Sound.MAGMACUBE_JUMP;
        } else if (damagee.getType() == EntityType.MUSHROOM_COW) {
            sound = Sound.COW_HURT;
        } else if (damagee.getType() == EntityType.OCELOT) {
            sound = Sound.CAT_MEOW;
        } else if (damagee.getType() == EntityType.PIG) {
            sound = Sound.PIG_IDLE;
        } else if (damagee.getType() == EntityType.PIG_ZOMBIE) {
            sound = Sound.ZOMBIE_HURT;
        } else if (damagee.getType() == EntityType.SHEEP) {
            sound = Sound.SHEEP_IDLE;
        } else if (damagee.getType() == EntityType.SILVERFISH) {
            sound = Sound.SILVERFISH_HIT;
        } else if (damagee.getType() == EntityType.SKELETON) {
            sound = Sound.SKELETON_HURT;
        } else if (damagee.getType() == EntityType.SLIME) {
            sound = Sound.SLIME_ATTACK;
        } else if (damagee.getType() == EntityType.SNOWMAN) {
            sound = Sound.STEP_SNOW;
        } else if (damagee.getType() == EntityType.SPIDER) {
            sound = Sound.SPIDER_IDLE;
        } else if (damagee.getType() == EntityType.WITHER) {
            sound = Sound.WITHER_HURT;
        } else if (damagee.getType() == EntityType.WOLF) {
            sound = Sound.WOLF_HURT;
        } else if (damagee.getType() == EntityType.ZOMBIE) {
            sound = Sound.ZOMBIE_HURT;
        }
        damagee.getWorld().playSound(damagee.getLocation(), sound, 1.5F + (float) (0.5D * Math.random()), 0.8F + (float) (0.4000000059604645D * Math.random()));
    }

    public static boolean onBlock(Player player) {
        double xMod = player.getLocation().getX() % 1.0D;
        if (player.getLocation().getX() < 0.0D) {
            xMod += 1.0D;
        }
        double zMod = player.getLocation().getZ() % 1.0D;
        if (player.getLocation().getZ() < 0.0D) {
            zMod += 1.0D;
        }
        int xMin = 0;
        int xMax = 0;
        int zMin = 0;
        int zMax = 0;
        if (xMod < 0.3D) {
            xMin = -1;
        }
        if (xMod > 0.7D) {
            xMax = 1;
        }
        if (zMod < 0.3D) {
            zMin = -1;
        }
        if (zMod > 0.7D) {
            zMax = 1;
        }
        for (int x = xMin; x <= xMax; x++) {
            for (int z = zMin; z <= zMax; z++) {
                if (player.getLocation().add(x, -0.5D, z).getBlock().getType() != Material.AIR) {
                    return true;
                }
                Material beneath = player.getLocation().add(x, -1.5D, z).getBlock().getType();
                if ((player.getLocation().getY() % 0.5D == 0.0D) && (
                        (beneath == Material.FENCE) ||
                                (beneath == Material.NETHER_FENCE) ||
                                (beneath == Material.COBBLE_WALL))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void creatureMove(org.bukkit.entity.Entity ent, Location target, float speed) {
        if (!(ent instanceof Creature)) {
            return;
        }
        if (UtilMath.offset(ent.getLocation(), target) < 0.1D) {
            return;
        }
        EntityCreature ec = ((CraftCreature) ent).getHandle();
        Navigation nav = (Navigation) ec.getNavigation();
        if (UtilMath.offset(ent.getLocation(), target) > 24.0D) {
            Location newTarget = ent.getLocation();

            newTarget.add(UtilAlgebra.getTrajectory(ent.getLocation(), target).multiply(24));

            nav.a(newTarget.getX(), newTarget.getY(), newTarget.getZ(), speed);
        } else {
            nav.a(target.getX(), target.getY(), target.getZ(), speed);
        }
    }

    public static boolean creatureMoveFast(org.bukkit.entity.Entity ent, Location target, float speed) {
        if (!(ent instanceof Creature)) {
            return false;
        }
        if (UtilMath.offset(ent.getLocation(), target) < 0.1D) {
            return false;
        }
        if (UtilMath.offset(ent.getLocation(), target) < 2.0D) {
            speed = Math.min(speed, 1.0F);
        }
        EntityCreature ec = ((CraftCreature) ent).getHandle();
        ec.getControllerMove().a(target.getX(), target.getY(), target.getZ(), speed);

        return true;
    }
}
