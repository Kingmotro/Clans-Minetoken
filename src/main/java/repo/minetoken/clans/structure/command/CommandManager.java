package repo.minetoken.clans.structure.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import repo.minetoken.clans.utilities.Format;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

public abstract class CommandManager implements CommandExecutor {

    public JavaPlugin plugin;
    public HashMap<String, SubCommand> commands;
    public String command;
    public String permission;

    public CommandManager(JavaPlugin plugin, String command, String permission) {
        this.plugin = plugin;
        this.command = command;
        this.permission = permission;

        commands = new HashMap<>();
    }

    public void addCommand(String command, SubCommand subCommand) {
        commands.put(command, subCommand);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            Bukkit.getServer().getConsoleSender().sendMessage(Format.main("Error", "Only players can use this command."));
            return true;
        }

        Player player = (Player) commandSender;

        if (command.getName().equalsIgnoreCase(this.command)) {
            if (player.hasPermission(permission)) {
                if (args == null || args.length < 1) {
                    player.sendMessage(Format.main(this.command, "Type /" + this.command + " help for command information."));
                    return true;
                }
            }
            if (args[0].equalsIgnoreCase("help")) {
                help(player);
                return true;
            }
            String sub = args[0];
            Vector<String> l = new Vector<>();
            l.addAll(Arrays.asList(args));
            l.remove(0);
            args = l.toArray(new String[0]);
            if (!commands.containsKey(sub)) {
                player.sendMessage(Format.main(this.command, "This command doesn't exist."));
                return true;
            }
            if (player.hasPermission(commands.get(sub).permission())) {
                try {
                    commands.get(sub).onCommand(player, args);
                } catch (Exception e) {
                    e.printStackTrace();
                    player.sendMessage(Format.main("Error", "Running this command cause a problem, contact a Leader/Admin or please post a thread."));
                }
            } else {
                player.sendMessage(Format.main("Error", "You don't have permission for this command."));
            }
            return true;
        } else {
            player.sendMessage(Format.main("Error", "You don't have permission for this comamd"));
        }
        return false;
    }

    public abstract void help(Player player);
}
