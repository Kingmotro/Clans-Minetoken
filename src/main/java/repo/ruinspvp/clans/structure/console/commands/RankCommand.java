package repo.ruinspvp.clans.structure.console.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import repo.ruinspvp.clans.structure.rank.RankManager;
import repo.ruinspvp.clans.structure.rank.enums.Ranks;
import repo.ruinspvp.clans.structure.rank.enums.Result;
import repo.ruinspvp.clans.utilities.Format;

import java.util.UUID;

public class RankCommand implements CommandExecutor {

    public RankManager rankManager;

    public RankCommand(RankManager rankManager) {
        this.rankManager = rankManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length > 1) {
            String name = args[0];
            if (rankManager.cPlayer.checkExists(rankManager.cPlayer.getUUID(name)) == Result.TRUE) {
                UUID uuid = rankManager.cPlayer.getUUID(name);
                String rankName = args[1];
                if(rankManager.cRank.getRankFromString(rankName) != null) {
                    Ranks rank = rankManager.cRank.getRankFromString(rankName);
                    rankManager.cRank.setRank(uuid, rank);
                } else {
                    commandSender.sendMessage(Format.main("Error", "This isn't a rank."));
                }
            } else {
                commandSender.sendMessage(Format.main("Error", "This player isn't on the database."));
            }
        } else {
            help(commandSender);
        }
        return false;
    }

    public void help(CommandSender commandSender) {
        commandSender.sendMessage(Format.main("Rank", "Commands:"));
        commandSender.sendMessage(Format.info("/setrank {player} {rank}"));
    }
}
