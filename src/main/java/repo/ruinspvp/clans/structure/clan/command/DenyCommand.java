package repo.ruinspvp.clans.structure.clan.command;

import org.bukkit.entity.Player;
import repo.ruinspvp.clans.structure.command.SubCommand;
import repo.ruinspvp.clans.structure.clan.ClanManager;
import repo.ruinspvp.clans.structure.rank.enums.Result;
import repo.ruinspvp.clans.utilities.Format;

public class DenyCommand implements SubCommand {

    ClanManager clanManager;

    public DenyCommand(ClanManager clanManager) {
        this.clanManager = clanManager;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (args.length == 0) {
            if (clanManager.invitedPlayer.containsKey(player.getUniqueId())) {
                if (clanManager.cPlayer.hasClan(player.getUniqueId()) == Result.FALSE) {
                    player.sendMessage(Format.main("Clans", "You have denied the clan " + clanManager.invitedPlayer.get(player.getUniqueId()).getFaction()));
                    try {
                        if(clanManager.invitedPlayer.get(player.getUniqueId()).getPlayer().isOnline()) {
                            clanManager.invitedPlayer.get(player.getUniqueId()).getPlayer().sendMessage(Format.main("Clans", player.getName() + " has denied to join the clan."));
                        }
                    } catch (Exception ignore) {}
                    clanManager.invitedPlayer.remove(player.getUniqueId());
                } else {
                    player.sendMessage(Format.main("Clans", "You must leave your current clan to join."));
                }
            } else {
                player.sendMessage(Format.main("Clans", "You have no pending clan request."));
            }
        } else {
            player.sendMessage(help());
        }
        return false;
    }

    @Override
    public String help() {
        return Format.info("/clan deny");
    }

    @Override
    public String permission() {
        return "ruinspvp.default";
    }
}
