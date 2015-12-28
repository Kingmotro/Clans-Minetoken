package repo.minetoken.clans.structure.character.skills;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;
import repo.minetoken.clans.Clans;
import repo.minetoken.clans.structure.cooldowns.Cooldown;
import repo.minetoken.clans.structure.character.Characters;
import repo.minetoken.clans.structure.character.SkillType;
import repo.minetoken.clans.structure.character.Skills;
import repo.minetoken.clans.utilities.Format;
import repo.minetoken.clans.utilities.UtilSound;
import repo.minetoken.clans.utilities.UtilWeapon;
import repo.minetoken.clans.utilities.UtilSound.Pitch;

public class Rush extends Skills {

    ArrayList<Player> effect = new ArrayList<Player>();

    public Rush() {
        super("Rush", new String[]{""}, SkillType.AXE);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {

        final Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (UtilWeapon.hasSword(player)) {
                if (!Characters.thief.containsKey(player.getName())) {
                    return;
                }
                if (Cooldown.isCooling(player.getName(), "Rush")) {
                    Cooldown.coolDurMessage(player, "Rush");
                    return;
                }
                Material m = player.getLocation().getBlock().getType();
                if (m == Material.STATIONARY_WATER || m == Material.WATER) {
                    player.sendMessage(Format.main("Wizard", "You cannot use " + ChatColor.LIGHT_PURPLE + "Rush" + ChatColor.YELLOW + " in water!"));
                    UtilSound.play(player, Sound.VILLAGER_HIT, Pitch.HIGH);
                    return;
                }
                Vector v = player.getLocation().getDirection().multiply(3.5).setY(1);
                player.setVelocity(v);
                Bukkit.getWorld(player.getWorld().getName()).playEffect(player.getLocation(), Effect.STEP_SOUND, 57);
                Bukkit.getWorld(player.getWorld().getName()).playEffect(player.getLocation(), Effect.STEP_SOUND, 41);
                UtilSound.play(player, Sound.BAT_TAKEOFF, Pitch.HIGH);
                Cooldown.add(player.getName(), "Rush", "Wizard", 8, 8);
                Bukkit.getWorld(player.getWorld().getName()).playEffect(player.getLocation(), Effect.STEP_SOUND, 57);
                Clans.instance.getServer().getScheduler().scheduleSyncDelayedTask(Clans.instance, new Runnable() {
                    public void run() {
                        effect.add(player);
                    }
                }, 1);
            }
        }

    }

    @EventHandler
    public void onFall(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getCause() == DamageCause.FALL) {
                if (effect.contains(event.getEntity())) {
                    event.setCancelled(true);
                    effect.remove(event.getEntity());
                }
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (effect.contains(player)) {
            Bukkit.getWorld(player.getWorld().getName()).playEffect(player.getLocation(), Effect.STEP_SOUND, 57);
            Bukkit.getWorld(player.getWorld().getName()).playEffect(player.getLocation(), Effect.LARGE_SMOKE, 50);
        } else {
            return;
        }
        if (player.isOnGround() && effect.contains(player)) {

            Bukkit.getWorld(player.getWorld().getName()).playEffect(player.getLocation(), Effect.STEP_SOUND, 57);
            Bukkit.getWorld(player.getWorld().getName()).playEffect(player.getLocation(), Effect.STEP_SOUND, 41);
            effect.remove(player);
        }
    }
}
