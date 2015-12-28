package repo.minetoken.clans.structure.damage;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.Fish;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import repo.minetoken.clans.structure.combat.events.DamageEvent;
import repo.minetoken.clans.utilities.UtilAction;
import repo.minetoken.clans.utilities.UtilAlgebra;
import repo.minetoken.clans.utilities.UtilClick;
import repo.minetoken.clans.utilities.UtilWeapon;

public class DamageManager implements Listener {

    public boolean UseSimpleWeaponDamage = false;
    public boolean DisableDamageChanges = false;
    private boolean enabled = true;
    static JavaPlugin plugin;

    public DamageManager(JavaPlugin plugin) {
        DamageManager.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void startDamageEvent(EntityDamageEvent event) {
        if (!enabled) {
            return;
        }

        if (!(event.getEntity() instanceof LivingEntity)) {
            return;
        }
        LivingEntity damagee = getDamageeEntity(event);
        LivingEntity damager = UtilClick.getDamagerEntity(event, true);
        Projectile projectile = getProjectile(event);
        if ((projectile instanceof Fish)) {
            return;
        }
        if (!DisableDamageChanges) {
            weaponDamage(event, damager);
        }
        newDamageEvent(damagee, damager, projectile, event.getCause(), event.getDamage(), true, false, DisableDamageChanges, null, null);
        event.setCancelled(true);
    }

    public static void newDamageEvent(LivingEntity damagee, LivingEntity damager, Projectile proj, EntityDamageEvent.DamageCause cause, double damage, boolean knockback, boolean ignoreRate, boolean ignoreArmor, String source, String reason) {
        newDamageEvent(damagee, damager, proj,
                cause, damage, knockback, ignoreRate, ignoreArmor,
                source, reason, false);
    }

    public static void newDamageEvent(LivingEntity damagee, LivingEntity damager, Projectile proj, EntityDamageEvent.DamageCause cause, double damage, boolean knockback, boolean ignoreRate, boolean ignoreArmor, String source, String reason, boolean cancelled) {
        Bukkit.getServer().getPluginManager().callEvent(
                new DamageEvent(damagee, damager, proj, cause, damage,
                        knockback, ignoreRate, ignoreArmor,
                        source, reason, cancelled));
    }

    @EventHandler(priority = EventPriority.LOW)
    public void cancelDamageEvent(DamageEvent event) {
        if (event.getDamageeEntity().getHealth() <= 0.0D) {
            event.setCancelled("0 Health");
            return;
        }
        if (event.getDamageePlayer() != null) {
            Player damagee = event.getDamageePlayer();
            if (damagee.getGameMode() != GameMode.SURVIVAL) {
                event.setCancelled("Damagee in Creative");
                return;
            }

        }
        if (event.getDamagerPlayer(true) != null) {
            Player damager = event.getDamagerPlayer(true);
            if (damager.getGameMode() != GameMode.SURVIVAL) {
                event.setCancelled("Damager in Creative");
                return;
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void endDamageEvent(DamageEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (event.getDamage() < 1.0D) {
            return;
        }
        onDamage(event);
    }

    @EventHandler
    private void onDamage(DamageEvent event) {
        if (event.getDamageeEntity() == null) {
            return;
        }
        if (event.getDamageeEntity().getHealth() <= 0.0D) {
            return;
        }

        if ((event.getDamagerPlayer(true) != null) && (event.displayDamageToLevel())) {
            if (event.getCause() != EntityDamageEvent.DamageCause.THORNS) {
                event.getDamagerPlayer(true).setLevel((int) event.getDamage());
            }
        }
        try {
            int bruteBonus = 0;
            if (event.isBrute()) {
                if (((event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) ||
                        (event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) ||
                        (event.getCause() == EntityDamageEvent.DamageCause.CUSTOM)) &&
                        (event.getDamage() > 2.0D)) {
                    bruteBonus = 10;
                }
            }
            onHandleDamage(event.getDamageeEntity(), event.getDamagerEntity(true), event.getCause(), (int) event.getDamage() + bruteBonus, event.ignoreArmor());

            event.getDamageeEntity().playEffect(EntityEffect.HURT);
            double knockback = event.getDamage();
            if (knockback < 2.0D) {
                knockback = 2.0D;
            }
            knockback = Math.log10(knockback);
            for (Iterator localIterator = event.getKnockback().values().iterator(); localIterator.hasNext(); ) {
                double cur = ((Double) localIterator.next()).doubleValue();
                knockback *= cur;
            }
            if ((event.isKnockback()) &&
                    (event.getDamagerEntity(true) != null)) {
                Vector trajectory = UtilAlgebra.getTrajectory2d(event.getDamagerEntity(true), event.getDamageeEntity());
                trajectory.multiply(0.6D * knockback);
                trajectory.setY(Math.abs(trajectory.getY()));

                UtilAction.velocity(event.getDamageeEntity(),
                        trajectory, 0.2D + trajectory.length() * 0.8D, false, 0.0D, Math.abs(0.2D * knockback), 0.4D + 0.04D * knockback, true);
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static Server server;

    private void onHandleDamage(LivingEntity damagee, LivingEntity damager, EntityDamageEvent.DamageCause cause, float damage, boolean ignoreArmor)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        EntityLiving entityDamagee = ((CraftLivingEntity) damagee).getHandle();
        EntityLiving entityDamager = null;
        if (damager != null) {
            entityDamager = ((CraftLivingEntity) damager).getHandle();
        }
        entityDamagee.aG = 1.5F;
        if (entityDamagee.noDamageTicks > entityDamagee.maxNoDamageTicks / 2.0F) {
            if (damage <= entityDamagee.lastDamage) {
                return;
            }
            applyDamage(entityDamagee, damage - entityDamagee.lastDamage, ignoreArmor);
            entityDamagee.lastDamage = damage;
        } else {
            entityDamagee.lastDamage = damage;
            entityDamagee.aG = entityDamagee.getHealth();

            applyDamage(entityDamagee, damage, ignoreArmor);
        }
        if (entityDamager != null) {
            entityDamagee.b(entityDamager);
        }

        if ((entityDamager != null) &&
                ((entityDamager instanceof EntityHuman))) {
            entityDamagee.killer = ((EntityHuman) entityDamager);
        }
        if (entityDamagee.getHealth() <= 0.0F) {
            if (entityDamager != null) {
                if ((entityDamager instanceof EntityHuman)) {
                    entityDamagee.die(DamageSource.playerAttack((EntityHuman) entityDamager));
                } else if ((entityDamager instanceof EntityLiving)) {
                    entityDamagee.die(DamageSource.mobAttack(entityDamager));
                } else {
                    entityDamagee.die(DamageSource.GENERIC);
                }
            } else {
                entityDamagee.die(DamageSource.GENERIC);
            }
        }
    }

    @EventHandler
    public void onDamageSound(DamageEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if ((event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) && (event.getCause() != EntityDamageEvent.DamageCause.PROJECTILE)) {
            return;
        }
        LivingEntity damagee = event.getDamageeEntity();
        if (damagee == null) {
            return;
        }

        Sound sound = Sound.HURT_FLESH;
        float vol = 1.0F;
        float pitch = 1.0F;
        if ((damagee instanceof Player)) {
            Player player = (Player) damagee;

            double r = Math.random();

            ItemStack stack;
            if (r > 0.5D) {
                stack = player.getInventory().getChestplate();
            } else if (r > 0.25D) {
                stack = player.getInventory().getLeggings();
            } else if (r > 0.1D) {
                stack = player.getInventory().getHelmet();
            } else {
                stack = player.getInventory().getBoots();
            }
            if (stack != null) {
                if (stack.getType().toString().contains("LEATHER")) {
                    sound = Sound.SHOOT_ARROW;
                    pitch = 2.0F;
                } else if (stack.getType().toString().contains("CHAINMAIL")) {
                    sound = Sound.ITEM_BREAK;
                    pitch = 1.4F;
                } else if (stack.getType().toString().contains("GOLD")) {
                    sound = Sound.ITEM_BREAK;
                    pitch = 1.8F;
                } else if (stack.getType().toString().contains("IRON")) {
                    sound = Sound.BLAZE_HIT;
                    pitch = 0.7F;
                } else if (stack.getType().toString().contains("DIAMOND")) {
                    sound = Sound.BLAZE_HIT;
                    pitch = 0.9F;
                }
            }
        } else {
            return;
        }
        damagee.getWorld().playSound(damagee.getLocation(), sound, vol, pitch);
    }

    private void applyDamage(EntityLiving entityLiving, float damage, boolean ignoreArmor)
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        if (!ignoreArmor) {
            int j = 25 - entityLiving.ae;
            float k = damage * j;

            damage = k / 25.0F;
        }
        entityLiving.setHealth(entityLiving.getHealth() - damage);
    }

    private void weaponDamage(EntityDamageEvent event, LivingEntity ent) {
        if (!(ent instanceof Player)) {
            return;
        }
        if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            return;
        }
        Player damager = (Player) ent;
        if (UseSimpleWeaponDamage) {
            if (event.getDamage() > 1.0D) {
                event.setDamage(event.getDamage() - 1.0D);
            }
            if (damager.getItemInHand().getType().name().contains("GOLD")) {
                event.setDamage(event.getDamage() + 2.0D);
            }
            return;
        }
        if ((damager.getItemInHand() == null) || (!UtilWeapon.isWeapon(damager.getItemInHand()))) {
            event.setDamage(1.0D);
            return;
        }
        Material mat = damager.getItemInHand().getType();

        int damage = 6;
        if (mat.name().contains("WOOD")) {
            damage -= 3;
        } else if (mat.name().contains("STONE")) {
            damage -= 2;
        } else if (mat.name().contains("DIAMOND")) {
            damage += 0;
        } else if (mat.name().contains("GOLD")) {
            damage++;
        }
        event.setDamage(damage);
    }

    private LivingEntity getDamageeEntity(EntityDamageEvent event) {
        if ((event.getEntity() instanceof LivingEntity)) {
            return (LivingEntity) event.getEntity();
        }
        return null;
    }

    private Projectile getProjectile(EntityDamageEvent event) {
        if (!(event instanceof EntityDamageByEntityEvent)) {
            return null;
        }
        EntityDamageByEntityEvent eventEE = (EntityDamageByEntityEvent) event;
        if ((eventEE.getDamager() instanceof Projectile)) {
            return (Projectile) eventEE.getDamager();
        }
        return null;
    }

    public void setEnabled(boolean var) {
        enabled = var;
    }
}