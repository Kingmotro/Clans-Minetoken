package repo.ruinspvp.clans.structure.console;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.clans.structure.console.commands.CreditCommand;
import repo.ruinspvp.clans.structure.console.commands.PointsCommand;
import repo.ruinspvp.clans.structure.console.commands.RankCommand;
import repo.ruinspvp.clans.structure.economy.EconomyManager;
import repo.ruinspvp.clans.structure.rank.RankManager;

public class ConsoleManager implements Listener {

    public JavaPlugin plugin;
    public RankManager rankManager;
    public EconomyManager economyManager;

    public ConsoleManager(JavaPlugin plugin, RankManager rankManager, EconomyManager economyManager) {
        this.plugin = plugin;
        this.rankManager = rankManager;
        this.economyManager = economyManager;

        plugin.getCommand("setrank").setExecutor(new RankCommand(rankManager));
        plugin.getCommand("givepoints").setExecutor(new PointsCommand(economyManager));
        plugin.getCommand("givecredits").setExecutor(new CreditCommand(economyManager));
    }

}
