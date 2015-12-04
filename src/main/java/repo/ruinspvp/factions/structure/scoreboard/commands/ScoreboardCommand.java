package repo.ruinspvp.factions.structure.scoreboard.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.factions.structure.command.CommandManager;
import repo.ruinspvp.factions.structure.scoreboard.ScoreboardManager;
import repo.ruinspvp.factions.structure.scoreboard.ScoreboardMenu;
import repo.ruinspvp.factions.utilities.Format;

public class ScoreboardCommand implements CommandExecutor {

    public ScoreboardManager scoreboardManager;

    public ScoreboardCommand(ScoreboardManager scoreboardManager) {
        this.scoreboardManager = scoreboardManager;
    }

    public String help() {
        return Format.help("/scoreboard menu", "Opens scoreboard menu.");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player)) {
            return true;
        }

        Player player = (Player) commandSender;

        if(args.length == 0) {
            new ScoreboardMenu(scoreboardManager).show(player);
        } else {
            player.sendMessage(help());
        }
        return false;
    }
}
