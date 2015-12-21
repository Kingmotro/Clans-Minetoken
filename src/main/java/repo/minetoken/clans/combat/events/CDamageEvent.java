package repo.minetoken.clans.combat.events;

import java.util.ArrayList;
import java.util.HashMap;
import repo.minetoken.clans.damage.DamageChange;
import repo.minetoken.clans.utilities.C;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;

public class CDamageEvent
extends Event
{
	private static final HandlerList handlers = new HandlerList();
	private EntityDamageEvent.DamageCause eventCause;
	private double initialDamage;
	private ArrayList<DamageChange> damageMult = new ArrayList<DamageChange>();
	private ArrayList<DamageChange> damageMod = new ArrayList<DamageChange>();
	private ArrayList<String> cancellers = new ArrayList<String>();
	private HashMap<String, Double> knockbackMod = new HashMap<String, Double>();
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

	public CDamageEvent(LivingEntity damaged, LivingEntity damager, Projectile projectile, EntityDamageEvent.DamageCause cause, double damage, boolean knockback, boolean ignoreRate, boolean ignoreArmor, String initialSource, String initialReason, boolean cancelled)
	{
		eventCause = cause;
		if ((initialSource == null) || (initialReason == null)) {
			initialDamage = damage;
		}
		damagedEntity = damaged;
		if ((this.damagedEntity != null) && ((this.damagedEntity instanceof Player))) {
			damagedPlayer = ((Player)this.damagedEntity);
		}
		damagerEntity = damager;
		if ((this.damagerEntity != null) && ((this.damagerEntity instanceof Player))) {
			damagerPlayer = ((Player)this.damagerEntity);
		}
		this.projectile = projectile;

		this.knockback = knockback;
		this.ignoreRate = ignoreRate;
		this.ignoreArmor = ignoreArmor;
		if ((initialSource != null) && (initialReason != null)) {
			AddMod(initialSource, initialReason, damage, true);
		}
		if (this.eventCause == EntityDamageEvent.DamageCause.FALL) {
			ignoreArmor = true;
		}
		if (cancelled) {
			SetCancelled("Pre-Cancelled");
		}

	}

	public HandlerList getHandlers()
	{
		return handlers;
	}

	public static HandlerList getHandlerList()
	{
		return handlers;
	}

	public void AddMult(String source, String reason, double mod, boolean useAttackName)
	{
		damageMult.add(new DamageChange(source, reason, mod, useAttackName));
	}

	public void AddMod(String source, String reason, double mod, boolean useAttackName)
	{
		damageMod.add(new DamageChange(source, reason, mod, useAttackName));
	}

	public void AddKnockback(String reason, double d)
	{
		knockbackMod.put(reason, Double.valueOf(d));
	}

	public boolean IsCancelled()
	{
		return !this.cancellers.isEmpty();
	}

	public void SetCancelled(String reason)
	{
		cancellers.add(reason);
	}

	public double GetDamage()
	{
		double damage = GetDamageInitial();
		for (DamageChange mult :damageMult) {
			damage *= mult.GetDamage();
		}
		for (DamageChange mult :damageMod) {
			damage += mult.GetDamage();
		}
		return damage;
	}

	public LivingEntity GetDamageeEntity()
	{
		return damagedEntity;
	}

	public Player GetDamageePlayer()
	{
		return damagedPlayer;
	}

	public LivingEntity GetDamagerEntity(boolean ranged)
	{
		if (ranged) {
			return damagerEntity;
		}
		if (this.projectile == null) {
			return damagerEntity;
		}
		return null;
	}

	public Player GetDamagerPlayer(boolean ranged)
	{
		if (ranged) {
			return damagerPlayer;
		}
		if (this.projectile == null) {
			return damagerPlayer;
		}
		return null;
	}

	public Projectile GetProjectile()
	{
		return projectile;
	}

	public EntityDamageEvent.DamageCause GetCause()
	{
		return eventCause;
	}

	public double GetDamageInitial()
	{
		return initialDamage;
	}

	public void SetIgnoreArmor(boolean ignore)
	{
		ignoreArmor = ignore;
	}

	public void SetIgnoreRate(boolean ignore)
	{
		ignoreRate = ignore;
	}

	public void SetKnockback(boolean knockback)
	{
		knockback = knockback;
	}

	public void SetBrute()
	{
		damageeBrute = true;
	}

	public boolean IsBrute()
	{
		return damageeBrute;
	}

	public String GetReason()
	{
		String reason = "";
		for (DamageChange change :damageMod) {
			if (change.UseReason()) {
				reason = reason + C.red + change.GetReason() + ChatColor.GRAY + ", ";
			}
		}
		if (reason.length() > 0)
		{
			reason = reason.substring(0, reason.length() - 2);
			return reason;
		}
		return null;
	}

	public boolean IsKnockback()
	{
		return knockback;
	}

	public boolean IgnoreRate()
	{
		return ignoreRate;
	}

	public boolean IgnoreArmor()
	{
		return ignoreArmor;
	}

	public void SetDamager(LivingEntity ent)
	{
		if (ent == null) {
			return;
		}
		damagerEntity = ent;

		damagerPlayer = null;
		if ((ent instanceof Player)) {
			damagerPlayer = ((Player)ent);
		}
	}

	public ArrayList<DamageChange> GetDamageMod()
	{
		return damageMod;
	}

	public ArrayList<DamageChange> GetDamageMult()
	{
		return damageMult;
	}

	public HashMap<String, Double> GetKnockback()
	{
		return knockbackMod;
	}

	public ArrayList<String> GetCancellers()
	{
		return cancellers;
	}

	public void SetDamageToLevel(boolean val)
	{
		damageToLevel = val;
	}

	public boolean DisplayDamageToLevel()
	{
		return damageToLevel;
	}
}
