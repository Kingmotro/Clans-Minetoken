package repo.minetoken.clans.structure.character.skills;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;
import repo.minetoken.clans.Clans;
import repo.minetoken.clans.damage.DamageManager;
import repo.minetoken.clans.structure.cooldowns.Cooldown;
import repo.minetoken.clans.structure.character.Characters;
import repo.minetoken.clans.structure.character.SkillType;
import repo.minetoken.clans.structure.character.Skills;
import repo.minetoken.clans.utilities.C;
import repo.minetoken.clans.utilities.Format;
import repo.minetoken.clans.utilities.UtilSound;
import repo.minetoken.clans.utilities.UtilSound.Pitch;
import repo.minetoken.clans.utilities.utilAction;
import repo.minetoken.clans.utilities.utilAlgebra;
import repo.minetoken.clans.utilities.utilBlock;
import repo.minetoken.clans.utilities.utilP;
import repo.minetoken.clans.utilities.utilWeapon;

public class Fury extends Skills{
	ArrayList<Player> furyUsed = new ArrayList<Player>();
	ArrayList<Player> protect = new ArrayList<Player>();
	public static final BlockFace[] axis = { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };
	public static final BlockFace[] radial = { BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST };
	private HashMap<LivingEntity, Double> height = new HashMap<LivingEntity, Double>();
	private HashMap<LivingEntity, Long> live = new HashMap<LivingEntity, Long>();
	public Fury() {
		super("Fury", new String[] {""}, SkillType.AXE);
	}

	@EventHandler
	public void onRightClick(PlayerInteractEvent event) {

		final Player player = event.getPlayer();
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
			if(utilWeapon.hasAxe(player)){
				if(!Characters.warrior.containsKey(player.getName())) {
					return;
				}
				if(Cooldown.isCooling(player.getName(), "Fury Slam")) {
					Cooldown.coolDurMessage(player, "Fury Slam");
					return;
				}
				Material m = player.getLocation().getBlock().getType();
				if (m == Material.STATIONARY_WATER || m == Material.WATER) {
					player.sendMessage(Format.main("Warrior", "You cannot use " + ChatColor.LIGHT_PURPLE + "Fury Slam" + ChatColor.YELLOW + " in water!"));
					UtilSound.play(player, Sound.VILLAGER_HIT, Pitch.HIGH);
					return;
				}
				UtilSound.play(player, Sound.IRONGOLEM_THROW, Pitch.HIGH);
				Cooldown.add(player.getName(), "Fury Slam", "Warrior", 8, 8); 
				Vector vec = player.getLocation().getDirection();
				if (vec.getY() < 0.0D) {
					vec.setY(vec.getY() * -1.0D);
				}
				utilAction.velocity(player, vec, 1.0D, false, 0.0D, 1.0D, 1.0D, true);
				protect.add(player);
				live.put(player, Long.valueOf(System.currentTimeMillis()));
				height.put(player, Double.valueOf(player.getLocation().getY()));
				Clans.instance.getServer().getScheduler().scheduleSyncDelayedTask(Clans.instance, new Runnable() {
					  public void run() {
						  furyUsed.add(player);
					  }
					}, 5L);
				
				return;
			}

	}


	@EventHandler
	public void onFall(EntityDamageEvent event) {

		if(event.getEntity() instanceof Player){
			if(event.getCause() == DamageCause.FALL){
				float fDist = event.getEntity().getFallDistance();
				if(protect.contains(event.getEntity())){
					if(fDist >= 10){
						event.setCancelled(false);
						protect.remove(event.getEntity());
						return;
					}
				event.setCancelled(true);
				protect.remove(event.getEntity());
				}
			}
		}

	}

	public void FurySlam(final Player player)
	{
	
			live.remove(player);

			double mult = 0.5D;
			if (height.containsKey(player))
			{
				mult += (((Double)height.remove(player)).doubleValue() - player.getLocation().getY()) / 10.0D;
				mult = Math.min(mult, 2.0D);
				player.sendMessage(Format.main("Warrior", "Effectiveness: " + C.green + new StringBuilder(String.valueOf((int)(mult * 100.0D))).append("%").toString()));
			}
			int damage = 6;
			double range = 6.0D * mult;
			HashMap<LivingEntity, Double> targets = utilBlock.getInRadius(player.getLocation(), range);
			if(utilAction.isGrounded(player)){
				for (LivingEntity cur : targets.keySet()) {
					if (!cur.equals(player)) {
						if (utilAction.isGrounded(player))
						{
							DamageManager.NewDamageEvent(cur, player, null, 
							EntityDamageEvent.DamageCause.CUSTOM, damage * ((Double)targets.get(cur)).doubleValue() + 0.5D, false, false, false,
							player.getName(), "Fury Slam");

							utilAction.velocity(cur, 
									utilAlgebra.getTrajectory2d(player.getLocation().toVector(), cur.getLocation().toVector()), 
									1.8D * ((Double)targets.get(cur)).doubleValue() * mult, true, 0.0D, 0.4D + 1.0D * ((Double)targets.get(cur)).doubleValue() * mult, 1.6D * mult, true);

						
							if ((cur instanceof Player)) {
								utilP.message((Player)player, Format.main("Warrior", "You hit " + C.red + cur.getName() + C.yellow + " with " + C.lpurple + "Fury Slam" + "."));
								utilP.message((Player)cur, Format.main("Warrior", Format.name(player.getName()) + " hit you with " + C.lpurple + "Fury Slam" + "."));
							}
						}
					}
				}
				player.getWorld().playSound(player.getLocation(), Sound.ZOMBIE_WOOD, 2.0F, 0.2F);
				for (Block cur : utilBlock.getInRadius(player.getLocation(), Double.valueOf(4.0D)).keySet()) {
					if ((utilBlock.airFoliage(cur.getRelative(BlockFace.UP))) && (!utilBlock.airFoliage(cur))) {
						cur.getWorld().playEffect(cur.getLocation(), Effect.STEP_SOUND, cur.getTypeId());
					}
				}
				Clans.instance.getServer().getScheduler().scheduleSyncDelayedTask(Clans.instance, new Runnable() {
					  public void run() {
						  protect.add(player);
					  }
					}, 5L);
		}
	}

	@EventHandler
	public void onPlayerMove1(PlayerMoveEvent event) {

		Player player = event.getPlayer();
		if(furyUsed.contains(player)){
			
		}else{
			return;
		}
		if(player.isOnGround() && furyUsed.contains(player)){
	
			FurySlam(player);
			furyUsed.remove(player); 
			
		}
	}

	public void Reset(Player player)
	{
		live.remove(player);
		height.remove(player);
	}
}