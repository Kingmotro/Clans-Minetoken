package repo.ruinspvp.clans.structure.character;

import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.clans.structure.character.characters.Warrior;

public class CharacterManager {

    public JavaPlugin plugin;
    public Character[] characters;

    public CharacterManager(JavaPlugin plugin) {
        this.plugin = plugin;
        characters = new Character[] {new Warrior()};
        registerCharacters();
    }

    public void registerCharacters() {
        for(Character character : characters) {
            plugin.getServer().getPluginManager().registerEvents(character, plugin);
            for(Skills skills : character.getSkills()) {
                plugin.getServer().getPluginManager().registerEvents(skills, plugin);
            }
        }
    }

}
