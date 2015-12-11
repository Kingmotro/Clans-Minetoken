package repo.minetoken.clans.structure.rank.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import repo.minetoken.clans.structure.rank.enums.Ranks;
import repo.minetoken.clans.structure.command.SubCommand;
import repo.minetoken.clans.structure.rank.enums.Result;
import repo.minetoken.clans.utilities.Format;

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
            if (rankComand.rankManager.cPlayer.checkExists(rankComand.rankManager.cPlayer.getUUID(name)) == Result.TRUE) {
                String rankName = args[1];
                UUID uuid = rankComand.rankManager.cPlayer.getUUID(name);
                if (rankComand.rankManager.cRank.getRankFromString(rankName) != null) {
                    Ranks rank = rankComand.rankManager.cRank.getRankFromString(rankName);
                    if (rankComand.rankManager.cRank.getRank(player.getUniqueId()).getPermLevel() > rank.getPermLevel()) {
                        rankComand.rankManager.cRank.setRank(uuid, rank);
                        player.sendMessage(Format.main("Rank", "You have applied " + Format.highlight(rank.getName()) + " rank to " + Format.highlight(name) + "."));
                        try {
                            if (Bukkit.getPlayer(uuid).isOnline()) {
                                Bukkit.getPlayer(uuid).sendMessage(Format.main("Rank", "The " + Format.highlight(rank.getName()) + " rank as been applied to your account."));
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
            player.sendMessage(help());
        }
        return false;
    }

    @Override
    public String help() {
        return Format.info("/rank apply {player} {rank}");
    }

    @Override
    public String permission() {
        return "ruinspvp.admin";
    }
}
