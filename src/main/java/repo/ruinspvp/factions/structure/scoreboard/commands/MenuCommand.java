package repo.ruinspvp.factions.structure.scoreboard.commands;

import org.bukkit.entity.Player;
import repo.ruinspvp.factions.structure.command.SubCommand;
import repo.ruinspvp.factions.structure.scoreboard.ScoreboardManager;
import repo.ruinspvp.factions.structure.scoreboard.ScoreboardMenu;
import repo.ruinspvp.factions.utilities.Format;

public class MenuCommand implements SubCommand {

    public ScoreboardManager scoreboardManager;

    public MenuCommand(ScoreboardManager scoreboardManager) {
        this.scoreboardManager = scoreboardManager;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if(args.length == 0) {
            new ScoreboardMenu(scoreboardManager).show(player);
        } else {
            player.sendMessage(help());
        }
        return false;
    }

    @Override
    public String help() {
        return Format.help("/scoreboard menu", "Opens scoreboard menu.");
    }

    @Override
    public String permission() {
        return "ruinspvp.default";
    }
}
