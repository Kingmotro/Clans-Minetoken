package repo.ruinspvp.clans.structure.console.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import repo.ruinspvp.clans.structure.economy.EconomyManager;
import repo.ruinspvp.clans.structure.rank.enums.Result;
import repo.ruinspvp.clans.utilities.Format;

import java.util.UUID;

public class PointsCommand implements CommandExecutor {

    public EconomyManager economyManager;

    public PointsCommand(EconomyManager economyManager) {
        this.economyManager = economyManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length > 1) {
            String name = args[0];
            if (economyManager.cPlayer.checkExists(economyManager.cPlayer.getUUID(name)) == Result.TRUE) {
                UUID uuid = economyManager.cPlayer.getUUID(name);
                String sAmount = args[1];
                if(isInt(sAmount)) {
                    int amount = Integer.parseInt(sAmount);
                    economyManager.cEco.addPoints(uuid, amount);
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
        commandSender.sendMessage(Format.main("Economy", "Commands:"));
        commandSender.sendMessage(Format.info("/givepoints {player} {amount}"));
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