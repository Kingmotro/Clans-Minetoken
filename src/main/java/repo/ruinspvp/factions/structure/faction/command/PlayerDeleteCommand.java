package repo.ruinspvp.factions.structure.faction.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import repo.ruinspvp.factions.structure.command.SubCommand;
import repo.ruinspvp.factions.structure.faction.FactionManager;
import repo.ruinspvp.factions.structure.faction.enums.FactionRanks;
import repo.ruinspvp.factions.structure.rank.enums.Result;
import repo.ruinspvp.factions.utilities.Format;

public class PlayerDeleteCommand implements SubCommand {

    public FactionManager factionManager;

    public PlayerDeleteCommand(FactionManager factionManager) {
        this.factionManager = factionManager;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if(args.length == 0) {
            if(factionManager.fPlayer.hasFaction(player.getUniqueId()) == Result.TRUE) {
                if(factionManager.fPlayer.getFRank(player.getUniqueId()).getPermLevel() >= FactionRanks.FOUNDER.getPermLevel()) {
                    Bukkit.broadcastMessage(Format.main("Factions", player.getName() + " has disbanded the faction " + factionManager.fPlayer.getFaction(player.getUniqueId())));
                    factionManager.fFaction.deleteFaction(factionManager.fPlayer.getFaction(player.getUniqueId()));
                } else {
                    player.sendMessage(Format.main("Factions", "You must be the founder of this faction to delete it. If you want this faction deleted contact a Server Admin."));
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
        return Format.info("/faction delete");
    }

    @Override
    public String permission() {
        return "ruinspvp.default";
    }
}
