package repo.ruinspvp.factions.structure.console;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.factions.structure.console.commands.MoneyCommand;
import repo.ruinspvp.factions.structure.console.commands.RankCommand;
import repo.ruinspvp.factions.structure.economy.EconomyManager;
import repo.ruinspvp.factions.structure.rank.RankManager;

public class ConsoleManager implements Listener {

    public JavaPlugin plugin;
    public RankManager rankManager;
    public EconomyManager economyManager;

    public ConsoleManager(JavaPlugin plugin, RankManager rankManager, EconomyManager economyManager) {
        this.plugin = plugin;
        this.rankManager = rankManager;
        this.economyManager = economyManager;

        plugin.getCommand("setrank").setExecutor(new RankCommand(rankManager));
        plugin.getCommand("givemoney").setExecutor(new MoneyCommand(economyManager));
    }

}
