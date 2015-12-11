package repo.ruinspvp.clans.structure.economy.ccommands;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.clans.structure.command.CommandManager;
import repo.ruinspvp.clans.structure.economy.EconomyManager;
import repo.ruinspvp.clans.utilities.Format;

public class CreditCommand extends CommandManager {

    public EconomyManager economyManager;

    public CreditCommand(JavaPlugin plugin, EconomyManager economyManager) {
        super(plugin, "Economy", "minetoken.default");
        this.economyManager = economyManager;

        addCommand("give", new CreditGiveCommand(this));
    }

    @Override
    public void help(Player player) {
        player.sendMessage(Format.main("Economy", "Commands:"));
        player.sendMessage(Format.help("/credits pay {player} {amount}", "Pay a specific player."));
        if(player.hasPermission("ruinspvp.mod")) {
            player.sendMessage(Format.help("/credits give {player} {amount}", "Give player money without taking from your balance"));
        }
    }
}
