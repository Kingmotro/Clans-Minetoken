package repo.minetoken.clans.structure.character.skills;

import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import repo.minetoken.clans.combat.events.CDamageEvent;
import repo.minetoken.clans.structure.character.Characters;
import repo.minetoken.clans.structure.character.SkillType;
import repo.minetoken.clans.structure.character.Skills;
import repo.minetoken.clans.utilities.C;
import repo.minetoken.clans.utilities.Format;
import repo.minetoken.clans.utilities.UpdateEvent;
import repo.minetoken.clans.utilities.UpdateType;
import repo.minetoken.clans.utilities.utilAction;
import repo.minetoken.clans.utilities.utilAlgebra;
import repo.minetoken.clans.utilities.utilBlock;
import repo.minetoken.clans.utilities.utilMath;
import repo.minetoken.clans.utilities.utilP;
import repo.minetoken.clans.utilities.utilWeapon;

public class Flash extends Skills{
	public Flash() {
		super("Flash", new String[] {""}, SkillType.AXE);
	}
	private HashMap<Player, Integer> flash = new HashMap();
	private HashSet<Player> active = new HashSet<Player>();
	@EventHandler
	public void onRightClick(PlayerInteractEvent event) {
		Skills[] skill = Characters.getSkills();
		final Player player = event.getPlayer();
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
			if(utilWeapon.hasAxe(player)){
				if(!Characters.thief.containsKey(player.getName())) {
					return;
				}

				active.add(player);
				Skill(player, 5);
				utilP.message(player, Format.main("Wizard", "You prepared to use " + C.lpurple + "Flash" + ""));
			}
	}

	@EventHandler
	  public void Recharge(UpdateEvent event)
	  {
	    for (Player cur : active) {
	      if (!flash.containsKey(cur))
	      {
	        flash.put(cur, Integer.valueOf(0));
	      }
	      else
	      {
	        int charges = ((Integer)flash.get(cur)).intValue();
	        if (charges < 3) {
	         
	            flash.put(cur, Integer.valueOf(charges + 1));
	            utilP.message(cur, Format.main("Thief", "Flash Charges: " + Format.elem(new StringBuilder().append(flash.get(cur)).toString())));
	          
	        }
	      }
	    }
	  }
	  
	  public void Skill(Player player, int level)
	  {
	 
	    flash.put(player, Integer.valueOf(((Integer)flash.get(player)).intValue() - 1));
	    
	    Block lastSmoke = player.getLocation().getBlock();
	    
	    double maxRange = 6.0D;
	    double curRange = 0.0D;
	    while (curRange <= maxRange)
	    {
	      Location newTarget = player.getLocation().add(new Vector(0.0D, 0.2D, 0.0D)).add(player.getLocation().getDirection().multiply(curRange));
	      if ((!utilBlock.airFoliage(newTarget.getBlock())) || 
	        (!utilBlock.airFoliage(newTarget.getBlock().getRelative(BlockFace.UP)))) {
	        break;
	      }
	      curRange += 0.2D;
	      if (!lastSmoke.equals(newTarget.getBlock())) {
	        lastSmoke.getWorld().playEffect(lastSmoke.getLocation(), Effect.SMOKE, 4);
	      }
	      lastSmoke = newTarget.getBlock();
	    }
	    curRange -= 0.4D;
	    if (curRange < 0.0D) {
	      curRange = 0.0D;
	    }
	    Location loc = player.getLocation().add(player.getLocation().getDirection().multiply(curRange).add(new Vector(0.0D, 0.4D, 0.0D)));
	    if (curRange > 0.0D) {
	    	player.teleport(loc);
	    }
	    player.setFallDistance(0.0F);
	    
	    utilP.message(player, Format.main("Thief", "Flash Charges: " + Format.elem(new StringBuilder().append(flash.get(player)).toString())));
	    
	    player.getWorld().playSound(player.getLocation(), Sound.WITHER_SHOOT, 0.4F, 1.2F);
	    player.getWorld().playSound(player.getLocation(), Sound.SILVERFISH_KILL, 1.0F, 1.6F);
	  }
	  
	  public void Reset(Player player)
	  {
	    flash.remove(player);
	    active.remove(player);
	  }
}
