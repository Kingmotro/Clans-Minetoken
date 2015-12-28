package repo.minetoken.clans.structure.combat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.server.v1_8_R3.ItemStack;
import repo.minetoken.clans.Clans;
import repo.minetoken.clans.structure.combat.events.CombatEvent;
import repo.minetoken.clans.structure.combat.events.DamageEvent;
import repo.minetoken.clans.structure.combat.events.DeathEvent;
import repo.minetoken.clans.utilities.Format;
import repo.minetoken.clans.structure.update.UpdateEvent;
import repo.minetoken.clans.structure.update.UpdateType;
import repo.minetoken.clans.utilities.UtilClick;
import repo.minetoken.clans.utilities.UtilEntity;
import repo.minetoken.clans.utilities.UtilTime;

public class CombatManager implements Listener {

    private HashMap<Player, CombatLog> active = new HashMap<>();
    private HashMap<String, Combat> combatClients = new HashMap<>();
    private HashSet<Player> removeList = new HashSet<>();
    protected long ExpireTime = 15000L;

    public CombatManager(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public Combat get(String name) {
        if (!combatClients.containsKey(name)) {
            combatClients.put(name, new Combat());
        }
        return combatClients.get(name);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void addAttack(EntityDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if ((event.getEntity() == null) || (!(event.getEntity() instanceof Player))) {
            return;
        }
        Player damagee = (Player) event.getEntity();

        LivingEntity damagerEnt = UtilClick.getDamagerEntity(event, true);
        if (damagerEnt != null) {
            if ((damagerEnt instanceof Player)) {
                get((Player) damagerEnt).LastCombat = System.currentTimeMillis();
            }
            get(damagee).attacked(UtilEntity.getName(damagerEnt),
                    event.getDamage(), damagerEnt, null);
        } else {
            EntityDamageEvent.DamageCause cause = event.getCause();

            String source = "?";
            String reason = "-";
            if (cause == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
                source = "Explosion";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.CONTACT) {
                source = "Cactus";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.CUSTOM) {
                source = "Custom";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.DROWNING) {
                source = "Water";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                source = "Entity";
                reason = "Attack";
            } else if (cause == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                source = "Explosion";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.FALL) {
                source = "Fall";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.FALLING_BLOCK) {
                source = "Falling Block";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.FIRE) {
                source = "Fire";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.FIRE_TICK) {
                source = "Fire";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.LAVA) {
                source = "Lava";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.LIGHTNING) {
                source = "Lightning";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.MAGIC) {
                source = "Magic";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.MELTING) {
                source = "Melting";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.POISON) {
                source = "Poison";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.PROJECTILE) {
                source = "Projectile";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.STARVATION) {
                source = "Starvation";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.SUFFOCATION) {
                source = "Suffocation";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.SUICIDE) {
                source = "Suicide";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.VOID) {
                source = "Void";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.WITHER) {
                source = "Wither";
                reason = "-";
            }
            get(damagee).attacked(source,
                    event.getDamage(), null, reason);
        }
    }

    @EventHandler
    public void addAttack(DamageEvent event) {
        if (event.getDamageePlayer() == null) {
            return;
        }
        if (event.getDamagerEntity(true) != null) {

            String reason = event.getReason();
            if (reason == null) {
                if (event.getDamagerPlayer(false) != null) {
                    Player damager = event.getDamagerPlayer(false);

                    reason = "Fists";
                    if (damager.getItemInHand() != null) {
                        ItemStack itemStack = CraftItemStack.asNMSCopy(damager
                                .getItemInHand());
                        if (itemStack != null) {
                            reason =
                                    CraftItemStack.asNMSCopy(damager.getItemInHand()).getName();
                        }
                    }
                } else if (event.getProjectile() != null) {
                    if ((event.getProjectile() instanceof Arrow)) {
                        reason = "Archery";
                    } else if ((event.getProjectile() instanceof Fireball)) {
                        reason = "Fireball";
                    }
                }
            }
            if ((event.getDamagerEntity(true) instanceof Player)) {
                get((Player) event.getDamagerEntity(true)).LastCombat = System.currentTimeMillis();
            }
            get(event.getDamageePlayer()).attacked(UtilEntity.getName(event.getDamagerEntity(true)),
                    (int) event.getDamage(), event.getDamagerEntity(true),
                    reason.toString());
        } else {
            EntityDamageEvent.DamageCause cause = event.getCause();

            String source = "?";
            String reason = "-";
            if (cause == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
                source = "Explosion";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.CONTACT) {
                source = "Cactus";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.CUSTOM) {
                source = "Custom";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.DROWNING) {
                source = "Water";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                source = "Entity";
                reason = "Attack";
            } else if (cause == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                source = "Explosion";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.FALL) {
                source = "Fall";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.FALLING_BLOCK) {
                source = "Falling Block";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.FIRE) {
                source = "Fire";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.FIRE_TICK) {
                source = "Fire";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.LAVA) {
                source = "Lava";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.LIGHTNING) {
                source = "Lightning";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.MAGIC) {
                source = "Magic";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.MELTING) {
                source = "Melting";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.POISON) {
                source = "Poison";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.PROJECTILE) {
                source = "Projectile";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.STARVATION) {
                source = "Starvation";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.SUFFOCATION) {
                source = "Suffocation";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.SUICIDE) {
                source = "Suicide";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.VOID) {
                source = "Void";
                reason = "-";
            } else if (cause == EntityDamageEvent.DamageCause.WITHER) {
                source = "Wither";
                reason = "-";
            }
            if (event.getReason() != null) {
                reason = event.getReason();
            }
            get(event.getDamageePlayer()).attacked(source,
                    (int) event.getDamage(), null, reason);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void handleDeath(PlayerDeathEvent event) {
        event.setDeathMessage(null);
        if (!active.containsKey(event.getEntity())) {
            return;
        }
        CombatLog log = active.remove(event.getEntity());
        log.setDeathTime(System.currentTimeMillis());

        get(event.getEntity().getName()).GetDeaths().addFirst(log);

        int assists = 0;
        for (int i = 0; i < log.GetAttackers().size(); i++) {
            if (log.GetAttackers().get(i).IsPlayer()) {
                if (!UtilTime.elapsed(log.GetAttackers().get(i).getLastDamage(), this.ExpireTime)) {
                    if (log.getKiller() == null) {
                        log.setKiller(log.GetAttackers().get(i));

                        Combat killerClient = get(log.GetAttackers().get(i).getName());
                        if (killerClient != null) {
                            killerClient.GetKills().addFirst(log);
                        }
                    } else {
                        assists++;

                        Combat assistClient = get(log.GetAttackers().get(i).getName());
                        if (assistClient != null) {
                            assistClient.GetAssists().addFirst(log);
                        }
                    }
                }
            }
        }
        log.setAssists(assists);

        DeathEvent deathEvent = new DeathEvent(event, get(event.getEntity().getName()), log);
        Clans.instance.getServer().getPluginManager().callEvent(deathEvent);
        if (deathEvent.GetBroadcastType() == DeathMessageType.Detailed) {
            for (Player cur : event.getEntity().getWorld().getPlayers()) {
                String killedColor = log.getKilledColor();

                String deadPlayer = killedColor + event.getEntity().getName();
                if (log.getKiller() != null) {
                    String killerColor = log.getKilledColor();

                    String killPlayer = killerColor + log.getKiller().getName();
                    if (log.getAssists() > 0) {
                        killPlayer = killPlayer + " + " + log.getAssists();
                    }
                    String weapon = log.getKiller().getLastDamageSource();
                    cur.sendMessage(Format.main("Death",
                            deadPlayer + ChatColor.GRAY + " killed by " +
                                    killPlayer + ChatColor.GRAY + " with " +
                                    Format.highlight(weapon) + "."));

                } else if (log.GetAttackers().isEmpty()) {
                    cur.sendMessage(Format.main("Death",
                            deadPlayer + ChatColor.GRAY + "has died."));
                } else if ((log.getLastDamager() != null) && (log.getLastDamager().getReason() != null) && (log.getLastDamager().getReason().length() > 1)) {
                    cur.sendMessage(Format.main("Death", new StringBuilder(String.valueOf(deadPlayer))
                            .append(ChatColor.GRAY)
                            .append(" killed by ")
                            .append(Format.highlight(log.getLastDamager()
                                    .getReason())).toString()) +
                            ChatColor.GRAY + ".");

                } else {
                    cur.sendMessage(Format.main("Death", new StringBuilder(String.valueOf(deadPlayer))
                            .append(ChatColor.GRAY)
                            .append(" killed by ")
                            .append(Format.highlight(log.GetAttackers().getFirst().getName())).toString()) +
                            ChatColor.GRAY + ".");

                }
            }
            event.getEntity().sendMessage("" + log.display());
        } else if (deathEvent.GetBroadcastType() == DeathMessageType.Simple) {
            if (log.getKiller() != null) {
                String killerColor = log.getKillerColor();
                String killPlayer = killerColor + log.getKiller().getName();

                String killedColor = log.getKilledColor();
                String deadPlayer = killedColor + event.getEntity().getName();
                if (log.getAssists() > 0) {
                    killPlayer = killPlayer + " + " + log.getAssists();
                }
                String weapon = log.getKiller().getLastDamageSource();

                event.getEntity().sendMessage(Format.main("Death", "You killed " + Format.highlight(deadPlayer) + " with " + Format.highlight(weapon) + "."));

                event.getEntity().sendMessage(Format.main("Death", killPlayer + ChatColor.GRAY + " killed you with " + Format.highlight(weapon) + "."));
            } else if (log.GetAttackers().isEmpty()) {
                event.getEntity().sendMessage(Format.main("Death", "You have died."));
            } else {
                event.getEntity().sendMessage(Format.main("Death", new StringBuilder("You were killed by ")
                        .append(Format.highlight(log.GetAttackers().getFirst().getName())).toString()) + ChatColor.GRAY + ".");
            }
        }
    }

    @EventHandler
    public void ExpireOld(UpdateEvent event) {
        if (event.getType() != UpdateType.FAST) {
            return;
        }
        for (CombatLog log : active.values()) {
            log.expireOld();
        }
    }

    public void add(Player player) {
        active.put(player, new CombatLog(player, 15000L));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void Clear(CombatEvent event) {
        active.remove(event.GetPlayer());
    }

    public CombatLog get(Player player) {
        if (!active.containsKey(player)) {
            add(player);
        }
        return active.get(player);
    }

    public long getExpireTime() {
        return ExpireTime;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void ClearInactives(UpdateEvent event) {
        if (event.getType() == UpdateType.MIN_02) {
            Iterator<Player> removeIterator = removeList.iterator();
            while (removeIterator.hasNext()) {
                Player player = removeIterator.next();
                if (!player.isOnline()) {
                    active.remove(player);
                }
                removeIterator.remove();
            }
            for (Player player : active.keySet()) {
                if (!player.isOnline()) {
                    removeList.add(player);
                }
            }
        }
    }

    public void debugInfo(Player player) {
        StringBuilder nameBuilder = new StringBuilder();
        for (Player combats : active.keySet()) {
            if (!combats.isOnline()) {
                if (nameBuilder.length() != 0) {
                    nameBuilder.append(", ");
                }
                nameBuilder.append(combats.getName());
            }
        }
        player.sendMessage(Format.main("Test", nameBuilder.toString()));
    }
}
