package repo.minetoken.clans.structure.combat;

import java.util.HashMap;
import java.util.LinkedList;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import repo.minetoken.clans.utilities.Format;
import repo.minetoken.clans.utilities.UtilTime;

public class CombatC {

    private boolean player = false;
    private LinkedList<CombatD> damage;
    protected String entityName;
    protected long LastDamage = 0L;

    public CombatC(String name, LivingEntity ent) {
        entityName = name;
        if (ent != null) {
            if ((ent instanceof Player)) {
                player = true;
            }
        }
    }

    public void addDamage(String source, double dmg) {
        if (source == null) {
            source = "-";
        }
        getDamage().addFirst(new CombatD(source, dmg));
        LastDamage = System.currentTimeMillis();
    }

    public String getName() {
        if (entityName.equals("Null")) {
            return "World";
        }
        return entityName;
    }

    public LinkedList<CombatD> getDamage() {
        if (damage == null) {
            damage = new LinkedList<>();
        }
        return damage;
    }

    public String getReason() {
        if (damage.isEmpty()) {
            return null;
        }
        return damage.get(0).getName();
    }

    public long getLastDamage() {
        return LastDamage;
    }

    public int getTotalDamage() {
        int total = 0;
        for (CombatD cur : getDamage()) {
            total = (int) (total + cur.getDamage());
        }
        return total;
    }

    public String getBestWeapon() {
        HashMap<String, Integer> cumulative = new HashMap<String, Integer>();
        String weapon = null;
        int best = 0;
        for (CombatD cur : damage) {
            int dmg = 0;
            if (cumulative.containsKey(cur.getName())) {
                dmg = cumulative.get(cur.getName()).intValue();
            }
            cumulative.put(cur.getName(), Integer.valueOf(dmg));
            if (dmg >= best) {
                weapon = cur.getName();
            }
        }
        return weapon;
    }

    public String display(long deathTime) {
        String time;
        if (deathTime == 0L) {
            time = UtilTime.convertString(System.currentTimeMillis() - LastDamage, 1, UtilTime.TimeUnit.FIT) + " Ago";
        } else {
            time = UtilTime.convertString(deathTime - LastDamage, 1, UtilTime.TimeUnit.FIT) + " Prior";
        }
        return Format.highlight(entityName) + " [" + Format.highlight(new StringBuilder(String.valueOf(getTotalDamage())).append("dmg").toString()) + "] [" + Format.highlight(getBestWeapon()) + "]  [" + Format.highlight(time) + "]";
    }

    public boolean IsPlayer() {
        return player;
    }

    public String getLastDamageSource() {
        return  damage.getFirst().getName();
    }
}
