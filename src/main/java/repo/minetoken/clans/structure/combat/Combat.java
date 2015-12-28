package repo.minetoken.clans.structure.combat;

import java.util.LinkedList;
import java.util.WeakHashMap;

import org.bukkit.entity.LivingEntity;

public class Combat {

    private LinkedList<CombatLog> kills = new LinkedList<CombatLog>();
    private LinkedList<CombatLog> assists = new LinkedList<CombatLog>();
    private LinkedList<CombatLog> deaths = new LinkedList<CombatLog>();
    private WeakHashMap<LivingEntity, Long> lastHurt = new WeakHashMap<LivingEntity, Long>();
    private long lastHurtByOther = 0L;

    public LinkedList<CombatLog> GetKills() {
        return kills;
    }

    public LinkedList<CombatLog> GetAssists() {
        return assists;
    }

    public LinkedList<CombatLog> GetDeaths() {
        return deaths;
    }

    public boolean canBeHurtBy(LivingEntity damager) {
        if (damager != null) {
            return true;
        }
        if (System.currentTimeMillis() - lastHurtByOther > 250L) {
            lastHurtByOther = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean canHurt(LivingEntity damagee) {
        if (damagee == null) {
            return true;
        }
        if (!lastHurt.containsKey(damagee)) {
            lastHurt.put(damagee, Long.valueOf(System.currentTimeMillis()));
            return true;
        }
        if (System.currentTimeMillis() - lastHurt.get(damagee).longValue() > 400L) {
            lastHurt.put(damagee, Long.valueOf(System.currentTimeMillis()));
            return true;
        }
        return false;
    }
}
