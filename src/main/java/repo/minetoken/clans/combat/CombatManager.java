package repo.minetoken.clans.combat;

import java.util.HashSet;
import java.util.Iterator;

import org.bukkit.Bukkit;
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
import repo.minetoken.clans.combat.events.CCombatEvent;
import repo.minetoken.clans.combat.events.CDamageEvent;
import repo.minetoken.clans.combat.events.CDeathEvent;
import repo.minetoken.clans.utilities.C;
import repo.minetoken.clans.utilities.CHashMap;
import repo.minetoken.clans.utilities.Format;
import repo.minetoken.clans.utilities.UpdateEvent;
import repo.minetoken.clans.utilities.UpdateType;
import repo.minetoken.clans.utilities.utilClick;
import repo.minetoken.clans.utilities.utilEntity;
import repo.minetoken.clans.utilities.utilTime;

public class CombatManager implements Listener
{
  private CHashMap<Player, CombatLog> active = new CHashMap<Player, CombatLog>();
  private CHashMap<String, CCombat> combatClients = new CHashMap<String, CCombat>();
  private HashSet<Player> removeList = new HashSet<Player>();
  protected long ExpireTime = 15000L;
  
  public CombatManager(JavaPlugin plugin)
  {
	   Bukkit.getPluginManager().registerEvents(this, plugin); 
  }
  
  
  public CCombat Get(String name)
  {
    if (!combatClients.containsKey(name)) {
      combatClients.put(name, new CCombat());
    }
    return (CCombat)combatClients.get(name);
  }
  
  @EventHandler(priority=EventPriority.MONITOR)
  public void AddAttack(EntityDamageEvent event)
  {
    if (event.isCancelled()) {
      return;
    }
    if ((event.getEntity() == null) || (!(event.getEntity() instanceof Player))) {
      return;
    }
    Player damagee = (Player)event.getEntity();
    
    LivingEntity damagerEnt = utilClick.GetDamagerEntity(event, true);
    if (damagerEnt != null)
    {
      if ((damagerEnt instanceof Player)) {
        Get((Player)damagerEnt).LastCombat = System.currentTimeMillis();
      }
      Get(damagee).Attacked(utilEntity.getName(damagerEnt), 
        event.getDamage(), damagerEnt, null);
    }
    else
    {
      EntityDamageEvent.DamageCause cause = event.getCause();
      
      String source = "?";
      String reason = "-";
      if (cause == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)
      {
        source = "Explosion";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.CONTACT)
      {
        source = "Cactus";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.CUSTOM)
      {
        source = "Custom";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.DROWNING)
      {
        source = "Water";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.ENTITY_ATTACK)
      {
        source = "Entity";
        reason = "Attack";
      }
      else if (cause == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)
      {
        source = "Explosion";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.FALL)
      {
        source = "Fall";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.FALLING_BLOCK)
      {
        source = "Falling Block";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.FIRE)
      {
        source = "Fire";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.FIRE_TICK)
      {
        source = "Fire";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.LAVA)
      {
        source = "Lava";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.LIGHTNING)
      {
        source = "Lightning";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.MAGIC)
      {
        source = "Magic";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.MELTING)
      {
        source = "Melting";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.POISON)
      {
        source = "Poison";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.PROJECTILE)
      {
        source = "Projectile";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.STARVATION)
      {
        source = "Starvation";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.SUFFOCATION)
      {
        source = "Suffocation";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.SUICIDE)
      {
        source = "Suicide";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.VOID)
      {
        source = "Void";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.WITHER)
      {
        source = "Wither";
        reason = "-";
      }
      Get(damagee).Attacked(source, 
        event.getDamage(), null, reason);
    }
  }
  @EventHandler
  public void AddAttack(CDamageEvent event)
  {
    if (event.GetDamageePlayer() == null) {	
      return;
    }
    if (event.GetDamagerEntity(true) != null)
    {
   
      String reason = event.GetReason();
      if (reason == null) {
        if (event.GetDamagerPlayer(false) != null)
        {
          Player damager = event.GetDamagerPlayer(false);
          
          reason = "Fists";
          if (damager.getItemInHand() != null)
          {
        	  ItemStack itemStack = CraftItemStack.asNMSCopy(damager
              .getItemInHand());
            if (itemStack != null) {
              reason = 
                CraftItemStack.asNMSCopy(damager.getItemInHand()).getName();
            }
          }
        }
        else if (event.GetProjectile() != null)
        {
          if ((event.GetProjectile() instanceof Arrow)) {
            reason = "Archery";
          } else if ((event.GetProjectile() instanceof Fireball)) {
            reason = "Fireball";
          }
        }
      }
      if ((event.GetDamagerEntity(true) instanceof Player)) {
        Get((Player)event.GetDamagerEntity(true)).LastCombat = System.currentTimeMillis();
      }
      Get(event.GetDamageePlayer()).Attacked(utilEntity.getName(event.GetDamagerEntity(true)), 
        (int)event.GetDamage(), event.GetDamagerEntity(true), 
        reason.toString());
    }
    else
    {
      EntityDamageEvent.DamageCause cause = event.GetCause();
      
      String source = "?";
      String reason = "-";
      if (cause == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)
      {
        source = "Explosion";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.CONTACT)
      {
        source = "Cactus";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.CUSTOM)
      {
        source = "Custom";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.DROWNING)
      {
        source = "Water";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.ENTITY_ATTACK)
      {
        source = "Entity";
        reason = "Attack";
      }
      else if (cause == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)
      {
        source = "Explosion";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.FALL)
      {
        source = "Fall";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.FALLING_BLOCK)
      {
        source = "Falling Block";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.FIRE)
      {
        source = "Fire";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.FIRE_TICK)
      {
        source = "Fire";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.LAVA)
      {
        source = "Lava";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.LIGHTNING)
      {
        source = "Lightning";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.MAGIC)
      {
        source = "Magic";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.MELTING)
      {
        source = "Melting";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.POISON)
      {
        source = "Poison";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.PROJECTILE)
      {
        source = "Projectile";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.STARVATION)
      {
        source = "Starvation";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.SUFFOCATION)
      {
        source = "Suffocation";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.SUICIDE)
      {
        source = "Suicide";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.VOID)
      {
        source = "Void";
        reason = "-";
      }
      else if (cause == EntityDamageEvent.DamageCause.WITHER)
      {
        source = "Wither";
        reason = "-";
      }
      if (event.GetReason() != null) {
        reason = event.GetReason();
      }
      Get(event.GetDamageePlayer()).Attacked(source, 
        (int)event.GetDamage(), null, reason);
    }
  }
  
  @EventHandler(priority=EventPriority.LOWEST)
  public void HandleDeath(PlayerDeathEvent event)
  {
    event.setDeathMessage(null);
    if (!active.containsKey(event.getEntity())) {
      return;
    }
    CombatLog log = (CombatLog)active.remove(event.getEntity());
    log.SetDeathTime(System.currentTimeMillis());
    
    Get(event.getEntity().getName()).GetDeaths().addFirst(log);
    
    int assists = 0;
    for (int i = 0; i < log.GetAttackers().size(); i++) {
      if (((CombatC)log.GetAttackers().get(i)).IsPlayer()) {
        if (!utilTime.elapsed(((CombatC)log.GetAttackers().get(i)).GetLastDamage(), this.ExpireTime)) {
          if (log.GetKiller() == null)
          {
            log.SetKiller((CombatC)log.GetAttackers().get(i));
            
            CCombat killerClient = Get(((CombatC)log.GetAttackers().get(i)).GetName());
            if (killerClient != null) {
              killerClient.GetKills().addFirst(log);
            }
          }
          else
          {
            assists++;
            
            CCombat assistClient = Get(((CombatC)log.GetAttackers().get(i)).GetName());
            if (assistClient != null) {
              assistClient.GetAssists().addFirst(log);
            }
          }
        }
      }
    }
    log.SetAssists(assists);
    
    CDeathEvent deathEvent = new CDeathEvent(event, Get(event.getEntity().getName()), log);
    Clans.instance.getServer().getPluginManager().callEvent(deathEvent);
    if (deathEvent.GetBroadcastType() == DeathMessageType.Detailed)
    {
      for (Player cur : event.getEntity().getWorld().getPlayers())
      {
        String killedColor = log.GetKilledColor();
        
        String deadPlayer = killedColor + event.getEntity().getName();
        if (log.GetKiller() != null)
        {
          String killerColor = log.GetKillerColor();
          
          String killPlayer = killerColor + log.GetKiller().GetName();
          if (log.GetAssists() > 0) {
            killPlayer = killPlayer + " + " + log.GetAssists();
          }
          String weapon = log.GetKiller().GetLastDamageSource();
          cur.sendMessage(Format.main("Death", 
            deadPlayer + C.gray + " killed by " + 
            killPlayer + C.gray + " with " + 
            Format.item(weapon) + "."));
         
        }
        else if (log.GetAttackers().isEmpty())
        {
        	 cur.sendMessage(Format.main("Death", 
        	            deadPlayer + C.gray + "has died."));
        }
        else if ((log.GetLastDamager() != null) && (log.GetLastDamager().GetReason() != null) && (log.GetLastDamager().GetReason().length() > 1))
        {
        	cur.sendMessage(Format.main("Death", new StringBuilder(String.valueOf(deadPlayer))
                    .append(C.gray)
                    .append(" killed by ")
                    .append(Format.name(log.GetLastDamager()
                    .GetReason())).toString()) + 
                    C.gray + ".");
          
        }
        else
        {
        	 cur.sendMessage(Format.main("Death", new StringBuilder(String.valueOf(deadPlayer))
        	            .append(C.gray)
        	            .append(" killed by ")
        	            .append(Format.name(
        	            ((CombatC)log.GetAttackers().getFirst()).GetName())).toString()) + 
        	            C.gray + ".");
     
        }
      }
      event.getEntity().sendMessage("" + log.Display());
    }
    else if (deathEvent.GetBroadcastType() == DeathMessageType.Simple)
    {
      if (log.GetKiller() != null)
      {
        String killerColor = log.GetKillerColor();
        String killPlayer = killerColor + log.GetKiller().GetName();
        
        String killedColor = log.GetKilledColor();
        String deadPlayer = killedColor + event.getEntity().getName();
        if (log.GetAssists() > 0) {
          killPlayer = killPlayer + " + " + log.GetAssists();
        }
        String weapon = log.GetKiller().GetLastDamageSource();
        
        String killer = log.GetKiller().GetName();
        event.getEntity().sendMessage(Format.main("Death", "You killed " + Format.elem(deadPlayer) + " with " + Format.item(weapon) + "."));
        
        event.getEntity().sendMessage(Format.main("Death", killPlayer + C.gray + " killed you with " + Format.item(weapon) + "."));
      }
      else if (log.GetAttackers().isEmpty())
      {
    	  event.getEntity().sendMessage(Format.main("Death", "You have died."));
      }
      else
      {
    	 event.getEntity().sendMessage(Format.main("Death", new StringBuilder("You were killed by ")
          .append(Format.name(((CombatC)log.GetAttackers().getFirst()).GetName())).toString()) + C.dgray + ".");
      }
    }
  }
  
  @EventHandler
  public void ExpireOld(UpdateEvent event)
  {
    if (event.getType() != UpdateType.FAST) {
      return;
    }
    for (CombatLog log : active.values()) {
      log.ExpireOld();
    }
  }
  
  public void Add(Player player)
  {
    active.put(player, new CombatLog(player, 15000L));
  }
  
  @EventHandler(priority=EventPriority.HIGHEST)
  public void Clear(CCombatEvent event)
  {
    active.remove(event.GetPlayer());
  }
  
  public CombatLog Get(Player player)
  {
    if (!active.containsKey(player)) {
      Add(player);
    }
    return (CombatLog)active.get(player);
  }
  
  public long GetExpireTime()
  {
    return ExpireTime;
  }
  
  @EventHandler(priority=EventPriority.HIGHEST)
  public void ClearInactives(UpdateEvent event)
  {
    if (event.getType() == UpdateType.MIN_02)
    {
      Iterator<Player> removeIterator = removeList.iterator();
      while (removeIterator.hasNext())
      {
        Player player = (Player)removeIterator.next();
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
 
  public void DebugInfo(Player player)
  {
    StringBuilder nameBuilder = new StringBuilder();
    for (Player combats : active.keySet()) {
      if (!combats.isOnline())
      {
        if (nameBuilder.length() != 0) {
          nameBuilder.append(", ");
        }
        nameBuilder.append(combats.getName());
      }
    }
    player.sendMessage(Format.main("Test", nameBuilder.toString()));
  }
}
