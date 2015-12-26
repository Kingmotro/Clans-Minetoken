package repo.minetoken.clans.structure.character;

import org.bukkit.plugin.java.JavaPlugin;

import repo.minetoken.clans.structure.cooldowns.HandleCooldowns;
import repo.minetoken.clans.structure.inventory.MenuManager;
import repo.minetoken.clans.structure.playerInfo.playerInfoItem;
import repo.minetoken.clans.structure.character.characters.Warrior;
import repo.minetoken.clans.structure.character.characters.check.CheckCharacter;
import repo.minetoken.clans.structure.character.menu.ShopSelector;
import repo.minetoken.clans.structure.character.menu.ShopSelectorManager;
import repo.minetoken.clans.structure.character.menu.SkillSelector;
import repo.minetoken.clans.structure.character.menu.SkillSelectorManager;
import repo.minetoken.clans.structure.character.menu.Skills.DisplaySkills;
import repo.minetoken.clans.structure.character.menu.Skills.WarriorSkillsManager;
import repo.minetoken.clans.structure.character.playermenus.enableJoinMenu;
import repo.minetoken.clans.structure.character.menu.Skills.WarriorSkills;

public class CharacterManager {

	public JavaPlugin plugin;
	public Characters[] characters;
	public SkillSelectorManager selector;
	public ShopSelectorManager shopselector;

	public MenuManager menuManager;
	public WarriorSkillsManager warriorskills;
	public CheckCharacter checkC;
	public playerInfoItem playerInfo;
	public enableJoinMenu joinM;
	
	public CharacterManager(JavaPlugin plugin, MenuManager menuManager) {
		this.plugin = plugin;
		characters = new Characters[] {new Warrior()};
		selector = new SkillSelectorManager(this);
		shopselector = new ShopSelectorManager(this);
		warriorskills = new WarriorSkillsManager(this);
		checkC = new CheckCharacter(this);
		playerInfo = new playerInfoItem(this);
		joinM = new enableJoinMenu(this);
		registerCharacters();
		HandleCooldowns.handlecooldowns(); 
		menuManager.addMenu("Select a Character", new SkillSelector());
		menuManager.addMenu("Shop", new ShopSelector());
		menuManager.addMenu("Edit Guardian Skills", new DisplaySkills(null, 0, "Guardian"));
		menuManager.addMenu("Edit Wizard Skills", new DisplaySkills(null, 0, "Wizard"));
		menuManager.addMenu("Edit Warrior Skills", new DisplaySkills(null, 0, "Warrior"));
		menuManager.addMenu("Edit Thief Skills", new DisplaySkills(null, 0, "Thief"));
		menuManager.addMenu("Edit Gatherer Skills", new DisplaySkills(null, 0, "Gatherer"));
		
		
		registerCharacters();
		HandleCooldowns.handlecooldowns();
		menuManager.addMenu("Select a Character", new SkillSelector());
		menuManager.addMenu("Shop", new ShopSelector());
		menuManager.addMenu("Warrior", new WarriorSkills());
	}

	public void registerCharacters() {
		for(Characters character : characters) {
			plugin.getServer().getPluginManager().registerEvents(character, plugin);
			for(Skills skills : character.getSkills()) {
				plugin.getServer().getPluginManager().registerEvents(skills, plugin);
				plugin.getServer().getPluginManager().registerEvents(selector, plugin);
				plugin.getServer().getPluginManager().registerEvents(shopselector, plugin);
				plugin.getServer().getPluginManager().registerEvents(warriorskills, plugin);
				plugin.getServer().getPluginManager().registerEvents(checkC, plugin);
				plugin.getServer().getPluginManager().registerEvents(playerInfo, plugin);
				plugin.getServer().getPluginManager().registerEvents(joinM, plugin);

			}
		}
	}

}
