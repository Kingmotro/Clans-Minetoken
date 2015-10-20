package repo.ruinspvp.factions.structure.faction;

import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.factions.structure.database.Database;

public class FactionManager extends Database {

    JavaPlugin plugin;

    public FactionManager(JavaPlugin plugin) {
        super("root", "ThePyxel", "", "3306", "localhost");
        this.plugin = plugin;
    }
}
