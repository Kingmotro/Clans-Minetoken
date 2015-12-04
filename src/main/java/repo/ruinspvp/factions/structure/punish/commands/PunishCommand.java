
package repo.ruinspvp.factions.structure.punish.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import repo.ruinspvp.factions.structure.punish.PunishManager;
import repo.ruinspvp.factions.structure.punish.PunishPlayer;
import repo.ruinspvp.factions.structure.rank.enums.Result;
import repo.ruinspvp.factions.utilities.Format;

import java.text.ParseException;
import java.util.UUID;

public class PunishCommand implements CommandExecutor {

    public PunishManager plugin;

    public PunishCommand(PunishManager punishManager) {
        this.plugin = punishManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        Player player = (Player) commandSender;

        if(args == null) {
        } else {
            if (args.length == 0) {
                player.sendMessage(Format.main("Punish", "You are missing an argument."));
                return true;
            } else {
                if (args.length == 1) {
                    player.sendMessage(Format.main("Punish", "You are missing an argument."));
                    return true;
                }
            }

            UUID uuid = plugin.rankManager.fPlayer.getUUID(args[0]);
            String name = plugin.rankManager.fPlayer.getName(uuid);

            if(args[1].equalsIgnoreCase("check")) {
                plugin.checkInventory(player, name);
                return true;
            }

            if(plugin.rankManager.fPlayer.checkExists(uuid) == Result.FALSE || plugin.rankManager.fPlayer.checkExists(uuid) == Result.ERROR) {
                player.sendMessage(Format.main("Punish", "Seems like this player has never joined " + Format.highlight("ThePyxel")) + ".");
                return true;
            }

            if(player.getName().equalsIgnoreCase(name)) {
                player.sendMessage(Format.main("Punish", "Sorry you can not punish your self."));
                return true;
            }

            if(plugin.punish.rankManager.fRank.getRank(uuid).getPermLevel() >= plugin.punish.rankManager.fRank.getRank(player.getUniqueId()).getPermLevel()) {
                player.sendMessage(Format.main("Punish", "You can not punish someone with the same or greater rank that yours."));
                return true;
            }

            StringBuilder sb = new StringBuilder("");

            for (int i = 1; i < args.length; i++) {
                sb.append(args[i] + " ");
            }

            String reason = sb.toString();
            PunishPlayer punishPlayer = new PunishPlayer(name, reason, player);

            try {
                plugin.punishInventory(player, punishPlayer);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
