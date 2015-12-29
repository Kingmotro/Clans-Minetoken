package repo.minetoken.clans.structure.character;

import org.bukkit.plugin.java.JavaPlugin;

import repo.minetoken.clans.structure.cooldowns.HandleCooldowns;
import repo.minetoken.clans.structure.inventory.MenuManager;
import repo.minetoken.clans.structure.playerInfo.PlayerInfoItem;
import repo.minetoken.clans.structure.character.characters.Warrior;
import repo.minetoken.clans.structure.character.characters.check.CheckCharacter;
import repo.minetoken.clans.structure.character.menu.SkillSelector;
import repo.minetoken.clans.structure.character.menu.SkillSelectorManager;
import repo.minetoken.clans.structure.playerInfo.PlayerInfoMenu;

public class CharacterManager {

	public JavaPlugin plugin;
	public Characters[] characters;
	public SkillSelectorManager selector;
	public CheckCharacter checkC;
	public PlayerInfoItem playerInfo;
	
	public CharacterManager(JavaPlugin plugin, MenuManager menuManager) {
		this.plugin = plugin;
		characters = new Characters[] {new Warrior()};
		selector = new SkillSelectorManager();
		checkC = new CheckCharacter();
		playerInfo = new PlayerInfoItem();
		register();
		HandleCooldowns.handlecooldowns(); 
		menuManager.addMenu("characterm", new SkillSelector());
		menuManager.addMenu("profilem", new PlayerInfoMenu());
	}

	public void register() {
		for(Characters character : characters) {
			plugin.getServer().getPluginManager().registerEvents(character, plugin);
			for(Skills skills : character.getSkills()) {
				plugin.getServer().getPluginManager().registerEvents(skills, plugin);
				plugin.getServer().getPluginManager().registerEvents(selector, plugin);
				plugin.getServer().getPluginManager().registerEvents(checkC, plugin);
				plugin.getServer().getPluginManager().registerEvents(playerInfo, plugin);
			}
		}
	}

}
