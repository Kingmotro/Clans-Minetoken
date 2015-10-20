package repo.ruinspvp.factions.structure.faction.command;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.factions.structure.command.CommandManager;
import repo.ruinspvp.factions.structure.faction.FactionManager;

public class FactionCommand extends CommandManager {

    public FactionManager factionManager;

    public FactionCommand(JavaPlugin plugin, FactionManager factionManager) {
        super(plugin, "faction", "ruinspvp.default");
        this.factionManager = factionManager;
    }

    @Override
    public void help(Player player) {

    }
}
