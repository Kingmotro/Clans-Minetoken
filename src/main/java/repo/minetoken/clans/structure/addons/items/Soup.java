package repo.minetoken.clans.structure.addons.items;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import repo.minetoken.clans.structure.addons.Addon;
import repo.minetoken.clans.structure.addons.AddonManager;
import repo.minetoken.clans.structure.cooldowns.Cooldown;

public class Soup extends Addon {

	public Soup(AddonManager manager) {
		super(manager, "Soup");
	}

	@EventHandler
	public void onSoup(PlayerInteractEvent event)
	{

		Player player = event.getPlayer();
		
		if(player.getItemInHand().getType() == Material.MUSHROOM_SOUP) { 
			if(Cooldown.isCooling(player.getName(), "Soup")) {
				Cooldown.coolDurMessage(player, "Soup");
				return;
			}
		player.getWorld().playSound(player.getLocation(), Sound.EAT, 2.0F, 1.0F);
		player.getWorld().playEffect(player.getLocation(), Effect.STEP_SOUND, 39);
		player.getWorld().playEffect(player.getLocation(), Effect.STEP_SOUND, 40);

		player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 0));
		player.setFoodLevel(player.getFoodLevel() + 6);
		event.setCancelled(true);
		player.setItemInHand(null); 
		Cooldown.add(player.getName(), "Soup", 5, 5); 
		}
	}

}
