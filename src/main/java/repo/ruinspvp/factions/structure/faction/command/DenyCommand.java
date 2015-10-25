package repo.ruinspvp.factions.structure.faction.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import repo.ruinspvp.factions.structure.command.SubCommand;
import repo.ruinspvp.factions.structure.faction.FactionManager;
import repo.ruinspvp.factions.structure.rank.enums.Result;
import repo.ruinspvp.factions.utilities.Format;

import java.util.UUID;

public class DenyCommand implements SubCommand {

    FactionManager factionManager;

    public DenyCommand(FactionManager factionManager) {
        this.factionManager = factionManager;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (args.length == 0) {
            if (factionManager.invitedPlayer.containsKey(player.getUniqueId())) {
                if (factionManager.fPlayer.hasFaction(player.getUniqueId()) == Result.FALSE) {
                    player.sendMessage(Format.main("Factions", "You have denied the faction " + factionManager.invitedPlayer.get(player.getUniqueId()).getFaction()));
                    try {
                        if(factionManager.invitedPlayer.get(player.getUniqueId()).getPlayer().isOnline()) {
                            factionManager.invitedPlayer.get(player.getUniqueId()).getPlayer().sendMessage(Format.main("Factions", player.getName() + " has denied to join the faction."));
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
        return Format.info("/faction deny");
    }

    @Override
    public String permission() {
        return "ruinspvp.default";
    }
}
