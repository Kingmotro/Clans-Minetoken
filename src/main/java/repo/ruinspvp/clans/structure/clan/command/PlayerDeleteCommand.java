package repo.ruinspvp.clans.structure.clan.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import repo.ruinspvp.clans.structure.command.SubCommand;
import repo.ruinspvp.clans.structure.clan.ClanManager;
import repo.ruinspvp.clans.structure.clan.enums.ClanRanks;
import repo.ruinspvp.clans.structure.rank.enums.Result;
import repo.ruinspvp.clans.utilities.Format;

public class PlayerDeleteCommand implements SubCommand {

    public ClanManager clanManager;

    public PlayerDeleteCommand(ClanManager clanManager) {
        this.clanManager = clanManager;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if(args.length == 0) {
            if(clanManager.cPlayer.hasClan(player.getUniqueId()) == Result.TRUE) {
                if(clanManager.cPlayer.getCRank(player.getUniqueId()).getPermLevel() >= ClanRanks.FOUNDER.getPermLevel()) {
                    Bukkit.broadcastMessage(Format.main("Clans", player.getName() + " has disbanded the clan " + clanManager.cPlayer.getClan(player.getUniqueId())));
                    clanManager.CClans.deleteClan(clanManager.cPlayer.getClan(player.getUniqueId()));
                } else {
                    player.sendMessage(Format.main("Clans", "You must be the founder of this clan to delete it. If you want this clan deleted contact a Server Admin."));
                }
            } else {
                player.sendMessage(Format.main("Clans", "You have no clan."));
            }
        } else {
            player.sendMessage(help());
        }
        return false;
    }

    @Override
    public String help() {
        return Format.info("/clan delete");
    }

    @Override
    public String permission() {
        return "ruinspvp.default";
    }
}
