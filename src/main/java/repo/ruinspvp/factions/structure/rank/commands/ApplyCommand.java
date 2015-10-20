package repo.ruinspvp.factions.structure.rank.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import repo.ruinspvp.factions.structure.command.SubCommand;
import repo.ruinspvp.factions.structure.rank.enums.Ranks;
import repo.ruinspvp.factions.structure.rank.enums.Result;
import repo.ruinspvp.factions.utilities.Format;

import java.util.UUID;

public class ApplyCommand implements SubCommand {

    public RankCommand rankComand;

    public ApplyCommand(RankCommand rankComand) {
        this.rankComand = rankComand;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (args.length > 1) {
            String name = args[0];
            if(name.equalsIgnoreCase(player.getName())) {
                player.sendMessage(Format.main("Error", "You can't give your self a rank."));
                return true;
            }
            if (rankComand.rankManager.fPlayer.checkExists(rankComand.rankManager.fPlayer.getUUID(name)) == Result.TRUE) {
                String rankName = args[1];
                UUID uuid = rankComand.rankManager.fPlayer.getUUID(name);
                if (rankComand.rankManager.fRank.getRankFromString(rankName) != null) {
                    Ranks rank = rankComand.rankManager.fRank.getRankFromString(rankName);
                    if (rankComand.rankManager.fRank.getRank(player.getUniqueId()).getPermLevel() > rank.getPermLevel()) {
                        rankComand.rankManager.fRank.setRank(uuid, rank);
                        player.sendMessage(Format.main("Rank", "You have applied " + rank.getName() + " rank to " + name + "."));
                        try {
                            if (Bukkit.getPlayer(uuid).isOnline()) {
                                Bukkit.getPlayer(uuid).sendMessage(Format.main("Rank", "The " + rank.getName() + " rank as been applied to your account."));
                            }
                        } catch (Exception ignore) {
                        }
                    } else {
                        player.sendMessage(Format.main("Error", "You can't apply a higher rank than yours."));
                    }
                } else {
                    player.sendMessage(Format.main("Error", "That rank doesn't exist."));
                }
            }
        } else {
            help(player);
        }
        return false;
    }

    @Override
    public String help(Player player) {
        return Format.help("/rank apply {player} {rank}");
    }

    @Override
    public String permission() {
        return "ruinspvp.admin";
    }
}
