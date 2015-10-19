package repo.ruinspvp.factions.structure.teleport;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by sebas on 10/19/2015.
 */
public class teleportcmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command c, String s, String[] args) {
        Player player = (Player) sender;
        if (c.getName().equalsIgnoreCase("tp")) {
            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "Please Specify A Player You Wish To Teleport To.");
                return false;
            }
            Player target = Bukkit.getServer().getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(ChatColor.RED + "Couldn't Not Find " + args[0] + "Please Choose Another Player You Wish To Teleport To.");
                return false;
            }
            if (args.length == 1) {
                player.teleport(target.getLocation());
                return false;
            }
            if (args.length == 2)
                target.teleport(player.getLocation());
            return false;
        }
        return false;
    }
}
