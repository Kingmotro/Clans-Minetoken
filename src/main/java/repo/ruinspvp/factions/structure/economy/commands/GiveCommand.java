package repo.ruinspvp.factions.structure.economy.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import repo.ruinspvp.factions.structure.command.SubCommand;
import repo.ruinspvp.factions.structure.rank.enums.Result;
import repo.ruinspvp.factions.utilities.Format;

import java.util.UUID;

public class GiveCommand implements SubCommand {

    EconomyCommand economyCommand;

    public GiveCommand(EconomyCommand economyCommand) {
        this.economyCommand = economyCommand;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (args.length > 1) {
            String name = args[0];
            if(name.equalsIgnoreCase(player.getName())) {
                player.sendMessage(Format.main("Error", "You can't give your self money."));
                return true;
            }
            if (economyCommand.economyManager.fPlayer.checkExists(economyCommand.economyManager.fPlayer.getUUID(name)) == Result.TRUE) {
                if (isInt(args[1])) {
                    int amount = Integer.parseInt(args[1]);
                    UUID uuid = economyCommand.economyManager.fPlayer.getUUID(name);
                    if (amount > 0 && amount <= 5000) {
                        economyCommand.economyManager.fEco.addMoney(uuid, amount);
                        player.sendMessage(Format.main("Money", "You gave " + name + ", $" + amount + " bucks."));
                        try {
                            if (Bukkit.getPlayer(uuid).isOnline()) {
                                Bukkit.getPlayer(uuid).sendMessage(Format.main("Money", " You have been given $" + amount + " bucks."));
                            }
                        } catch (Exception ignore) {}
                    } else {
                        player.sendMessage(Format.main("Error", "Sorry your amount isn't valid, try putting numbers between 0 and 5000."));
                    }
                } else {
                    player.sendMessage(Format.main("Error", "Be sure the amount is a number."));
                }
            }
        } else {
            help(player);
        }
        return false;
    }

    @Override
    public String help(Player player) {
        return Format.help("/money give {player} {amount}");
    }

    @Override
    public String permission() {
        return "ruinspvp.mod";
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