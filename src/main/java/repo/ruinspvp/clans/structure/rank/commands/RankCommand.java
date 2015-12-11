package repo.ruinspvp.clans.structure.rank.commands;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.clans.structure.command.CommandManager;
import repo.ruinspvp.clans.structure.rank.RankManager;
import repo.ruinspvp.clans.utilities.Format;

public class RankCommand extends CommandManager {

    public RankManager rankManager;

    public RankCommand(JavaPlugin plugin, RankManager rankManager) {
        super(plugin, "Rank", "ruinspvp.admin");
        this.rankManager = rankManager;

        addCommand("apply", new ApplyCommand(this));
    }

    @Override
    public void help(Player player) {
        player.sendMessage(Format.main("Rank", "Commands:"));
        player.sendMessage(Format.help("/rank apply {player} {rank}", "Set's player rank."));
    }
}
