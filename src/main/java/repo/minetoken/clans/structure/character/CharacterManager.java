package repo.minetoken.clans.structure.character;

import org.bukkit.plugin.java.JavaPlugin;

import repo.minetoken.clans.structure.cooldowns.HandleCooldowns;
import repo.minetoken.clans.structure.inventory.MenuManager;
import repo.minetoken.clans.structure.character.characters.Warrior;
import repo.minetoken.clans.structure.character.menu.ShopSelector;
import repo.minetoken.clans.structure.character.menu.ShopSelectorManager;
import repo.minetoken.clans.structure.character.menu.SkillSelector;
import repo.minetoken.clans.structure.character.menu.SkillSelectorManager;

public class CharacterManager {

	public JavaPlugin plugin;
	public Characters[] characters;
	public SkillSelectorManager selector;
	public ShopSelectorManager shopselector;
	public MenuManager menuManager;
	
	public CharacterManager(JavaPlugin plugin, MenuManager menuManager) {
		this.plugin = plugin;
		characters = new Characters[] {new Warrior()};
		selector = new SkillSelectorManager(this);
		shopselector = new ShopSelectorManager(this);
		
		registerCharacters();
		HandleCooldowns.handlecooldowns();
		menuManager.addMenu("Select a Character", new SkillSelector(null, 0));
		menuManager.addMenu("Shop", new ShopSelector(null, 0));
	}

	public void registerCharacters() {
		for(Characters character : characters) {
			plugin.getServer().getPluginManager().registerEvents(character, plugin);
			for(Skills skills : character.getSkills()) {
				plugin.getServer().getPluginManager().registerEvents(skills, plugin);
				plugin.getServer().getPluginManager().registerEvents(selector, plugin);
				plugin.getServer().getPluginManager().registerEvents(shopselector, plugin);
			}
		}
	}

}
