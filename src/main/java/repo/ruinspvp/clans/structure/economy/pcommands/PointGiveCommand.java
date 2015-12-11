package repo.ruinspvp.clans.structure.economy.pcommands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import repo.ruinspvp.clans.structure.command.SubCommand;
import repo.ruinspvp.clans.structure.rank.enums.Result;
import repo.ruinspvp.clans.utilities.Format;

import java.util.UUID;

public class PointGiveCommand implements SubCommand {

    PointsCommand economyCommand;

    public PointGiveCommand(PointsCommand economyCommand) {
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
            if (economyCommand.economyManager.cPlayer.checkExists(economyCommand.economyManager.cPlayer.getUUID(name)) == Result.TRUE) {
                if (isInt(args[1])) {
                    int amount = Integer.parseInt(args[1]);
                    UUID uuid = economyCommand.economyManager.cPlayer.getUUID(name);
                    if (amount > 0 && amount <= 5000) {
                        economyCommand.economyManager.cEco.addPoints(uuid, amount);
                        player.sendMessage(Format.main("Economy", "You gave " + name + ", $" + amount + " points."));
                        try {
                            if (Bukkit.getPlayer(uuid).isOnline()) {
                                Bukkit.getPlayer(uuid).sendMessage(Format.main("Economy", " You have been given $" + amount + " points."));
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
            player.sendMessage(help());
        }
        return false;
    }

    @Override
    public String help() {
        return Format.info("/points give {player} {amount}");
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