package repo.minetoken.clans.structure.character.skills;

import java.util.HashSet;
import java.util.LinkedList;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import repo.minetoken.clans.structure.character.Characters;
import repo.minetoken.clans.structure.character.SkillType;
import repo.minetoken.clans.structure.character.Skills;
import repo.minetoken.clans.utilities.Format;
import repo.minetoken.clans.structure.update.UpdateEvent;
import repo.minetoken.clans.structure.update.UpdateType;
import repo.minetoken.clans.utilities.UtilAction;
import repo.minetoken.clans.utilities.UtilAlgebra;
import repo.minetoken.clans.utilities.UtilMath;
import repo.minetoken.clans.utilities.UtilWeapon;

public class BladeTrail extends Skills {

    public BladeTrail() {
        super("Blade Trail", new String[]{""}, SkillType.SWORD);
    }

    private HashSet<Player> active = new HashSet<Player>();

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
            if (UtilWeapon.hasSword(player)) {
                if (!Characters.warrior.containsKey(player.getName())) {
                    return;
                }

                active.add(player);
                player.sendMessage(Format.main("Warrior", "You prepared to use " + ChatColor.LIGHT_PURPLE + "Evade" + ""));
            }
    }

    @EventHandler
    public void onEnergy(UpdateEvent event) {
        if (event.getType() != UpdateType.TICK) {
            return;
        }
        for (Player cur : Bukkit.getOnlinePlayers()) {
            if (active.contains(cur)) {

                if (!cur.isBlocking()) {
                    active.remove(cur);
                } else {
                    cur.getWorld().playEffect(cur.getLocation(), Effect.STEP_SOUND, 42);
                    for (int i = 0; i <= 5; i++) {
                        onPull(cur, cur.getEyeLocation().add(cur.getLocation().getDirection().multiply(i)));
                    }
                }
            }

        }
    }

    public void onPull(Player player, Location loc) {
        for (Player other : getNearby(loc, 2.0D)) {
            if (!player.equals(other)) {
                if (UtilMath.offset(player, other) >= 2.0D) {
                    UtilAction.velocity(other, UtilAlgebra.getTrajectory2d(other, player),
                            0.2D, false, 0.0D, 0.0D, 1.0D, false);
                }
            }
        }
    }

    public static LinkedList<Player> getNearby(Location loc, double maxDist) {
        LinkedList<Player> nearbyMap = new LinkedList<>();
        for (Player cur : loc.getWorld().getPlayers()) {
            if (cur.getGameMode() != GameMode.CREATIVE) {
                if (!cur.isDead()) {
                    double dist = loc.toVector().subtract(cur.getLocation().toVector()).length();
                    if (dist <= maxDist) {
                        for (int i = 0; i < nearbyMap.size(); i++) {
                            if (dist < loc.toVector().subtract(nearbyMap.get(i).getLocation().toVector()).length()) {
                                nearbyMap.add(i, cur);
                                break;
                            }
                        }
                        if (!nearbyMap.contains(cur)) {
                            nearbyMap.addLast(cur);
                        }
                    }
                }
            }
        }
        return nearbyMap;
    }

    public void onReset(Player player) {
        active.remove(player);
    }
}
