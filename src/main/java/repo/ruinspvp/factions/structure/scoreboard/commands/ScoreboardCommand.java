package repo.ruinspvp.factions.structure.scoreboard.commands;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.factions.structure.command.CommandManager;
import repo.ruinspvp.factions.structure.scoreboard.ScoreboardManager;
import repo.ruinspvp.factions.utilities.Format;

public class ScoreboardCommand extends CommandManager {

    public ScoreboardManager scoreboardManager;

    public ScoreboardCommand(JavaPlugin plugin, ScoreboardManager scoreboardManager) {
        super(plugin, "scoreboard", "ruinspvp.default");
        this.scoreboardManager = scoreboardManager;

        addCommand("menu", new MenuCommand(scoreboardManager));
    }

    @Override
    public void help(Player player) {
        player.sendMessage(Format.help("/scoreboard menu", "Opens scoreboard menu."));
    }
}
