package repo.minetoken.clans.combat;

import java.util.HashMap;
import java.util.LinkedList;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import repo.minetoken.clans.utilities.Format;
import repo.minetoken.clans.utilities.utilTime;

public class CombatC
{
  private boolean player = false;
  private LinkedList<CombatD> damage;
  protected String EntityName;
  protected long LastDamage = 0L;
  
  public CombatC(String name, LivingEntity ent)
  {
    EntityName = name;
    if (ent != null) {
      if ((ent instanceof Player)) {
        player = true;
      }
    }
  }
  
  public void AddDamage(String source, double dmg)
  {
    if (source == null) {
      source = "-";
    }
    GetDamage().addFirst(new CombatD(source, dmg));
    LastDamage = System.currentTimeMillis();
  }
  
  public String GetName()
  {
    if (EntityName.equals("Null")) {
      return "World";
    }
    return EntityName;
  }
  
  public LinkedList<CombatD> GetDamage()
  {
    if (damage == null) {
      damage = new LinkedList<CombatD>();
    }
    return damage;
  }
  
  public String GetReason()
  {
    if (damage.isEmpty()) {
      return null;
    }
    return ((CombatD)damage.get(0)).GetName();
  }
  
  public long GetLastDamage()
  {
    return LastDamage;
  }
  
  public int GetTotalDamage()
  {
    int total = 0;
    for (CombatD cur : GetDamage()) {
      total = (int)(total + cur.GetDamage());
    }
    return total;
  }
  
  public String GetBestWeapon()
  {
    HashMap<String, Integer> cumulative = new HashMap<String, Integer>();
    String weapon = null;
    int best = 0;
    for (CombatD cur : damage)
    {
      int dmg = 0;
      if (cumulative.containsKey(cur.GetName())) {
        dmg = ((Integer)cumulative.get(cur.GetName())).intValue();
      }
      cumulative.put(cur.GetName(), Integer.valueOf(dmg));
      if (dmg >= best) {
        weapon = cur.GetName();
      }
    }
    return weapon;
  }
  
  public String Display(long deathTime)
  {
    String time = "";
    if (deathTime == 0L) {
      time = 
      
        utilTime.convertString(System.currentTimeMillis() - LastDamage, 1, utilTime.TimeUnit.FIT) + " Ago";
    } else {
      time = 
    		  utilTime.convertString(deathTime - LastDamage, 1, utilTime.TimeUnit.FIT) + " Prior";
    }
    return 
    
      Format.name(EntityName) + " [" + Format.elem(new StringBuilder(String.valueOf(GetTotalDamage())).append("dmg").toString()) + "] [" + Format.elem(GetBestWeapon()) + "]  [" + Format.time(time) + "]";
  }
  
  public boolean IsPlayer()
  {
    return player;
  }
  
  public String GetLastDamageSource()
  {
    return ((CombatD)damage.getFirst()).GetName();
  }
}
