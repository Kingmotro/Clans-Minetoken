package repo.minetoken.clans.combat;

import java.util.LinkedList;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import repo.minetoken.clans.utilities.Format;
import repo.minetoken.clans.utilities.utilTime;

public class CombatLog
{
  private LinkedList<CombatC> damager = new LinkedList<CombatC>();
  private CombatC player;
  private long expireTime;
  private long deathTime = 0L;
  private CombatC killer;
  private int assistants;
  private String killedColor = ChatColor.YELLOW + "";
  private String killerColor = ChatColor.YELLOW + "";
  protected CombatC LastDamager;
  public long LastDamaged;
  public long LastCombat;
  
  public CombatLog(Player player, long expireTime)
  {
    this.expireTime = expireTime;
    this.player = new CombatC(player.getName(), player);
  }
  
  public LinkedList<CombatC> GetAttackers()
  {
    return this.damager;
  }
  
  public CombatC GetPlayer()
  {
    return this.player;
  }
  
  public void Attacked(String damagerName, double damage, LivingEntity damagerEnt, String damageCause)
  {
    CombatC comp = GetEnemy(damagerName, damagerEnt);
    
    comp.AddDamage(damageCause, damage);
    
    this.LastDamager = comp;
    this.LastDamaged = System.currentTimeMillis();
    this.LastCombat = System.currentTimeMillis();
  }
  
  public CombatC GetEnemy(String name, LivingEntity ent)
  {
    ExpireOld();
    
    CombatC component = null;
    for (CombatC cur : this.damager) {
      if (cur.GetName().equals(name)) {
        component = cur;
      }
    }
    if (component != null)
    {
      this.damager.remove(component);
      this.damager.addFirst(component);
      return (CombatC)this.damager.getFirst();
    }
    this.damager.addFirst(new CombatC(name, ent));
    return (CombatC)this.damager.getFirst();
  }
  
  public void ExpireOld()
  {
    int expireFrom = -1;
    for (int i = 0; i < this.damager.size(); i++) {
      if (utilTime.elapsed(((CombatC)this.damager.get(i)).GetLastDamage(), this.expireTime))
      {
        expireFrom = i;
        break;
      }
    }
    if (expireFrom != -1) {
      while (this.damager.size() > expireFrom) {
        this.damager.remove(expireFrom);
      }
    }
  }
  
  public LinkedList<String> Display()
  {
    LinkedList<String> out = new LinkedList<String>();
    for (int i = 0; i < 8; i++) {
      if (i < this.damager.size()) {
        out.add(Format.desc("#" + i, ((CombatC)this.damager.get(i)).Display(this.deathTime)));
      }
    }
    return out;
  }
  
  public CombatC GetKiller()
  {
    return this.killer;
  }
  
  public void SetKiller(CombatC killer)
  {
    this.killer = killer;
  }
  
  public int GetAssists()
  {
    return this.assistants;
  }
  
  public void SetAssists(int assistants)
  {
    this.assistants = assistants;
  }
  
  public CombatC GetLastDamager()
  {
    return this.LastDamager;
  }
  
  public long GetDeathTime()
  {
    return this.deathTime;
  }
  
  public void SetDeathTime(long deathTime)
  {
    this.deathTime = deathTime;
  }
  
  public String GetKilledColor()
  {
    return this.killedColor;
  }
  
  public void SetKilledColor(String color)
  {
    this.killedColor = color;
  }
  
  public String GetKillerColor()
  {
    return this.killerColor;
  }
  
  public void SetKillerColor(String color)
  {
    this.killerColor = color;
  }
}