package repo.minetoken.clans.structure.character.playermenus;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import repo.minetoken.clans.structure.character.CharacterManager;
import repo.minetoken.clans.structure.character.Skills;
import repo.minetoken.clans.structure.character.menu.Skills.WarriorSwordSkills;
import repo.minetoken.clans.structure.inventory.MenuManager;

public class enableJoinMenu implements Listener{
	
	public enableJoinMenu(CharacterManager characterManager) {
		
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		  Skills.loadSkills(e.getPlayer());
	}
}
