package repo.ruinspvp.factions.structure.faction.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import repo.ruinspvp.factions.structure.command.SubCommand;
import repo.ruinspvp.factions.structure.faction.FactionManager;
import repo.ruinspvp.factions.structure.faction.enums.FactionRanks;
import repo.ruinspvp.factions.structure.rank.enums.Result;
import repo.ruinspvp.factions.utilities.Format;

import java.util.UUID;

public class LeaveCommand implements SubCommand {

    public FactionManager factionManager;

    public LeaveCommand(FactionManager factionManager) {
        this.factionManager = factionManager;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if(args.length == 0) {
            if(factionManager.fPlayer.hasFaction(player.getUniqueId()) == Result.TRUE) {
                if(factionManager.fPlayer.getFRank(player.getUniqueId()) == FactionRanks.FOUNDER) {
                    Bukkit.broadcastMessage(Format.main("Factions", player.getName() + " has disbanded the faction " + factionManager.fPlayer.getFaction(player.getUniqueId())));
                    factionManager.fFaction.deleteFaction(factionManager.fPlayer.getFaction(player.getUniqueId()));
                } else {
                    try {
                        for(String uuid : factionManager.fPlayer.getPlayersInAFaction(factionManager.fPlayer.getFaction(player.getUniqueId()))) {
                            if(Bukkit.getPlayer(UUID.fromString(uuid)).isOnline()) {
                                Player fPlayer = Bukkit.getPlayer(UUID.fromString(uuid));
                                if(fPlayer != player) {
                                    fPlayer.sendMessage(Format.main("Factions", player.getName() + " has left the faction."));
                                }
                            }
                        }
                    } catch (Exception ignore) {}
                    player.sendMessage(Format.main("Factions", "You have left " + factionManager.fPlayer.getFaction(player.getUniqueId())));
                    factionManager.fPlayer.leaveFaction(player.getUniqueId());
                }
            } else {
                player.sendMessage(Format.main("Factions", "You have no faction."));
            }
        } else {
            player.sendMessage(help());
        }
        return false;
    }

    @Override
    public String help() {
        return Format.info("/faction leave");
    }

    @Override
    public String permission() {
        return "ruinspvp.default";
    }
}
