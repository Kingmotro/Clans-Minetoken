package repo.ruinspvp.clans.structure.clan.command;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.clans.structure.command.CommandManager;
import repo.ruinspvp.clans.structure.clan.ClanManager;
import repo.ruinspvp.clans.utilities.Format;

public class ClanCommand extends CommandManager {

    public ClanManager clanManager;

    public ClanCommand(JavaPlugin plugin, ClanManager clanManager) {
        super(plugin, "Clan", "ruinspvp.default");
        this.clanManager = clanManager;

        addCommand("create", new CreateCommand(this));
        addCommand("invite", new InviteCommand(clanManager));
        addCommand("accept", new AcceptCommand(clanManager));
        addCommand("leave", new LeaveCommand(clanManager));
        addCommand("delete", new PlayerDeleteCommand(clanManager));
        addCommand("rank", new RankCommand(clanManager));
        addCommand("deny", new DenyCommand(clanManager));
        addCommand("info", new InfoCommand(clanManager));
        addCommand("kick", new KickCommand(clanManager));
    }

    @Override
    public void help(Player player) {
        player.sendMessage(Format.main("Clan", "Commands:"));
        player.sendMessage(Format.help("/clan create {name}", "Creates a clan your current ruin."));
        player.sendMessage(Format.help("/clan delete", "Requires Founder Rank, deletes your clan."));
        player.sendMessage(Format.help("/clan leave", "Removes you from your clan."));
        player.sendMessage(Format.help("/clan kick {player}", "Kick a player from your clan."));
        player.sendMessage(Format.help("/clan invite {player}", "Requires MOD Rank, invites player to the clan."));
        player.sendMessage(Format.help("/clan accept", "Accepts clan invitation."));
        player.sendMessage(Format.help("/clan deny", "Denies clan invitation."));
        player.sendMessage(Format.help("/clan rank", "Require Admin Rank, set player's rank in your clan."));
        player.sendMessage(Format.help("/clan info", "List all of your clan's info."));
    }
}
