package repo.minetoken.clans.structure.character.skills;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import repo.minetoken.clans.structure.character.SkillType;
import repo.minetoken.clans.structure.character.Skills;
import repo.minetoken.clans.utilities.utilAction;

public class Swim extends Skills{

	public Swim() {
		super("Swim", new String[] {""}, SkillType.PASSIVE_A);
	}
	
	 @EventHandler(priority=EventPriority.HIGHEST)
	  public void Crouch(PlayerToggleSneakEvent event)
	  {
	    if (event.isCancelled()) {
	      return;
	    }
	    Player player = event.getPlayer();
	    if ((player.getLocation().getBlock().getTypeId() != 8) && (player.getLocation().getBlock().getTypeId() != 9)) {
	      return;
	    }
	   
	   
	    utilAction.velocity(player, 0.6D, 0.2D, 0.6D, false);
	    
	    player.getWorld().playSound(player.getLocation(), Sound.SPLASH, 0.3F, 2.0F);
	  }
	  
}
