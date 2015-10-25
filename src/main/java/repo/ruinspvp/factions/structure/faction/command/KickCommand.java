package repo.ruinspvp.factions.structure.faction.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import repo.ruinspvp.factions.structure.command.SubCommand;
import repo.ruinspvp.factions.structure.faction.FactionManager;
import repo.ruinspvp.factions.structure.faction.enums.FactionRanks;
import repo.ruinspvp.factions.structure.rank.enums.Result;
import repo.ruinspvp.factions.utilities.Format;

import java.util.UUID;

public class KickCommand implements SubCommand {

    public FactionManager factionManager;

    public KickCommand(FactionManager factionManager) {
        this.factionManager = factionManager;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (args.length == 1) {
            String name = args[0];
            if (factionManager.fPlayer.getUUID(name) != null) {
                UUID uuid = factionManager.fPlayer.getUUID(name);
                if (factionManager.fPlayer.hasFaction(uuid) == Result.TRUE) {
                    if (factionManager.fPlayer.getFRank(player.getUniqueId()).getPermLevel() > factionManager.fPlayer.getFRank(uuid).getPermLevel()) {
                        if (factionManager.fPlayer.getFaction(player.getUniqueId()).equalsIgnoreCase(factionManager.fPlayer.getFaction(uuid))) {
                            try {
                                for (String uuids : factionManager.fPlayer.getPlayersInAFaction(factionManager.fPlayer.getFaction(player.getUniqueId()))) {
                                    if (Bukkit.getPlayer(UUID.fromString(uuids)).isOnline()) {
                                        Player fPlayer = Bukkit.getPlayer(UUID.fromString(uuids));
                                        if (fPlayer != player) {
                                            fPlayer.sendMessage(Format.main("Factions", player.getName() + " has benn kick from the faction."));
                                        }
                                    }
                                }
                                if (Bukkit.getPlayer(uuid).isOnline()) {
                                    Bukkit.getPlayer(uuid).sendMessage(Format.main("Factions", "You have been kicked from " + factionManager.fPlayer.getFaction(player.getUniqueId())));
                                }
                            } catch (Exception ignore) {}
                            factionManager.fPlayer.leaveFaction(uuid);
                        } else {
                            player.sendMessage(Format.main("Factions", "You must be in the same faction as the specified player."));
                        }
                    } else {
                        player.sendMessage(Format.main("Factions", "You cannot kick a player with a greater or equal to rank than yours."));
                    }
                } else {
                    player.sendMessage(Format.main("Factions", "You have no faction."));
                }
            } else {
                player.sendMessage(Format.main("Factions", "This player isn't on the database."));
            }
        } else {
            player.sendMessage(help());
        }
        return false;
    }

    @Override
    public String help() {
        return Format.info("/faction kick {player}");
    }

    @Override
    public String permission() {
        return "ruinspvp.default";
    }
}