package repo.minetoken.clans.structure.character.skills;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import repo.minetoken.clans.Clans;
import repo.minetoken.clans.structure.damage.DamageManager;
import repo.minetoken.clans.structure.cooldowns.Cooldown;
import repo.minetoken.clans.structure.character.Characters;
import repo.minetoken.clans.structure.character.SkillType;
import repo.minetoken.clans.structure.character.Skills;
import repo.minetoken.clans.utilities.Format;
import repo.minetoken.clans.utilities.UtilSound;
import repo.minetoken.clans.utilities.UtilSound.Pitch;
import repo.minetoken.clans.utilities.UtilAction;
import repo.minetoken.clans.utilities.UtilAlgebra;
import repo.minetoken.clans.utilities.UtilBlock;
import repo.minetoken.clans.utilities.UtilWeapon;

public class Fury extends Skills {
    ArrayList<Player> furyUsed = new ArrayList<>();
    ArrayList<Player> protect = new ArrayList<>();
    private HashMap<LivingEntity, Double> height = new HashMap<>();
    private HashMap<LivingEntity, Long> live = new HashMap<>();

    public Fury() {
        super("Fury", new String[]{""}, SkillType.AXE);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (UtilWeapon.hasAxe(player)) {
                if (!Characters.warrior.containsKey(player.getName())) {
                    return;
                }
                if (Cooldown.isCooling(player.getName(), "Fury Slam")) {
                    Cooldown.coolDurMessage(player, "Fury Slam");
                    return;
                }
                Material m = player.getLocation().getBlock().getType();
                if (m == Material.STATIONARY_WATER || m == Material.WATER) {
                    player.sendMessage(Format.main("Warrior", "You cannot use " + ChatColor.LIGHT_PURPLE + "Fury Slam" + ChatColor.YELLOW + " in water!"));
                    UtilSound.play(player, Sound.VILLAGER_HIT, Pitch.HIGH);
                    return;
                }
                UtilSound.play(player, Sound.IRONGOLEM_THROW, Pitch.HIGH);
                Cooldown.add(player.getName(), "Fury Slam", "Warrior", 8, 8);
                Vector vec = player.getLocation().getDirection();
                if (vec.getY() < 0.0D) {
                    vec.setY(vec.getY() * -1.0D);
                }
                UtilAction.velocity(player, vec, 1.0D, false, 0.0D, 1.0D, 1.0D, true);
                protect.add(player);
                live.put(player, Long.valueOf(System.currentTimeMillis()));
                height.put(player, Double.valueOf(player.getLocation().getY()));
                Clans.instance.getServer().getScheduler().scheduleSyncDelayedTask(Clans.instance, new Runnable() {
                    public void run() {
                        furyUsed.add(player);
                    }
                }, 5L);
                return;
            }
        }
    }

    @EventHandler
    public void onFall(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getCause() == DamageCause.FALL) {
                float fDist = event.getEntity().getFallDistance();
                if (protect.contains(event.getEntity())) {
                    if (fDist >= 10) {
                        event.setCancelled(false);
                        protect.remove(event.getEntity());
                        return;
                    }
                    event.setCancelled(true);
                    protect.remove(event.getEntity());
                }
            }
        }
    }

    public void onFurySlam(final Player player) {

        live.remove(player);

        double mult = 0.5D;
        if (height.containsKey(player)) {
            mult += height.remove(player).doubleValue() - player.getLocation().getY() / 10.0D;
            mult = Math.min(mult, 2.0D);
            player.sendMessage(Format.main("Warrior", "Effectiveness: " + ChatColor.GREEN + new StringBuilder(String.valueOf((int) (mult * 100.0D))).append("%").toString()));
        }
        int damage = 6;
        double range = 6.0D * mult;
        HashMap<LivingEntity, Double> targets = UtilBlock.getInRadius(player.getLocation(), range);
        if (UtilAction.isGrounded(player)) {
            for (LivingEntity cur : targets.keySet()) {
                if (!cur.equals(player)) {
                    if (UtilAction.isGrounded(player)) {
                        DamageManager.newDamageEvent(cur, player, null,
                                EntityDamageEvent.DamageCause.CUSTOM, damage * (targets.get(cur)).doubleValue() + 0.5D, false, false, false,
                                player.getName(), "Fury Slam");

                        UtilAction.velocity(cur, UtilAlgebra.getTrajectory2d(player.getLocation().toVector(), cur.getLocation().toVector()),
                                1.8D * (targets.get(cur)).doubleValue() * mult, true, 0.0D, 0.4D + 1.0D * ((Double) targets.get(cur)).doubleValue() * mult, 1.6D * mult, true);

                        if ((cur instanceof Player)) {
                            player.sendMessage(Format.main("Warrior", "You hit " + Format.highlight(cur.getName()) + ChatColor.YELLOW + " with " + ChatColor.LIGHT_PURPLE + "Fury Slam" + "."));
                            cur.sendMessage(Format.main("Warrior", Format.highlight(player.getName()) + " hit you with " + ChatColor.LIGHT_PURPLE + "Fury Slam" + "."));
                        }
                    }
                }
            }
            player.getWorld().playSound(player.getLocation(), Sound.ZOMBIE_WOOD, 2.0F, 0.2F);
            for (Block cur : UtilBlock.getInRadius(player.getLocation(), Double.valueOf(4.0D)).keySet()) {
                if ((UtilBlock.airFoliage(cur.getRelative(BlockFace.UP))) && (!UtilBlock.airFoliage(cur))) {
                    cur.getWorld().playEffect(cur.getLocation(), Effect.STEP_SOUND, cur.getTypeId());
                }
            }
            Clans.instance.getServer().getScheduler().scheduleSyncDelayedTask(Clans.instance, new Runnable() {
                public void run() {
                    protect.add(player);
                }
            }, 5L);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (furyUsed.contains(player)) {

        } else {
            return;
        }
        if (player.isOnGround() && furyUsed.contains(player)) {

            onFurySlam(player);
            furyUsed.remove(player);

        }
    }

    public void onReset(Player player) {
        live.remove(player);
        height.remove(player);
    }
}