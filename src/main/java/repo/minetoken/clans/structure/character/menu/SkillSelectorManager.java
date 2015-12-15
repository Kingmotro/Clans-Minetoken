package repo.minetoken.clans.structure.character.menu;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import repo.minetoken.clans.structure.character.CharacterManager;
import repo.minetoken.clans.utilities.UtilSound;
import repo.minetoken.clans.utilities.UtilSound.Pitch;

public class SkillSelectorManager implements Listener{
	
	
	public SkillSelectorManager(CharacterManager characterManager) {
		//todo
	}
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
        Action action = event.getAction();
    if (action == Action.RIGHT_CLICK_BLOCK) {
            if(event.getClickedBlock().getType() == Material.ENCHANTMENT_TABLE){
            	event.setCancelled(true);
            	new SkillSelector(null, 0).show(event.getPlayer());
            	UtilSound.play(event.getPlayer(), Sound.NOTE_PLING, Pitch.VERY_HIGH);
            }
        }
    }
}
