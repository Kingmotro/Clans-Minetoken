package repo.minetoken.clans.structure.combat.events;

import java.util.ArrayList;
import java.util.HashMap;

import repo.minetoken.clans.structure.damage.DamageChange;

import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private EntityDamageEvent.DamageCause eventCause;
    private double initialDamage;
    private ArrayList<DamageChange> damageMult = new ArrayList<>();
    private ArrayList<DamageChange> damageMod = new ArrayList<>();
    private ArrayList<String> cancellers = new ArrayList<>();
    private HashMap<String, Double> knockbackMod = new HashMap<>();
    private LivingEntity damagedEntity;
    private Player damagedPlayer;
    private LivingEntity damagerEntity;
    private Player damagerPlayer;
    private Projectile projectile;
    private boolean ignoreArmor = true;
    private boolean ignoreRate = false;
    private boolean knockback = true;
    private boolean damageeBrute = false;
    private boolean damageToLevel = true;

    public DamageEvent(LivingEntity damaged, LivingEntity damager, Projectile projectile, EntityDamageEvent.DamageCause cause, double damage, boolean knockback, boolean ignoreRate, boolean ignoreArmor, String initialSource, String initialReason, boolean cancelled) {
        eventCause = cause;
        if ((initialSource == null) || (initialReason == null)) {
            initialDamage = damage;
        }
        damagedEntity = damaged;
        if ((this.damagedEntity != null) && ((this.damagedEntity instanceof Player))) {
            damagedPlayer = ((Player) this.damagedEntity);
        }
        damagerEntity = damager;
        if ((this.damagerEntity != null) && ((this.damagerEntity instanceof Player))) {
            damagerPlayer = ((Player) this.damagerEntity);
        }
        this.projectile = projectile;

        this.knockback = knockback;
        this.ignoreRate = ignoreRate;
        this.ignoreArmor = ignoreArmor;
        if ((initialSource != null) && (initialReason != null)) {
            addMod(initialSource, initialReason, damage, true);
        }
        if (this.eventCause == EntityDamageEvent.DamageCause.FALL) {
            ignoreArmor = true;
        }
        if (cancelled) {
            setCancelled("Pre-Cancelled");
        }
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public void addMult(String source, String reason, double mod, boolean useAttackName) {
        damageMult.add(new DamageChange(source, reason, mod, useAttackName));
    }

    public void addMod(String source, String reason, double mod, boolean useAttackName) {
        damageMod.add(new DamageChange(source, reason, mod, useAttackName));
    }

    public void addKnockback(String reason, double d) {
        knockbackMod.put(reason, Double.valueOf(d));
    }

    public boolean isCancelled() {
        return !this.cancellers.isEmpty();
    }

    public void setCancelled(String reason) {
        cancellers.add(reason);
    }

    public double getDamage() {
        double damage = getDamageInitial();
        for (DamageChange mult : damageMult) {
            damage *= mult.getDamage();
        }
        for (DamageChange mult : damageMod) {
            damage += mult.getDamage();
        }
        return damage;
    }

    public LivingEntity getDamageeEntity() {
        return damagedEntity;
    }

    public Player getDamageePlayer() {
        return damagedPlayer;
    }

    public LivingEntity getDamagerEntity(boolean ranged) {
        if (ranged) {
            return damagerEntity;
        }
        if (this.projectile == null) {
            return damagerEntity;
        }
        return null;
    }

    public Player getDamagerPlayer(boolean ranged) {
        if (ranged) {
            return damagerPlayer;
        }
        if (this.projectile == null) {
            return damagerPlayer;
        }
        return null;
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public EntityDamageEvent.DamageCause getCause() {
        return eventCause;
    }

    public double getDamageInitial() {
        return initialDamage;
    }

    public void setIgnoreArmor(boolean ignore) {
        ignoreArmor = ignore;
    }

    public void setIgnoreRate(boolean ignore) {
        ignoreRate = ignore;
    }

    public void setKnockback(boolean knockback) {
        this.knockback = knockback;
    }

    public void setBrute() {
        damageeBrute = true;
    }

    public boolean isBrute() {
        return damageeBrute;
    }

    public String getReason() {
        String reason = "";
        for (DamageChange change : damageMod) {
            if (change.useReason()) {
                reason = reason + ChatColor.RED + change.getReason() + ChatColor.GRAY + ", ";
            }
        }
        if (reason.length() > 0) {
            reason = reason.substring(0, reason.length() - 2);
            return reason;
        }
        return null;
    }

    public boolean isKnockback() {
        return knockback;
    }

    public boolean ignoreRate() {
        return ignoreRate;
    }

    public boolean ignoreArmor() {
        return ignoreArmor;
    }

    public void setDamager(LivingEntity ent) {
        if (ent == null) {
            return;
        }
        damagerEntity = ent;

        damagerPlayer = null;
        if ((ent instanceof Player)) {
            damagerPlayer = ((Player) ent);
        }
    }

    public ArrayList<DamageChange> getDamageMod() {
        return damageMod;
    }

    public ArrayList<DamageChange> getDamageMult() {
        return damageMult;
    }

    public HashMap<String, Double> getKnockback() {
        return knockbackMod;
    }

    public ArrayList<String> getCancellers() {
        return cancellers;
    }

    public void setDamageToLevel(boolean val) {
        damageToLevel = val;
    }

    public boolean displayDamageToLevel() {
        return damageToLevel;
    }
}
