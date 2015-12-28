package repo.minetoken.clans.structure.combat;

import java.util.LinkedList;

import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import repo.minetoken.clans.utilities.Format;
import repo.minetoken.clans.utilities.UtilTime;

public class CombatLog {

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

    public CombatLog(Player player, long expireTime) {
        this.expireTime = expireTime;
        this.player = new CombatC(player.getName(), player);
    }

    public LinkedList<CombatC> GetAttackers() {
        return this.damager;
    }

    public CombatC getPlayer() {
        return this.player;
    }

    public void attacked(String damagerName, double damage, LivingEntity damagerEnt, String damageCause) {
        CombatC comp = getEnemy(damagerName, damagerEnt);

        comp.addDamage(damageCause, damage);

        this.LastDamager = comp;
        this.LastDamaged = System.currentTimeMillis();
        this.LastCombat = System.currentTimeMillis();
    }

    public CombatC getEnemy(String name, LivingEntity ent) {
        expireOld();

        CombatC component = null;
        for (CombatC cur : this.damager) {
            if (cur.getName().equals(name)) {
                component = cur;
            }
        }
        if (component != null) {
            this.damager.remove(component);
            this.damager.addFirst(component);
            return this.damager.getFirst();
        }
        this.damager.addFirst(new CombatC(name, ent));
        return this.damager.getFirst();
    }

    public void expireOld() {
        int expireFrom = -1;
        for (int i = 0; i < this.damager.size(); i++) {
            if (UtilTime.elapsed(this.damager.get(i).getLastDamage(), this.expireTime)) {
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

    public LinkedList<String> display() {
        LinkedList<String> out = new LinkedList<String>();
        for (int i = 0; i < 8; i++) {
            if (i < this.damager.size()) {
                out.add(Format.desc("#" + i, this.damager.get(i).display(this.deathTime)));
            }
        }
        return out;
    }

    public CombatC getKiller() {
        return this.killer;
    }

    public void setKiller(CombatC killer) {
        this.killer = killer;
    }

    public int getAssists() {
        return this.assistants;
    }

    public void setAssists(int assistants) {
        this.assistants = assistants;
    }

    public CombatC getLastDamager() {
        return this.LastDamager;
    }

    public long getDeathTime() {
        return this.deathTime;
    }

    public void setDeathTime(long deathTime) {
        this.deathTime = deathTime;
    }

    public String getKilledColor() {
        return this.killedColor;
    }

    public void setKilledColor(String color) {
        this.killedColor = color;
    }

    public String getKillerColor() {
        return this.killerColor;
    }

    public void setKillerColor(String color) {
        this.killerColor = color;
    }
}