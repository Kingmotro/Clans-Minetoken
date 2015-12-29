package repo.minetoken.clans.structure.scoreboard.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import repo.minetoken.clans.structure.command.CommandManager;
import repo.minetoken.clans.structure.scoreboard.ScoreboardManager;
import repo.minetoken.clans.structure.scoreboard.ScoreboardMenu;
import repo.minetoken.clans.utilities.Format;

public class ScoreboardCommand implements CommandExecutor {

    public ScoreboardManager scoreboardManager;

    public ScoreboardCommand(ScoreboardManager scoreboardManager) {
        this.scoreboardManager = scoreboardManager;
    }

    public String help() {
        return Format.help("/scoreboard", "Opens scoreboard menu.");
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

