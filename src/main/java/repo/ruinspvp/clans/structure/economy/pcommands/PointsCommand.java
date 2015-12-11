package repo.ruinspvp.clans.structure.economy.pcommands;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.clans.structure.command.CommandManager;
import repo.ruinspvp.clans.structure.economy.EconomyManager;
import repo.ruinspvp.clans.utilities.Format;

public class PointsCommand extends CommandManager {

    public EconomyManager economyManager;

    public PointsCommand(JavaPlugin plugin, EconomyManager economyManager) {
        super(plugin, "Economy", "minetoken.default");
        this.economyManager = economyManager;

        addCommand("pay", new PointPayCommand(this));
        addCommand("give", new PointGiveCommand(this));
    }

    @Override
    public void help(Player player) {
        player.sendMessage(Format.main("Economy", "Commands:"));
        player.sendMessage(Format.help("/points pay {player} {amount}", "Pay a specific player."));
        if(player.hasPermission("minetoken.mod")) {
            player.sendMessage(Format.help("/points give {player} {amount}", "Give player money without taking from your balance"));
        }
    }
}
