package repo.ruinspvp.factions.structure.economy.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import repo.ruinspvp.factions.structure.command.SubCommand;
import repo.ruinspvp.factions.structure.rank.enums.Result;
import repo.ruinspvp.factions.utilities.Format;

import java.util.UUID;

public class PayCommand implements SubCommand {

    EconomyCommand economyCommand;

    public PayCommand(EconomyCommand economyCommand) {
        this.economyCommand = economyCommand;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (args.length > 1) {
            String name = args[0];
            if (economyCommand.economyManager.fPlayer.checkExists(economyCommand.economyManager.fPlayer.getUUID(name)) == Result.TRUE) {
                if (isInt(args[1])) {
                    int amount = Integer.parseInt(args[1]);
                    UUID uuid = economyCommand.economyManager.fPlayer.getUUID(name);
                    if(amount > 0) {
                        if (economyCommand.economyManager.fEco.getMoney(player.getUniqueId()) >= amount) {
                            economyCommand.economyManager.fEco.addMoney(uuid, amount);
                            economyCommand.economyManager.fEco.removeMoney(player.getUniqueId(), amount);
                            player.sendMessage(Format.main("Money", "You have payed " + name + ", $" + amount + " bucks."));
                            try {
                                if (Bukkit.getPlayer(uuid).isOnline()) {
                                    Bukkit.getPlayer(uuid).sendMessage(Format.main("Money", player.getName() + " has payed you $" + amount + " bucks."));
                                }
                            } catch (Exception e) {
                            }
                        } else {
                            player.sendMessage(Format.main("Error", "Insufficient funds."));
                        }
                    } else {
                        player.sendMessage(Format.main("Error", "Sorry your amount isn't valid, try putting numbers above 0."));
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
    public String help(Player p) {
        return Format.help("/money pay {player} {amount}");
    }

    @Override
    public String permission() {
        return "ruinspvp.default";
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
