package repo.ruinspvp.factions.structure.faction.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import repo.ruinspvp.factions.structure.command.SubCommand;
import repo.ruinspvp.factions.structure.faction.FactionManager;
import repo.ruinspvp.factions.structure.rank.enums.Result;
import repo.ruinspvp.factions.utilities.Format;

import java.util.UUID;

public class AcceptCommand implements SubCommand {

    FactionManager factionManager;

    public AcceptCommand(FactionManager factionManager) {
        this.factionManager = factionManager;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if(args.length == 0) {
            if(factionManager.invitedPlayer.containsKey(player.getUniqueId())) {
                if(factionManager.fPlayer.hasFaction(player.getUniqueId()) == Result.FALSE) {
                    factionManager.fPlayer.joinFaction(player.getUniqueId(), player.getName(), factionManager.invitedPlayer.get(player.getUniqueId()).getFaction());
                    player.sendMessage(Format.main("Factions", "You have successfully joined " + factionManager.invitedPlayer.get(player.getUniqueId()).getFaction()));
                    try {
                        for(String uuid : factionManager.fPlayer.getPlayersInAFaction(factionManager.invitedPlayer.get(player.getUniqueId()).getFaction())) {
                            if(Bukkit.getPlayer(UUID.fromString(uuid)).isOnline()) {
                                Player fPlayer = Bukkit.getPlayer(UUID.fromString(uuid));
                                fPlayer.sendMessage(Format.main("Factions", player.getName() + " has joined the faction."));
                            }
                        }
                    } catch (Exception ignore) {}
                    factionManager.invitedPlayer.remove(player.getUniqueId());
                } else {
                    player.sendMessage(Format.main("Factions", "You must leave your current faction to join."));
                }
            } else {
                player.sendMessage(Format.main("Factions", "You have no pending faction request."));
            }
        } else {
            player.sendMessage(help());
        }
        return false;
    }

    @Override
    public String help() {
        return Format.help("/faction accept");
    }

    @Override
    public String permission() {
        return "ruinspvp.default";
    }
}
