package repo.minetoken.clans.structure.character.skills;

import org.bukkit.Sound;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;
import repo.minetoken.clans.Clans;
import repo.minetoken.clans.structure.cooldowns.Cooldown;
import repo.minetoken.clans.structure.character.SkillType;
import repo.minetoken.clans.structure.character.Skills;
import repo.minetoken.clans.utilities.UtilSound;
import repo.minetoken.clans.utilities.UtilSound.Pitch;

public class FireBall extends Skills{

	public FireBall() {
		super("Fireball", new String[] {""}, SkillType.SWORD);
	}

	@EventHandler
	public void onRightClick(PlayerInteractEvent event) {

		final Player player = event.getPlayer();
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
			if(player.getItemInHand().getType().toString().toLowerCase().contains("sword")) {
				if(Cooldown.isCooling(player.getName(), "Fireball")) {
					Cooldown.coolDurMessage(player, "Fireball");
					return;
				}
				Vector direction = player.getEyeLocation().getDirection().multiply(2);
				Fireball fireball = player.getWorld().spawn(player.getEyeLocation().add(direction.getX(), direction.getY(), direction.getZ()), Fireball.class);
				fireball.setShooter(player);
				fireball.setYield(5.0F);
				fireball.setMetadata("fireball", new FixedMetadataValue(Clans.instance, Boolean.valueOf(true)));
				UtilSound.play(player, Sound.GHAST_FIREBALL, Pitch.VERY_LOW); 
				Cooldown.add(player.getName(), "Fireball", 10, 10); 
				
			}
		
	}

}
