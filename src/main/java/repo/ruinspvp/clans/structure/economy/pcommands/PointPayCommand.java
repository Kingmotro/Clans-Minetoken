package repo.ruinspvp.clans.structure.economy.pcommands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import repo.ruinspvp.clans.structure.command.SubCommand;
import repo.ruinspvp.clans.structure.rank.enums.Result;
import repo.ruinspvp.clans.utilities.Format;

import java.util.UUID;

public class PointPayCommand implements SubCommand {

    PointsCommand economyCommand;

    public PointPayCommand(PointsCommand economyCommand) {
        this.economyCommand = economyCommand;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (args.length > 1) {
            String name = args[0];
            if(name.equalsIgnoreCase(player.getName())) {
                player.sendMessage(Format.main("Error", "You can't pay your self."));
                return true;
            }
            if (economyCommand.economyManager.cPlayer.checkExists(economyCommand.economyManager.cPlayer.getUUID(name)) == Result.TRUE) {
                if (isInt(args[1])) {
                    int amount = Integer.parseInt(args[1]);
                    UUID uuid = economyCommand.economyManager.cPlayer.getUUID(name);
                    if(amount > 0) {
                        if (economyCommand.economyManager.cEco.getPoints(player.getUniqueId()) >= amount) {
                            economyCommand.economyManager.cEco.addPoints(uuid, amount);
                            economyCommand.economyManager.cEco.removePoints(player.getUniqueId(), amount);
                            player.sendMessage(Format.main("Economy", "You have payed " + name + ", " + Format.highlight(amount+"p") + "."));
                            try {
                                if (Bukkit.getPlayer(uuid).isOnline()) {
                                    Bukkit.getPlayer(uuid).sendMessage(Format.main("Economy", player.getName() + " has payed you " + Format.highlight(amount+"p") + "."));
                                }
                            } catch (Exception ignore) {}
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
            player.sendMessage(help());
        }
        return false;
    }

    @Override
    public String help() {
        return Format.info("/points pay {player} {amount}");
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
