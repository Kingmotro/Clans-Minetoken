package repo.minetoken.clans.structure.clan.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import repo.minetoken.clans.structure.command.SubCommand;
import repo.minetoken.clans.structure.clan.ClanManager;
import repo.minetoken.clans.structure.rank.enums.Result;
import repo.minetoken.clans.utilities.Format;

import java.util.UUID;

public class AcceptCommand implements SubCommand {

    ClanManager clanManager;

    public AcceptCommand(ClanManager clanManager) {
        this.clanManager = clanManager;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if(args.length == 0) {
            if(clanManager.invitedPlayer.containsKey(player.getUniqueId())) {
                if(clanManager.cPlayer.hasClan(player.getUniqueId()) == Result.FALSE) {
                    clanManager.cPlayer.joinFaction(player.getUniqueId(), player.getName(), clanManager.invitedPlayer.get(player.getUniqueId()).getFaction());
                    player.sendMessage(Format.main("Clans", "You have successfully joined " + clanManager.invitedPlayer.get(player.getUniqueId()).getFaction()));
                    try {
                        for(String uuid : clanManager.cPlayer.getPlayersInAFaction(clanManager.invitedPlayer.get(player.getUniqueId()).getFaction())) {
                            if(Bukkit.getPlayer(UUID.fromString(uuid)).isOnline()) {
                                Player fPlayer = Bukkit.getPlayer(UUID.fromString(uuid));
                                fPlayer.sendMessage(Format.main("Clans", player.getName() + " has joined the clan."));
                            }
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
        return Format.info("/clan accept");
    }

    @Override
    public String permission() {
        return "ruinspvp.default";
    }
}
