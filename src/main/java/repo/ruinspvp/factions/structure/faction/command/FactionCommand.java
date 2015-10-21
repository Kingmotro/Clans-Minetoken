package repo.ruinspvp.factions.structure.faction.command;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.factions.structure.command.CommandManager;
import repo.ruinspvp.factions.structure.faction.FactionManager;
import repo.ruinspvp.factions.utilities.Format;

public class FactionCommand extends CommandManager {

    public FactionManager factionManager;

    public FactionCommand(JavaPlugin plugin, FactionManager factionManager) {
        super(plugin, "Faction", "ruinspvp.default");
        this.factionManager = factionManager;

        addCommand("create", new CreateCommand(this));
    }

    @Override
    public void help(Player player) {
        player.sendMessage(Format.main("Faction", "Commands:"));
        player.sendMessage(Format.help("/faction create {name}"));
    }
}
