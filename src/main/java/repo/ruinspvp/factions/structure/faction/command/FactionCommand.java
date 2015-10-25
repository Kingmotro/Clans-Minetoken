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
        addCommand("deny", new DenyCommand(factionManager));
        addCommand("info", new InfoCommand(factionManager));
        addCommand("kick", new KickCommand(factionManager));
    }

    @Override
    public void help(Player player) {
        player.sendMessage(Format.main("Factions", "Commands:"));
        player.sendMessage(Format.help("/faction create {name}", "Creates a faction your current ruin."));
        player.sendMessage(Format.help("/faction delete", "Requires Founder Rank, deletes your faction."));
        player.sendMessage(Format.help("/faction leave", "Removes you from your faction."));
        player.sendMessage(Format.help("/faction kick {player}", "Kick a player from your faction."));
        player.sendMessage(Format.help("/faction invite {player}", "Requires MOD Rank, invites player to the faction."));
        player.sendMessage(Format.help("/faction accept", "Accepts faction invitation."));
        player.sendMessage(Format.help("/faction deny", "Denies faction invitation."));
        player.sendMessage(Format.help("/faction rank", "Require Admin Rank, set player's rank in your faction."));
        player.sendMessage(Format.help("/faction info", "List all of your faction's info."));
    }
}
