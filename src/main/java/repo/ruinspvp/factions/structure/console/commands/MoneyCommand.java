package repo.ruinspvp.factions.structure.console.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import repo.ruinspvp.factions.structure.economy.EconomyManager;
import repo.ruinspvp.factions.structure.rank.enums.Result;
import repo.ruinspvp.factions.utilities.Format;

import java.util.UUID;

public class MoneyCommand implements CommandExecutor {

    public EconomyManager economyManager;

    public MoneyCommand(EconomyManager economyManager) {
        this.economyManager = economyManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length > 1) {
            String name = args[0];
            if (economyManager.fPlayer.checkExists(economyManager.fPlayer.getUUID(name)) == Result.TRUE) {
                UUID uuid = economyManager.fPlayer.getUUID(name);
                String sAmount = args[1];
                if(isInt(sAmount)) {
                    int amount = Integer.parseInt(sAmount);
                    economyManager.fEco.addMoney(uuid, amount);
                } else {
                    commandSender.sendMessage(Format.main("Error", "This isn't a number."));
                }
            } else {
                commandSender.sendMessage(Format.main("Error", "This player isn't on the database."));
            }
        } else {
            help(commandSender);
        }
        return false;
    }

    public void help(CommandSender commandSender) {
        commandSender.sendMessage(Format.main("Money", "Commands:"));
        commandSender.sendMessage(Format.help("/givemoney {player} {amount}"));
    }

    public boolean isInt(String i) {
        try {
            Integer.parseInt(i);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}