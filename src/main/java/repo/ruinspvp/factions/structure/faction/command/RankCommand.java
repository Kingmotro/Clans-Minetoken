package repo.ruinspvp.factions.structure.faction.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import repo.ruinspvp.factions.structure.command.SubCommand;
import repo.ruinspvp.factions.structure.faction.FactionManager;
import repo.ruinspvp.factions.structure.faction.enums.FactionRanks;
import repo.ruinspvp.factions.structure.rank.enums.Result;
import repo.ruinspvp.factions.utilities.Format;

import java.util.UUID;

public class RankCommand implements SubCommand {

    public FactionManager factionManager;

    public RankCommand(FactionManager factionManager) {
        this.factionManager = factionManager;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if(args.length == 2) {
            String name = args[0];
            String rankName = args[1];
            if(factionManager.fPlayer.getFRank(player.getUniqueId()).getPermLevel() >= FactionRanks.ADMIN.getPermLevel()) {
                if(!name.equalsIgnoreCase(player.getName())) {
                    if(factionManager.fPlayer.getRankFromString(rankName) != null) {
                        FactionRanks rank = factionManager.fPlayer.getRankFromString(rankName);
                        if(factionManager.fPlayer.getFRank(player.getUniqueId()).getPermLevel() >= rank.getPermLevel()) {
                            if(factionManager.fPlayer.getUUID(name) != null) {
                                if (factionManager.fPlayer.checkExists(factionManager.fPlayer.getUUID(name)) == Result.TRUE) {
                                    player.sendMessage(Format.main("Factions", "You have made " + name + "'s rank " + rank.getName() + "."));
                                    UUID uuid = factionManager.fPlayer.getUUID(name);
                                    factionManager.fPlayer.setFRank(uuid, rank);
                                    try {

                                        if (Bukkit.getPlayer(uuid).isOnline()) {
                                            Player target = Bukkit.getPlayer(uuid);
                                            target.sendMessage(Format.main("Factions", player.getName() + " has set your rank to " + rank.getName() + "."));
                                        }
                                    } catch (Exception ignore) {
                                    }
                                } else {
                                    player.sendMessage(Format.main("Factions", "According to the database this player doesn't exist, please contact staff."));
                                }
                            } else {
                                player.sendMessage(Format.main("Factions", "According to the database this player doesn't exist, please contact staff."));
                            }
                        } else {
                            player.sendMessage(Format.main("Factions", "You can't set a rank higher than yours."));
                        }
                    } else {
                        player.sendMessage(Format.main("Factions", "That isn't a faction rank, try Pleb, Mod, Admin, Leader."));
                    }
                } else {
                    player.sendMessage(Format.main("Factions", "You can't rank yourself."));
                }
            } else {
                player.sendMessage(Format.main("Factions", "You must a admin in your faction to set someone's rank."));
            }
        } else {
            player.sendMessage(help());
        }
        return false;
    }

    @Override
    public String help() {
        return Format.help("/faction rank {player} {rank}");
    }

    @Override
    public String permission() {
        return "ruinspvp.default";
    }
}
