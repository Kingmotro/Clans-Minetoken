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
        addCommand("invite", new InviteCommand(factionManager));
        addCommand("accept", new AcceptCommand(factionManager));
        addCommand("leave", new LeaveCommand(factionManager));
        addCommand("delete", new PlayerDeleteCommand(factionManager));
        addCommand("rank", new RankCommand(factionManager));
    }

    @Override
    public void help(Player player) {
        player.sendMessage(Format.main("Factions", "Commands:"));
        player.sendMessage(Format.help("/faction create {name}"));
        player.sendMessage(Format.help("/faction invite {player}"));
        player.sendMessage(Format.help("/faction accept"));
    }
}
