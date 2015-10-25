package repo.ruinspvp.factions.structure.economy.commands;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.factions.structure.command.CommandManager;
import repo.ruinspvp.factions.structure.economy.EconomyManager;
import repo.ruinspvp.factions.utilities.Format;

public class EconomyCommand extends CommandManager {

    public EconomyManager economyManager;

    public EconomyCommand(JavaPlugin plugin, EconomyManager economyManager) {
        super(plugin, "Money", "ruinspvp.default");
        this.economyManager = economyManager;

        addCommand("pay", new PayCommand(this));
        addCommand("give", new GiveCommand(this));
    }

    @Override
    public void help(Player player) {
        player.sendMessage(Format.main("Money", "Commands:"));
        player.sendMessage(Format.help("/money pay {player} {amount}", "Pay a specific player."));
        if(player.hasPermission("ruinspvp.mod")) {
            player.sendMessage(Format.help("/money give {player} {amount}", "Give player money without taking from your balance"));
        }
    }
}
